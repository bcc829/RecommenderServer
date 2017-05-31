package com.tourOfAll.mahout;

import java.util.ArrayList;

import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tourOfAll.DAO.PlaceDAO;
import com.tourOfAll.DAO.UserDAO;

public class AttributeRescorer implements IDRescorer {
	private int UserId;
	private AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:Spring-configure.xml");
	private UserDAO user = ctx.getBean("userDAO",UserDAO.class);
	private PlaceDAO place = ctx.getBean("placeDAO",PlaceDAO.class);
	ArrayList<String> userAttribute = new ArrayList<String>();
	public AttributeRescorer(int id) {
		this.UserId = id;
		this.userAttribute = user.selectByUserIdToGetItem_category_code(this.UserId);
	}
	@Override
	public boolean isFiltered(long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double rescore(long ItemId, double originalScore) {

		String attribute = place.selectByIdToGetAttribute((int)ItemId);
		
		
		if(userAttribute.size() == 0)
			return originalScore;
		
		for (int i = 0; i < this.userAttribute.size(); i++) {
			if (attribute.equals(this.userAttribute.get(i))) {
				// 가중치 1.1배
				return originalScore * 1.1;
			}
		}
		return originalScore;
	}

}
