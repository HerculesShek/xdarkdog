package com.xdarkdog.dao;

import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.Fruit;

public class FruitDao extends DaoSupport{
	// 根据社区的id获取所有的水果
	public List<Fruit> getFruitsByCommId(int comm_id){
		String sql = "SELECT * FROM ddcommunity.tbl_fruit where communityid = ?;";
		Object[] params = {comm_id};
		List<Fruit> fruits = executeQuery(sql, Fruit.class, params);
		return fruits;
	}
	
	public int addFruit(Fruit f) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_fruit` "
				+ "(`name`, "
				+ "`price`, "
				+ "`points`, "
				+ "`communityid`, "
				+ "`original_price`, "
				+ "`display_price`, "
				+ "`hot_tag`, "
				+ "`commend_tag`, "
				+ "`remark`, "
				+ "`introduce`, "
				+ "`photos`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Object[] params = {f.getName(),
				f.getPrice(),
				f.getPoints(),
				f.getCommunityid(),
				f.getOriginal_price(),
				f.getDisplay_price(),
				f.getHot_tag(),
				f.getCommend_tag(),
				f.getRemark(),
				f.getIntroduce(),
				f.getPhotos()};
		int affects = execOther(sql, params);
		return affects;
	}
	
	public int removeFruitById(int id){
		String sql = "delete from ddcommunity.tbl_fruit where id = ?;";
		Object[] param = {id};
		return execOther(sql, param);
	}
	
	public Fruit getFruitById(int id){
		String sql = "select * form ddcommunity.tbl_fruit where id = ?";
		Object[] params = {id};
		List<Fruit> fs = executeQuery(sql, Fruit.class, params);
		if(fs != null && fs.size()>0)
			return fs.get(0);
		return null;
	}
	
	public int removeFruitsByCommId(int commid){
		String sql = "delete FROM ddcommunity.tbl_fruit where communityid=?";
		Object[] param = {commid};
		int affects = execOther(sql, param);
		return affects;
	}
	
}
