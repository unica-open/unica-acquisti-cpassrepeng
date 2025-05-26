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

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * Report model instance
 */
public class ReportModel {

	@SerializedName("__report")
	private String report;
	@SerializedName("__format")
	private String format;
	@SerializedName("__asattachment")
	private Boolean attachment;
	private String fileName;
	private Map<String, Object> params;
	/**
	 * @return the report
	 */
	public String getReport() {
		return report;
	}
	/**
	 * @param report the report to set
	 */
	public void setReport(String report) {
		this.report = report;
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
	/**
	 * @return the attachment
	 */
	public Boolean getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(Boolean attachment) {
		this.attachment = attachment;
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
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
