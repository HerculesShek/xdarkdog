package com.xdarkdog.web.controller.front;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.spring.Inject;
import com.jfinal.plugin.spring.IocInterceptor;
import com.xdarkdog.dao.CommunityDao;
import com.xdarkdog.dao.FruitDao;
import com.xdarkdog.pojo.Community;
import com.xdarkdog.pojo.Fruit;

@Before(IocInterceptor.class)
public class FruitController extends Controller {
	
	@Inject.BY_TYPE
	private FruitDao fruitDao;
	@Inject.BY_TYPE
	private CommunityDao communityDao;

	public void index() {
		// 根据社区id查询
		String commId = getPara("commId");
		if (StringUtils.isNotEmpty(commId)) {
			Community community = communityDao.getCommById(Integer.valueOf(commId));
			setAttr("community", community);
		}
		renderJsp("/pro/fruit.jsp");
	}
	
	public void list() {
		// 根据社区id查询
		String commId = getPara("commId");
		// 根据GPS位置查询
		String locLon = getPara("lon");	// 经度
		String locLat = getPara("lat");	// 纬度

		List<Fruit> fruits = new ArrayList<Fruit>();
		if (StringUtils.isNotEmpty(commId)) {
			fruits = getFruitsOfCommunity(Integer.valueOf(commId));
			
		} else if (StringUtils.isNotEmpty(locLon) && StringUtils.isNotEmpty(locLat)) {
			fruits = getFruitsByGPS(Double.valueOf(locLon), Double.valueOf(locLat));
		}
		renderJson(fruits);
	}

	public List<Fruit> getFruitsOfCommunity(Integer communityId) {
		return fruitDao.getFruitsByCommId(communityId.intValue());
	}
	
	public List<Fruit> getFruitsByGPS(Double locLon, Double locLat) {
		
		List<Fruit> fruits = new ArrayList<Fruit>();
		
		List<Community> comms = new CommunityDao().getAllCommunitiesByKey(null);
		if( CollectionUtils.isNotEmpty(comms) ) {
			Community near_comm = comms.get(0);
			double min_distance = getDistance(locLat, locLon, near_comm.getLat(), near_comm.getLon());
			for (int idx = 1; idx < comms.size(); idx++) {
				Community c = comms.get(idx);
				double lat2 = c.getLat();
				double lon2 = c.getLon();
				double distance = getDistance(locLat, locLon, lat2, lon2);
				if (distance < min_distance) {
					min_distance = distance;
					near_comm = c;
				}
			}
			fruits = fruitDao.getFruitsByCommId(near_comm.getId());
		}
		
		return fruits;
	}
	
	private double getDistance(double lat1, double lon1, double lat2, double lon2){
		double res = (lat1-lat2)*(lat1-lat2)+(lon1-lon2)*(lon1-lon2);
		return Math.sqrt(res);
	}
}
