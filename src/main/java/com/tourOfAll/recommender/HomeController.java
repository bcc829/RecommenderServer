package com.tourOfAll.recommender;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.mahout.cf.taste.common.TasteException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tourOfAll.mahout.MahoutRecommender;


@RestController
public class HomeController {
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String home(@PathVariable int id) throws IOException, TasteException, SQLException{
		MahoutRecommender recommenderItem = new MahoutRecommender(id);
		return recommenderItem.getRecommendationsToJson();
		
	}
}
