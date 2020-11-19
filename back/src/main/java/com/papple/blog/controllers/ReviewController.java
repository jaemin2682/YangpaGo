package com.papple.blog.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.Review;
import com.papple.blog.payload.response.ReviewDetail;
import com.papple.blog.repository.PurchaseRepository;
import com.papple.blog.repository.ReviewRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/review")
public class ReviewController {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
    RestTemplate restTemplate;

	//이메일, 상품 아이디로 리뷰 조회
	@GetMapping("/search")
	@ApiOperation(value = "이메일, 상품 아이디로 리뷰 조회")
	public ResponseEntity<ReviewDetail> searchReview(String email, int product_id) throws Exception {
		ReviewDetail reviewDetail = reviewRepository.searchReview(email, product_id);
		return new ResponseEntity<ReviewDetail>(reviewRepository.searchReview(email, product_id), HttpStatus.OK);
	}
	
	
	//이메일로 리뷰  조회
	@GetMapping("/search/email/{email}")
	@ApiOperation(value = "이메일로 리뷰 조회")
	public ResponseEntity<List<Review>> searchReviewByEmail(@PathVariable String email) throws Exception {
		System.out.println("이메일로 리뷰  조회");
		List<Review> list = reviewRepository.searchReviewByEmail(email);
		return new ResponseEntity<List<Review>>(list, HttpStatus.OK);
	}
	
	//상품 아이디로 리뷰  조회
	@GetMapping("/search/productid/{productid}")
	@ApiOperation(value = "상품 아이디로 리뷰 조회")
	public ResponseEntity<List<Review>> searchReviewById(@PathVariable int productid) throws Exception {
		System.out.println("상품 아이디로 리뷰  조회");
		List<Review> list = reviewRepository.searchReviewById(productid);
		
		return new ResponseEntity<List<Review>>(list, HttpStatus.OK);
	}
	
	//리뷰  넣기
	@PostMapping("/add")
	@ApiOperation(value = "리뷰  추가")
	public ResponseEntity<String> insertReview(@RequestBody Review review) throws Exception {
		System.out.println("리뷰  추가");
		int flag = reviewRepository.addReview(review);
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		String obj = restTemplate.getForObject("http://127.0.0.1:5000/review?word=" + review.getContent(), String.class);
    	System.out.println("리뷰 긍정 퍼센트 : " + obj);
    	reviewRepository.updateReviewPercent((int)Double.parseDouble(obj), review.getEmail(), review.getProduct_id(), review.getContent());
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//리뷰  삭제
	@DeleteMapping("/delete/{email}/{productid}")
	@ApiOperation(value = "리뷰 삭제")
	public ResponseEntity<String> deleteReview(@PathVariable String email, @PathVariable int productid) throws Exception {
		int flag = reviewRepository.deleteReview(email, productid);
		System.out.println("리뷰  삭제");
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}
