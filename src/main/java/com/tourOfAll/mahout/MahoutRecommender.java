package com.tourOfAll.mahout;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.tourOfAll.DAO.EvaluationDAO;
import com.tourOfAll.DAO.PlaceDAO;


import org.apache.mahout.cf.taste.impl.eval.LoadEvaluator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.CachingUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;



public class MahoutRecommender {
	List<RecommendedItem> recommendations;

	public MahoutRecommender(int id) throws IOException, TasteException {
		//DB연동 
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:Spring-configure.xml");
		EvaluationDAO evaluationDAO  = ctx.getBean("evaluationDAO",EvaluationDAO.class);
		MysqlDataSource datasource = (MysqlDataSource) evaluationDAO.getDataSource();
		
//		datasource.setServerName("localhost");
//		datasource.setUser("root");
//		datasource.setPassword("465651");
//		datasource.setDatabaseName("tourOfAll2");
		
		
		DataModel model = new ReloadFromJDBCDataModel(new MySQLJDBCDataModel(datasource, "evaluations", "user_id", "item_id", "score", null));
	
		UserSimilarity similarity = new CachingUserSimilarity(new EuclideanDistanceSimilarity(model),model);


		//유저 이웃 계산 결과를 캐쉬로 저장
		UserNeighborhood neighborhood = new CachingUserNeighborhood(new ThresholdUserNeighborhood(0.75, similarity, model),model);
		

		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		LoadEvaluator.runLoad(recommender);


		IDRescorer testRescorer = new AttributeRescorer(id);
		this.recommendations = recommender.recommend(id, 20, testRescorer);
		
		
	}

	public String getRecommendations(){	
		String json = "{" + "\"Items\"" + ":" + "[";
		Iterator<RecommendedItem> itr = recommendations.iterator();
		while (itr.hasNext()) {
			RecommendedItem item = itr.next();
			PlaceDAO place = new PlaceDAO();
			String str = item.toString();
			String ItemId = str.substring(str.indexOf(":") + 1, str.indexOf(","));
			String value = str.substring(str.indexOf("value:") + 6, str.indexOf("value:") + 9);
			String url = place.selectByIdToGetURL(Integer.parseInt(ItemId));
			String title = place.selectByIdToGetTitle(Integer.parseInt(ItemId));
			if (itr.hasNext())
				json = json + "{" + "\"ID\"" + ":" + "\"" + ItemId + "\"" 
			            + ", " + "\"Value\"" + ":" + "\"" + value + "\""
			            + ", " + "\"URL\"" + ":" + "\"" + url + "\""
			            + ", " + "\"Title\"" + ":" + "\"" + title + "\""
			            + "}" + ", ";
			else
				json = json + "{" + "\"ID\"" + ":" + "\"" + ItemId + "\""
						+ ", " + "\"Value\"" + ":" + "\"" + value + "\""
						+ ", " + "\"URL\"" + ":" + "\"" + url + "\""
						+ ", " + "\"Title\"" + ":" + "\"" + title + "\""
						+ "}";
		}

		json = json + "]" + "}";
		
		return json;
	}
}
