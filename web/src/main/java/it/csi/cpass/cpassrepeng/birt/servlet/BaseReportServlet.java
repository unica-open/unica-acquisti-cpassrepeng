/*-
 * ========================LICENSE_START=================================
 * CPASS Report Engine - WAR submodule
 * %%
 * Copyright (C) 2019 - 2025 CSI Piemonte
 * %%
 * SPDX-FileCopyrightText: Copyright 2019 - 2025 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.cpass.cpassrepeng.birt.servlet;

import java.io.IOException;
import java.io.OutputStream;
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
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.model.api.util.StringUtil;
import org.eclipse.birt.report.service.BirtReportServiceFactory;
import org.eclipse.birt.report.service.BirtViewerReportService;
import org.eclipse.birt.report.utility.BirtUtility;

import it.csi.cpass.cpassrepeng.birt.util.log.LogUtil;

/**
 * Basic report servlet
 */
public abstract class BaseReportServlet extends HttpServlet {

	/** For serialization */
	private static final long serialVersionUID = 1L;
	/** Logger */
	protected final LogUtil log = new LogUtil(getClass());

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
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if (!authenticate(request, response)) {
			return;
		}
		try {
			doExecute(request, response);
		} catch (BirtException | IOException e) {
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
			case "zip": return "application/zip";
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
	protected abstract void doExecute(HttpServletRequest request, HttpServletResponse response) throws BirtException, IOException;
	
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
	protected void handleNonSoapException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		final String methodName = "handleNonSoapException";
		log.error(methodName, "Exception: " + exception.getMessage(), exception);
		response.setContentType("text/html; charset=utf-8");
		try {
			BirtUtility.appendErrorMessage(response.getOutputStream(), exception);
		} catch (IOException e) {
			log.error("handleNonSoapException", e);
		}
	}
	
	/**
	 * Initialization of the renderer options
	 * @param format
	 * @param response
	 * @return the render options
	 * @throws IOException in case on an IOException
	 */
	protected IRenderOption initRenderOptions(String format, HttpServletResponse response) throws IOException {
		return initRenderOptions(format, response.getOutputStream());
	}
	
	/**
	 * Initialization of the renderer options
	 * @param format
	 * @param outputStream
	 * @return the render options
	 * @throws IOException in case on an IOException
	 */
	protected IRenderOption initRenderOptions(String format, OutputStream outputStream) throws IOException {
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
		options.setOutputStream(outputStream);
		return options;
	}

	/**
	 * Sets the parameters into the task
	 * @param task the task
	 * @param paramTask the parameters for the task
	 * @param request the request
	 */
	protected void setParameters(IRunAndRenderTask task, IGetParameterDefinitionTask paramTask, HttpServletRequest request) {
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
	 * Sets the parameters into the task
	 * @param task the task
	 * @param paramTask the parameters for the task
	 * @param parameterMap the parameterMap
	 */
	protected void setParameters(IRunAndRenderTask task, IGetParameterDefinitionTask paramTask, Map<String, Object> parameterMap) {
		for(Iterator<?> it = paramTask.getParameterDefns(false).iterator(); it.hasNext();) {
			IParameterDefn pd = (IParameterDefn) it.next();
			String name = pd.getName();
			
			if(parameterMap.containsKey(name)) {
				Object value = parameterMap.get(name);
				task.setParameterValue(name, value);
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
		if(StringUtil.isEmpty(inputObject)) {
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
