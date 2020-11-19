package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.MyInfo;
import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.PurchaseProductList;
import com.papple.blog.payload.response.RecentProductList;
import com.papple.blog.payload.response.Review;

@Mapper
public interface MyInfoRepository {
	MyInfo searchInfo(String email);
	int searchIsStore(String email);
	List<RecentProductList> searchRecentProduct(String email);
	List<RecentProductList> searchGoodProduct(String email);
	int orderCountAll(String email);
	int reviewCount(String email);
	List<PurchaseProductList> purchaseList(String email);
	int isReview(String email, int product_id);
}
