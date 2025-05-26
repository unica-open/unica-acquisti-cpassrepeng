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
package it.csi.cpass.cpassrepeng.birt.util.config;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.eclipse.birt.report.utility.ParameterAccessor;
import org.eclipse.birt.report.utility.filename.IFilenameGenerator;

/**
 * Filename delegate generator
 */
public class ReportFilenameGeneratorDelegate implements IFilenameGenerator {

	@Override
	public String getFilename(String baseName, String extension, String outputType, Map options) {
		IFilenameGenerator filenameGenerator = new ReportFilenameGeneratorBasic();
		String format = (String) options.get(OPTIONS_EXTRACTION_EXTENSION);

		if (format != null) {
			String filenameGeneratorClassName = ParameterAccessor.getInitProp(String.format("viewer.emitter.filename.generator.class.%s", format));

			if (filenameGeneratorClassName != null)
				try {
					Class<?> clazz = Class.forName(filenameGeneratorClassName);
					Constructor<?> constructor = clazz.getConstructor();
					filenameGenerator = (IFilenameGenerator) constructor.newInstance();
				} catch (Exception e){
					// Ignore
				}
		}
	
		return filenameGenerator.getFilename(baseName, extension, outputType, options);
	}
}
