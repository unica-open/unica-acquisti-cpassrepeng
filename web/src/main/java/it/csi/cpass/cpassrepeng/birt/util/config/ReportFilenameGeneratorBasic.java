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

import java.util.Map;

import org.eclipse.birt.report.utility.filename.IFilenameGenerator;

/**
 * Basic generator
 */
public class ReportFilenameGeneratorBasic implements IFilenameGenerator {

	@Override
	public String getFilename(String baseName, String extension, String outputType, Map options) {
		String reportDesign = (String) options.get(OPTIONS_REPORT_DESIGN);
		String extractionExtension = (String) options.get(OPTIONS_EXTRACTION_EXTENSION);
		
		int dotIndex = reportDesign.lastIndexOf('.');
		
		return String.format("%s.%s", reportDesign.substring(0, dotIndex), extractionExtension);
	}
}
