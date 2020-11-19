package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.ProductList;

@Mapper
public interface ProductListRepository {
	List<ProductList> searchAllProductList(int page);
	List<ProductList> searchBGradeProductList(int page);
	List<ProductList> searchByName(String name, int page);
	List<ProductList> searchById(int id);
	
	//auto_increment
	Object auto();
}
