package com.papple.blog.repository;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.UserBehavior;

@Mapper
public interface UserBehaviorRepository {
	UserBehavior SelectBehavior(int user_id, int product_id);
	int InsertBehavior(int user_id, int product_id, int rating);
	int DeleteBehavior(int user_id, int product_id);
	int SearchUserId(String email);
}
