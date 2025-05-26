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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

import it.csi.cpass.cpassrepeng.birt.model.ReportModel;
import it.csi.cpass.cpassrepeng.birt.model.ReportModelContainer;
import it.csi.cpass.cpassrepeng.birt.util.report.BirtUtils;

/**
 * Report servlet for BIRT, for multiple reports
 */
public class SingleReportServlet extends BaseReportServlet {

	/** For serialization */
	private static final long serialVersionUID = 1097973059411042099L;

	@Override
	protected void doExecute(HttpServletRequest request, HttpServletResponse response)  {
		final String methodName = "doExecute";
		log.info(methodName, "START");
		try {
			BirtReportServiceFactory.getReportService().setContext(getServletContext(), null);
			
			ReportModelContainer reportModelContainer = BirtUtils.extractBody(request, ReportModelContainer.class);
			
			String fileName = reportModelContainer.getFileName() + "." + reportModelContainer.getFormat();
			response.setContentType(getMimeType(reportModelContainer.getFormat()));
			
			IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			IReportEngine engine = factory.createReportEngine(new EngineConfig());
			
			//if(reportModelContainer.getData()!=null && reportModelContainer.getData().size()>0) {
			if(!reportModelContainer.getData().isEmpty()) {
				ReportModel reportModel = reportModelContainer.getData().get(0);			
				ReportOutputContainer reportOutput = doExecuteReportModel(reportModel, request, engine,fileName);		
				if(reportOutput!=null ) {
					log.info(methodName, "reportOutput.filename "+ reportOutput.filename);
					response.setHeader("Content-Disposition", "attachment; filename=\"" + reportOutput.filename + "\"" );
					response.getOutputStream().write(reportOutput.bytes);
				}else {
					log.error(methodName, "reportOutput null");
				}
			}else {
				log.error(methodName, "reportModelContainer null o size = 0 ");
			}
			
		}catch (IOException ee) {
			log.error(methodName, "IOException: " + ee.getMessage(), ee);
		}catch ( BirtException ee) {
			log.error(methodName, "BirtException: " + ee.getMessage(), ee);
		}
	}

	private ReportOutputContainer doExecuteReportModel(ReportModel reportModel, HttpServletRequest request, IReportEngine engine,String filename)  {
		final String methodName = "doExecuteReportModel";
		String report = BirtUtils.getReport(reportModel.getReport(), request);
		String format = reportModel.getFormat();
		//String filename = ParameterAccessor.stripFileExtension(reportModel.getReport()) + "_" + idx + "." + reportModel.getFormat();
		filename = filename + "." + reportModel.getFormat();		
		//System.out.println("filename " + filename);
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
		} catch (EngineException ee) {
			log.error(methodName, "EngineException: " + ee.getMessage(), ee);
			//throw new IOException("Template opening error", ee);
		}catch (IOException ee) {
			log.error(methodName, "IOException: " + ee.getMessage(), ee);
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
