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
package it.csi.cpass.cpassrepeng.birt.model;

import java.util.List;

/**
 * Container for ReportModel instances
 */
public class ReportModelContainer {
	
	private List<ReportModel> data;
	private String fileName;
	private String format;
	
	/**
	 * @return the data
	 */
	public List<ReportModel> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<ReportModel> data) {
		this.data = data;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

}
