package com.tourOfAll.mahout;

import java.util.ArrayList;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

import com.tourOfAll.DAO.PlaceDAO;
import com.tourOfAll.DAO.UserDAO;

public class AttributeRescorer implements IDRescorer {
	int UserId;
	ArrayList<String> userAttribute = new ArrayList<String>();
	public AttributeRescorer(int id) {
		this.UserId = id;
		UserDAO user = new UserDAO();
		this.userAttribute = user.selectByUserIdToGetItem_category_code(this.UserId);
	}
	@Override
	public boolean isFiltered(long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double rescore(long ItemId, double originalScore) {
		PlaceDAO place = new PlaceDAO();
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
