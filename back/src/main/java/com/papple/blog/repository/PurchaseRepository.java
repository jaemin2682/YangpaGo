package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.PurchaseProduct;

@Mapper
public interface PurchaseRepository {
	List<PurchaseProduct> searchPurchaseByEmail(String email);
	int addPurchase(PurchaseProduct purchaseProduct);
	int deletePurchase(String email, int productid);
	int increaseScore(int id);
	//auto_increment
	Object auto();
}
