package com.example.demo.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
@Configuration
public class MetaDataConfiguartion extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {

    }
}
