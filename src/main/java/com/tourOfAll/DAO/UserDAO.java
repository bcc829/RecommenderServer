package com.tourOfAll.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
public class UserDAO {
private JdbcTemplate jdbcTemplate;


	public void setDataSource(DataSource datasource){
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public ArrayList<String> selectByUserIdToGetItem_category_code(int user_id){
		List<String> result = jdbcTemplate.query("select item_category_code from user_preferences where user_id = ?", new RowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String item_category_code = rs.getString("item_category_code");
				return item_category_code;
			}
			
		}, user_id);
		return (ArrayList<String>) result;
	}
}
