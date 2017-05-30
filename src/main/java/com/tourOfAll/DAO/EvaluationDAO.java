package com.tourOfAll.DAO;


import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


public class EvaluationDAO {
	
	private JdbcTemplate jdbcTemplate;
	

	public EvaluationDAO(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return jdbcTemplate.getDataSource();
		
	}



}
