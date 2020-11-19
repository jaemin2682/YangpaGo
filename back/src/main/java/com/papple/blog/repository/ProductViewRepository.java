package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.ViewProduct;

@Mapper
public interface ProductViewRepository {

	List<ViewProduct> searchViewByEmail(String email);
	int addView(ViewProduct viewProduct);
	int deleteView(String email, int product_id);
	
	//auto_increment
	Object auto();
}
