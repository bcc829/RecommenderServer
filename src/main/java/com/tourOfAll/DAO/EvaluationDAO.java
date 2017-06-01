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
	
	public boolean selectByUserIdToGetScore(int user_id){
		List<String> result = jdbcTemplate.query("select score from evaluations where user_id = ?", new RowMapper<String>(){

			
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String score = rs.getString("score");
				return score;
			}
			
		}, user_id);
		if(result.size() == 0)
			return false;
		else
			return true;
	}





}
