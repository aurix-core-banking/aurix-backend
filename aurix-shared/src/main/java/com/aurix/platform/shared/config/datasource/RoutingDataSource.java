package com.aurix.platform.shared.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<DataSourceType> CURRENT_DATASOURCE =
            ThreadLocal.withInitial(() -> DataSourceType.WRITE);

    public enum DataSourceType { READ, WRITE }

    public static void setRead() { CURRENT_DATASOURCE.set(DataSourceType.READ); }
    public static void setWrite() { CURRENT_DATASOURCE.set(DataSourceType.WRITE); }
    public static void clear() { CURRENT_DATASOURCE.remove(); }

    @Override
    protected Object determineCurrentLookupKey() {
        return CURRENT_DATASOURCE.get();
    }
}
