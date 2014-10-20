package com.xdarkdog.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.Community;
import com.xdarkdog.web.util.PageBean;

public class CommunityDao extends DaoSupport {
	public int addCommunity(Community comm) {
		String sql = "INSERT INTO `ddcommunity`.`tbl_community` (`fruit_shop_name`, `comm_name`, `fruit_shop_owner`, `owner_phone`, `location`, `lat`, `lon`, `photos`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		Object[] params = { comm.getFruit_shop_name(),
							comm.getComm_name(),
							comm.getFruit_shop_owner(),
							comm.getOwner_phone(),
				            comm.getLocation(), 
				            comm.getLat(),
				            comm.getLon(), 
				            comm.getPhotos()};
		int affectRows = execOther(sql, params);
		return affectRows;
	}
	
	public List<Community> getAllCommunitiesByKey(String key){
		List<Community> comms = null;
		
		if (key != null) {
			String sql = "select * from `ddcommunity`.`tbl_community` where name like ?";
			Object[] params = { "%" + key + "%" };
			comms = executeQuery(sql, Community.class, params);
		} else {
			String sql = "select * from `ddcommunity`.`tbl_community`";
			comms = executeQuery(sql, Community.class, null);
		}
		return comms;
	}
	
	public Community getCommById(int id){
		String sql = "select * from `ddcommunity`.`tbl_community` where id = ?";
		Object[] params= {id};
		List<Community> comms = executeQuery(sql, Community.class, params);
		if(comms !=null && comms.size()>0){
			return comms.get(0);
		}
		return null;
	}
	
	// TODO
	public int modifyComm(Community comm){
		String sql = "update `ddcommunity`.`tbl_community` set name=?, location=?, lat=?, lon=?, small_pic_url=?, big_pic_url=? where id = ?";
		// Object[] params= {comm.getName(), comm.getLocation(),comm.getLat(),comm.getLon(),comm.getSmall_pic_url(),comm.getBig_pic_url(),comm.getId()};
		// int affectRows = execOther(sql, params);
		int affectRows = 0;
		return affectRows;
	}
	
	public int removeCommById(int id) {
		String sql = "delete from `ddcommunity`.`tbl_community` where id = ?";
		Object[] params = { id };
		int affectRows = execOther(sql, params);
		return affectRows;
	}
	
	public PageBean getCommunitiesByPage(int currpage, int pagesize){
		PageBean pb = new PageBean();
		String sqlcount = "SELECT count(id) as cnt FROM ddcommunity.tbl_community;";
		ResultSet rs = execQuery(sqlcount, null);
		try {
			if(rs.next()){
				pb.setTotalRows(rs.getInt("cnt"));
			}
		} catch (SQLException e) {
			System.out.println("读取数据库返回数据处错误了！");
			e.printStackTrace();
		}
		
		String sql = "SELECT * FROM ddcommunity.tbl_community limit ?,?;";
		Object[] params = {(currpage-1)*pagesize, pagesize};
		List<Community> comms = executeQuery(sql, Community.class, params);
		pb.setData(comms);
		pb.setPageSize(pagesize);
		return pb;
	}
}
