package com.tourOfAll.DAO;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class EvaluationDAO {
	
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource datasource){
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public DataSource getDataSource(){
		return jdbcTemplate.getDataSource();
		
	}


}
