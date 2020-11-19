package com.papple.blog.repository;

import org.apache.ibatis.annotations.Mapper;
import com.papple.blog.models.*;
import com.papple.blog.payload.response.BestReview;
import com.papple.blog.payload.response.ProductInfo;
import com.papple.blog.payload.response.StoreInfo;
import com.papple.blog.payload.response.StoreInfoGet;
import com.papple.blog.payload.response.Store_ProductInfo;

import java.util.*;

@Mapper
public interface StoreRepository {
	StoreInfoGet searchStoreInfo(String email);
	Object searchStoreScore(String email);
	int getReviewCount(int score, String email);
	List<Store_ProductInfo> PopularProduct(String email, int page);
	List<Store_ProductInfo> searchAllProduct(int userid, String email);
	List<Store_ProductInfo> PopularProductAll(String email);
	List<Store_ProductInfo> allProduct(String email, int page);
	List<Store_ProductInfo> CheaperProduct(String email, int price, String type);
	List<BestReview> bestReview(String email);
	
	int reviewCount(String email);
	int positiveReviewCount(String email);
	
	int addStore(Store store);
	int modifyStore(Store store);
	
	
}
