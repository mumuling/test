package com.zhongtie.work.app;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;

/**
 * Auth:Cheek
 * date:2018.1.13
 */

public class Test extends DatabaseHolder {

    @Override
    public DatabaseDefinition getDatabase(Class<?> databaseClass) {
        return super.getDatabase(databaseClass);
    }

    @Override
    public DatabaseDefinition getDatabase(String databaseName) {
        return super.getDatabase(databaseName);
    }

    @Override
    public DatabaseDefinition getDatabaseForTable(Class<?> table) {
        return super.getDatabaseForTable(table);
    }
}
