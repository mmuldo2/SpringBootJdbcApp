package com.fepoc.pae.pamuse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
	
	
	
	//TDMS DataSource
	@Bean(name="tdmsDataSource")  //configure more for tdms here? maybe driver prefix in props...
	@Primary
	@ConfigurationProperties(prefix="spring.datasource.tdms")
	public DataSource tdmsDataSource()
	{
		System.out.println("tmds datasource called");
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="tdmsJdbcTemplate")
	public JdbcTemplate tdmsJdbcTemplate(@Qualifier("tdmsDataSource") DataSource tdmsDataSource)
	{
		System.out.println("tdms jdbctemplate calleed");
		return new JdbcTemplate(tdmsDataSource);
	}
	
	
	//MF DataSource
 	@Bean(name="MFDataSource")
    @ConfigurationProperties(prefix="spring.datasource.db2")
 	public DataSource MFDataSource()
    {
 			System.out.println("mf datasource called");
           return DataSourceBuilder.create().build();
    }	

	//MF Data sources 
	@Bean(name = "MFJdbcTemplate")
	public JdbcTemplate MFJdbcTemplate(@Qualifier("MFDataSource") DataSource MFDataSource) 
	{
		System.out.println("mf jdbctemplate called");
	
		return new JdbcTemplate(MFDataSource);
	}
	
}
