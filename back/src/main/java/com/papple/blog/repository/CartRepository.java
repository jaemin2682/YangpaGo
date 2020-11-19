package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.CartProduct;

@Mapper
public interface CartRepository {
	List<CartProduct> searchCartByEmail(String email);
	int addCart(CartProduct cartProduct);
	int deleteCart(String email, int productid);
	
	//auto_increment
	Object auto();
}
