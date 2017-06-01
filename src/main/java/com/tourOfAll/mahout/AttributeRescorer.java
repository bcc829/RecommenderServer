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
	private UserDAO user = ctx.getBean("userDAO", UserDAO.class);
	private PlaceDAO place = ctx.getBean("placeDAO", PlaceDAO.class);
	ArrayList<String> userAttribute = new ArrayList<String>();
	ArrayList<String> ar = new ArrayList<String>();

	public AttributeRescorer(int id) {
		this.UserId = id;
		this.userAttribute = user.selectByUserIdToGetItem_category_code(this.UserId);
		ar.add("A0101");
		ar.add("A0201");
		ar.add("A0202");
		ar.add("A0203");
		ar.add("A0204");
		ar.add("A0205");
		ar.add("A0206");
		ar.add("A0302");
		ar.add("A0303");
		ar.add("A0304");
	}

	@Override
	public boolean isFiltered(long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double rescore(long ItemId, double originalScore) {

		String attribute = place.selectByIdToGetAttribute((int) ItemId);

		if (attribute.equals(ar.get(0)) || attribute.equals(ar.get(1)) || attribute.equals(ar.get(2))
				|| attribute.equals(ar.get(3)) || attribute.equals(ar.get(4)) || attribute.equals(ar.get(5))
				|| attribute.equals(ar.get(6)) || attribute.equals(ar.get(7)) || attribute.equals(ar.get(8))
				|| attribute.equals(ar.get(9))) {

			if (userAttribute.size() == 0)
				return originalScore;

			for (int i = 0; i < this.userAttribute.size(); i++) {
				if (attribute.equals(this.userAttribute.get(i))) {
					// 가중치 1.1배
					if(originalScore >= 5){
						return originalScore; 
					}
					return originalScore * 1.1;
				}
			}
			return originalScore;
		}
		else
			return originalScore;
	}
}
