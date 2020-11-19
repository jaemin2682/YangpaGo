package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.Review;
import com.papple.blog.payload.response.ReviewDetail;

@Mapper
public interface ReviewRepository {
	List<Review> searchReviewByEmail(String email);
	List<Review> searchReviewById(int productid);
	ReviewDetail searchReview(String email, int product_id);
	int addReview(Review review);
	int deleteReview(String email, int productid);
	int updateReviewPercent(int percent, String email, int product_id, String content);
	//auto_increment
	Object auto();
}
