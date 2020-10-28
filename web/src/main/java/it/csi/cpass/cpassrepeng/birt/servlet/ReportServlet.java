/*-
 * ========================LICENSE_START=================================
 * CPASS Report Engine - WAR submodule
 * %%
 * Copyright (C) 2019 - 2020 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2019 - 2020 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.cpass.cpassrepeng.birt.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.context.BirtContext;
import org.eclipse.birt.report.context.IContext;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.service.BirtReportServiceFactory;
import org.eclipse.birt.report.service.BirtViewerReportService;
import org.eclipse.birt.report.utility.BirtUtility;
import org.eclipse.birt.report.utility.ParameterAccessor;

import it.csi.cpass.cpassrepeng.birt.util.log.LogUtil;

/**
 * Report servlet for BIRT
 */
public class ReportServlet extends HttpServlet {

	/** For serialization */
	private static final long serialVersionUID = 1097973059411042098L;

	private final LogUtil log = new LogUtil(getClass());

	@Override
	public void init(ServletConfig config) throws ServletException {
		BirtReportServiceFactory.init(new BirtViewerReportService(config.getServletContext()));
		try {
			Platform.startup();
		} catch (BirtException e) {
			// Ignore exception
		}
		super.init(config);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!authenticate(request, response)) {
			return;
		}

		try {
			doExecute(request, response);
		} catch (BirtException | RuntimeException e) {
			handleNonSoapException(request, response, e);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * Maps the MIME types
	 * @param format the format
	 * @return the mime type
	 */
	protected String getMimeType(String format) {
		switch(format.toLowerCase()) {
			case "pdf": return "application/pdf";
			case "xls": return "application/vnd.ms-excel";
			case "xlsx": return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			default: return "application/octet-stream";
		}
	}
	
	/**
	 * Execution of the report
	 * @param request the request
	 * @param response the response
	 * @throws BirtException in case of an exception
	 * @throws IOException in case of an IO Exception
	 */
	protected void doExecute(HttpServletRequest request, HttpServletResponse response) throws BirtException, IOException {
		final String methodName = "doExecute";
		BirtReportServiceFactory.getReportService().setContext(getServletContext(), null);
		IContext context = new BirtContext(request, response);
		
		String report = ParameterAccessor.getReport(request, null);
		String format = ParameterAccessor.getFormat(request);
		String openType = ParameterAccessor.getOpenType(request);

		// get extract file name
		String fileName = ParameterAccessor.getExtractionFilename(context, format, format);
		response.setHeader("Content-Disposition", openType + "; filename=\"" + fileName + "\"" );
		// set mime type
		response.setContentType(getMimeType(format));
		
		IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		IReportEngine engine = factory.createReportEngine(new EngineConfig());
		IReportRunnable design;
		try {
			log.debug(methodName, "Report " + report);
			design = engine.openReportDesign(report);
		} catch (EngineException ee) {
			log.error(methodName, "EngineException: " + ee.getMessage(), ee);
			throw new IOException("Template opening error", ee);
		}
		IGetParameterDefinitionTask paramTask = engine.createGetParameterDefinitionTask(design);
		IRunAndRenderTask task = engine.createRunAndRenderTask(design);
		task.setRenderOption(initRenderOptions(format, response));
		setParameters(task, paramTask, request);
		task.run();
	}
	
	/**
	 * Authentication
	 * @param request the request
	 * @param response the response
	 * @return whether the authentication succeeded
	 */
	protected boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	/**
	 * Handles an exception
	 * @param request the request
	 * @param response the response
	 * @param exception the exception
	 * @throws IOException in case of an exception
	 */
	protected void handleNonSoapException(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
		final String methodName = "handleNonSoapException";
		log.error(methodName, "Exception: " + exception.getMessage(), exception);
		response.setContentType("text/html; charset=utf-8");
		BirtUtility.appendErrorMessage(response.getOutputStream(), exception);
	}
	
	/**
	 * Initialization of the renderer options
	 * @param format
	 * @param response
	 * @return the render options
	 * @throws IOException in case on an IOException
	 */
	private IRenderOption initRenderOptions(String format, HttpServletResponse response) throws IOException {
		IRenderOption options;
		switch(format.toLowerCase()) {
			case "pdf":
				options = new PDFRenderOption();
				break;
			case "xls":
			case "xlsx":
				options = new EXCELRenderOption();
				break;
			default:
				options = new RenderOption();
				break;
		}
		options.setOutputFormat(format);
		options.setOutputStream(response.getOutputStream());
		return options;
	}

	/**
	 * Sets the parameters into the task
	 * @param task the task
	 * @param paramTask the parameters for the task
	 * @param request the request
	 */
	private void setParameters(IRunAndRenderTask task, IGetParameterDefinitionTask paramTask, HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		for(Iterator<?> it = paramTask.getParameterDefns(false).iterator(); it.hasNext();) {
			IParameterDefn pd = (IParameterDefn) it.next();
			String name = pd.getName();
			
			if(parameterMap.containsKey(name)) {
				String[] values = parameterMap.get(name);
				if(values.length == 1) {
					task.setParameterValue(name, convertType(values[0], pd.getDataType(), name));
				} else {
					task.setParameterValue(name, convertTypes(values, pd.getDataType(), name));
				}
			}
		}
	}
	
	/**
	 * Converts an array of string into the requested type
	 * @param inputObject the input array
	 * @param outputType the required type
	 * @param name the name of the type
	 * @return the output array
	 */
	private Object convertTypes(String[] inputObject, int outputType, String name) {
		Object[] res = new Object[inputObject.length];
		for(int i = 0; i < inputObject.length; i++) {
			res[i] = convertType(inputObject[i], outputType, name);
		}
		return res;
	}
	
	/**
	 * Converts a string into the requested type
	 * @param inputObject the input string
	 * @param outputType the required type
	 * @param name the name of the type
	 * @return the output value
	 */
	private Object convertType(String inputObject, int outputType, String name) {
		final String methodName = "convertType";
		if(inputObject == null) {
			log.error(methodName, "NULL value for name " + name);
			return null;
		}
		switch(outputType) {
			case IParameterDefn.TYPE_ANY: return inputObject;
			case IParameterDefn.TYPE_STRING: return inputObject;
			case IParameterDefn.TYPE_FLOAT: return Float.valueOf(inputObject);
			case IParameterDefn.TYPE_DECIMAL: return new BigDecimal(inputObject);
			case IParameterDefn.TYPE_BOOLEAN: return Boolean.valueOf(inputObject);
			case IParameterDefn.TYPE_INTEGER: return Integer.valueOf(inputObject);
			// TODO
			case IParameterDefn.TYPE_DATE_TIME: return null;
			case IParameterDefn.TYPE_DATE: return null;
			case IParameterDefn.TYPE_TIME: return null;
			default: return null;
		}
	}
}
