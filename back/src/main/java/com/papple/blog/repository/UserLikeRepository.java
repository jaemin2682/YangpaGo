package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.UserLike;

@Mapper
public interface UserLikeRepository {

	List<UserLike> searchUserLikeById(int userid);
	int addUserLike(UserLike userLike);
	int deleteUserLike(int userid, int productid);
	
	//auto_increment
	Object auto();
}
