package com.tourOfAll.DAO;


import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


public class PlaceDAO {
	private JdbcTemplate jdbcTemplate;
	

	public PlaceDAO(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public String selectByIdToGetAttribute(int id){
		String attribute = jdbcTemplate.queryForObject("select property from place where Content_Id = ?", String.class, id);
		return attribute;
	}
	
	public String selectByIdToGetTitle(int id){
		String Title =  jdbcTemplate.queryForObject("select Title from place where Content_Id = ?", String.class, id);
		return Title;
	}
	
	public String selectByIdToGetURL(int id){
		String URL = jdbcTemplate.queryForObject("select URL from place where Content_Id = ?", String.class, id);
		return URL;
	}

}
