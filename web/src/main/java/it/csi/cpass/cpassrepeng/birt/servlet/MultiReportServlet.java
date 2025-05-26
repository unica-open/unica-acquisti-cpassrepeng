/*-
 * ========================LICENSE_START=================================
 * CPASS Report Engine - WAR submodule
 * %%
 * Copyright (C) 2019 - 2025 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2019 - 2020 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.cpass.cpassrepeng.birt.servlet;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.service.BirtReportServiceFactory;
import org.eclipse.birt.report.utility.ParameterAccessor;

import it.csi.cpass.cpassrepeng.birt.model.ReportModel;
import it.csi.cpass.cpassrepeng.birt.model.ReportModelContainer;
import it.csi.cpass.cpassrepeng.birt.util.report.BirtUtils;

/**
 * Report servlet for BIRT, for multiple reports
 */
public class MultiReportServlet extends BaseReportServlet {

	/** For serialization */
	private static final long serialVersionUID = 1097973059411042099L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB

	@Override
	protected void doExecute(HttpServletRequest request, HttpServletResponse response){
		final String methodName = "doExecute";
		log.info(methodName, "START");
		try {
			BirtReportServiceFactory.getReportService().setContext(getServletContext(), null);	
			ReportModelContainer reportModelContainer = BirtUtils.extractBody(request, ReportModelContainer.class);
			String fileName = reportModelContainer.getFileName() + "." + reportModelContainer.getFormat();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"" );
			response.setContentType(getMimeType(reportModelContainer.getFormat()));
			
			IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			IReportEngine engine = factory.createReportEngine(new EngineConfig());
			
			//try (ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE))) {
			ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE));
			
			int i = 1;
			for(ReportModel reportModel : reportModelContainer.getData()) {
				ReportOutputContainer reportOutput = doExecuteReportModel(reportModel, request, engine, i);
				if(reportOutput!= null) {
					output.putNextEntry(new ZipEntry(reportOutput.filename));
					output.write(reportOutput.bytes);
					i++;
				}else {
					log.error(methodName, "output is null ");
				}
			}
			output.finish();
			output.flush();
		} catch (BirtException e) {
			log.error(methodName, e);
		}catch (IOException e) {
			log.error(methodName, e);
		}
	}

	private ReportOutputContainer doExecuteReportModel(ReportModel reportModel, HttpServletRequest request, IReportEngine engine, int idx) {
		final String methodName = "doExecuteReportModel";
		String report = BirtUtils.getReport(reportModel.getReport(), request);
		String format = reportModel.getFormat();
		String filename = reportModel.getFileName() != null ? reportModel.getFileName() : ParameterAccessor.stripFileExtension(reportModel.getReport());
		filename += "_" + idx + "." + reportModel.getFormat();
		log.info(methodName, "filename " + filename);

		IReportRunnable design;
		try {
			log.debug(methodName, "Report " + report);
			design = engine.openReportDesign(report);	
			IGetParameterDefinitionTask paramTask = engine.createGetParameterDefinitionTask(design);
			IRunAndRenderTask task = engine.createRunAndRenderTask(design);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			task.setRenderOption(initRenderOptions(format, baos));
			setParameters(task, paramTask, reportModel.getParams());
			task.run();
			return new ReportOutputContainer(filename, baos.toByteArray());
		}catch (EngineException ee) {
			log.error(methodName, "EngineException: " + ee.getMessage(), ee);
		}catch (IOException e) {
			log.error(methodName, "IOException: " + e.getMessage(), e);
		}
		return null;
	}
	
	private static class ReportOutputContainer {
		final String filename;
		final byte[] bytes;
		public ReportOutputContainer(String filename, byte[] bytes) {
			this.filename = filename;
			this.bytes = bytes;
		}
	}
}
