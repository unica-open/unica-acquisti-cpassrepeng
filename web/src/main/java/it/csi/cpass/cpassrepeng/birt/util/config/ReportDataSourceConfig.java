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
package it.csi.cpass.cpassrepeng.birt.util.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration for the DataSource
 */
public class ReportDataSourceConfig {

	private static volatile Properties config = null;

	/**
	 * Reads a property from the configuration file
	 * @param key the key to read
	 * @return the property value
	 */
	private static String getProperty(String key) {
		Properties result = config;
		if (result == null) {
			synchronized(ReportDataSourceConfig.class) {
				result = config;
				if(result == null) {
					result = new Properties();

					try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("datasource-config.properties")) {
						result.load(stream);
						config = result;
					} catch (Exception e) {
						// TODO: log
//						log.error("getProperty", e.getMessage(), e);
					}
				}
			}
		}
		return result.getProperty(key);
	}

	/**
	 * Obtains the JNDI name for ODA
	 * @return the JNDI name
	 */
	public static String getOdaJndiName() {
		return getProperty("datasource.oda.jndiname");
	}
}
