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
package it.csi.cpass.cpassrepeng.birt.util.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.birt.report.utility.ParameterAccessor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utilities for BIRT
 */
public class BirtUtils {
	
	private static final Gson GSON;
	
	static {
		GSON = new GsonBuilder().create();
	}
	/**
	 * Gets the report
	 * @param filePath
	 * @param request
	 * @return the report
	 */
	public static String getReport( String filePath, HttpServletRequest request ) {
		return ParameterAccessor.getRealPathOnWorkingFolder( filePath, request );
	}
	
	/**
	 * Extracts the body to a given class
	 * @param <T> the type
	 * @param request the request
	 * @param clazz the class
	 * @return the body
	 * @throws IOException 
	 */
	public static <T> T extractBody( HttpServletRequest request, Class<T> clazz ) throws IOException {
		String body = "";
		try(BufferedReader reader = request.getReader()) {
			body = reader.lines().collect(Collectors.joining(System.lineSeparator()));
		}
		return GSON.fromJson(body, clazz);
	}

}
