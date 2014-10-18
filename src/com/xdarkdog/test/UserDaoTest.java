package com.xdarkdog.test;

import java.util.Date;

import com.xdarkdog.dao.UserDao;
import com.xdarkdog.pojo.User;

public class UserDaoTest {
	public static void main(String[] args) {
		User u = new UserDao().getUserByKey("sadfasdfasdfasdf");
		System.out.println(u);
		
	}
	
	
}
