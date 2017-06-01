package com.tourOfAll.DAO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class EvaluationDAO {
	
	private JdbcTemplate jdbcTemplate;
	

	public EvaluationDAO(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public ArrayList<String> selectByUserIdToGetScore(int user_id){
		List<String> result = jdbcTemplate.query("select item_category_code from user_preferences where user_id = ?", new RowMapper<String>(){

			
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String score = rs.getString("score");
				return score;
			}
			
		}, user_id);
		return (ArrayList<String>) result;
	}





}
