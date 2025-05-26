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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.context.BirtContext;
import org.eclipse.birt.report.context.IContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.service.BirtReportServiceFactory;
import org.eclipse.birt.report.utility.ParameterAccessor;

/**
 * Report servlet for BIRT
 */
@Deprecated
public class ReportServlet extends BaseReportServlet {

	/** For serialization */
	private static final long serialVersionUID = 1097973059411042098L;

	@Override
	protected void doExecute(HttpServletRequest request, HttpServletResponse response)  {
		final String methodName = "doExecute";
		log.info(methodName, "START");
		try {
			BirtReportServiceFactory.getReportService().setContext(getServletContext(), null);
		} catch (BirtException e) {
			log.error(methodName, "BirtException ",e);
		}
		IContext context = new BirtContext(request, response);
		
		String report = ParameterAccessor.getReport(request, null);
		String format = ParameterAccessor.getFormat(request);
		String openType = ParameterAccessor.getOpenType(request);

		// get extract file name
		String fileName = ParameterAccessor.getExtractionFilename(context, format, format);
		log.info(methodName, "fileName " + fileName);

		response.setHeader("Content-Disposition", openType + "; filename=\"" + fileName + "\"" );
		// set mime type
		response.setContentType(getMimeType(format));
		
		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(new EngineConfig());
		IReportRunnable design = null;
		try {
			log.info(methodName, "Report " + report);
			design = engine.openReportDesign(report);
			IGetParameterDefinitionTask paramTask = engine.createGetParameterDefinitionTask(design);
			IRunAndRenderTask task = engine.createRunAndRenderTask(design);
			task.setRenderOption(initRenderOptions(format, response));
			setParameters(task, paramTask, request);
			task.run();
		} catch (IOException ee) {
			log.error(methodName, "IOException: " + ee.getMessage(), ee);
		}catch (EngineException ee) {
			log.error(methodName, "EngineException: " + ee.getMessage(), ee);
		}
		
	}
}
