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
package org.eclipse.birt.data.engine.executor;

import java.util.Map;

import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.data.engine.impl.DataEngineSession;
import org.eclipse.birt.data.engine.odi.IDataSource;
import org.eclipse.birt.data.engine.odi.IDataSourceFactory;
import org.eclipse.birt.report.data.oda.jdbc.Connection;

import it.csi.cpass.cpassrepeng.birt.util.config.ReportDataSourceConfig;

/**
 * The DataSource Factory class
 */
public class DataSourceFactory implements IDataSourceFactory {
	private static final String ODA_DRIVER_NAME = "org.eclipse.birt.report.data.oda.jdbc";

	/**
	 * volatile modifier is used here to ensure the DataSourceFactory, when
	 * being constructed by JVM, will be locked by current thread until the
	 * finish of construction.
	 */
	private static DataSourceFactory instance = null;
	/**
	 * Retrieves the factory. Lazy loaded
	 * @return the factory
	 */
	/* vecchia versione prima di sonar
	public static IDataSourceFactory getFactory() {
		if (instance == null) {
			synchronized (DataSourceFactory.class) {
				if (instance == null) {
					instance = new DataSourceFactory();
				}
			}
		}
		return instance;
	}
	 */
	public static synchronized IDataSourceFactory getFactory() {
		if (instance == null) {
			instance = new DataSourceFactory();
		}
		return instance;
	}
	
	/*
	public class SafeLazyInitialization {
    private static Resource resource;

    public static synchronized Resource getInstance() {
        if (resource == null)
            resource = new Resource();
        return resource;
    }

    static class Resource {
    }
}
	 */
	
	
	
	
	
	
	
	@Override
	public IDataSource getDataSource(String driverName, Map connectionProperties, DataEngineSession session) throws DataException {
		if (ODA_DRIVER_NAME.equals(driverName)) {
			connectionProperties.put(Connection.Constants.ODAJndiName, ReportDataSourceConfig.getOdaJndiName());
		}
		return new DataSource(driverName, connectionProperties, session);
	}

	@Override
	public IDataSource getEmptyDataSource(DataEngineSession session) {
		return new DataSource(null, null, session);
	}

}
