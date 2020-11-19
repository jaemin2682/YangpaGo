package com.papple.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.papple.blog.models.Store;
import com.papple.blog.payload.response.BestReview;
import com.papple.blog.payload.response.MyInfo;
import com.papple.blog.payload.response.OrderList;
import com.papple.blog.payload.response.PurchaseProductList;
import com.papple.blog.payload.response.RecentProductList;
import com.papple.blog.payload.response.StoreHome;
import com.papple.blog.payload.response.StoreInfo;
import com.papple.blog.payload.response.Store_ProductInfo;
import com.papple.blog.repository.MyInfoRepository;
import com.papple.blog.repository.StoreRepository;

import io.swagger.annotations.ApiOperation;

// http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mypage")
public class MyPageController {
	@Autowired
    private MyInfoRepository myInfoRepository;
	
	@GetMapping("/info")
	@ApiOperation(value = "내 정보 - wireframe 상 '내정보'탭의 정보 : type==1 > 판매자, type==2 > 구매자, isStore는 1이면 개설 완료, 0이면 미완료")
	public ResponseEntity<MyInfo> searchInfo(String email) {
		MyInfo myInfo = myInfoRepository.searchInfo(email);
		myInfo.setIsStore(myInfoRepository.searchIsStore(email));
		return new ResponseEntity<MyInfo>(myInfo, HttpStatus.OK);
    }
	
	@GetMapping("/orderlist")
	@ApiOperation(value = "주문조회 - wireframe 상 '주문조회'탭의 정보 : 각 상품의 isReview가 1이면 리뷰 한거, 0이면 안한거")
	public ResponseEntity<OrderList> searchOrderList(String email) {
		List<PurchaseProductList> list = myInfoRepository.purchaseList(email);
		for(int i=0;i<list.size();i++) list.get(i).setIsReview(myInfoRepository.isReview(email, list.get(i).getId()));		
		OrderList orderList = new OrderList(myInfoRepository.orderCountAll(email), myInfoRepository.reviewCount(email), 
				myInfoRepository.orderCountAll(email) - myInfoRepository.reviewCount(email), list);
		return new ResponseEntity<OrderList>(orderList, HttpStatus.OK);
    }
	
	@GetMapping("/goodlist")
	@ApiOperation(value = "찜한상품 - wireframe 상 '찜한상품'탭의 정보")
	public ResponseEntity<List<RecentProductList>> searchGoodList(String email) {
		return new ResponseEntity<List<RecentProductList>>(myInfoRepository.searchGoodProduct(email), HttpStatus.OK);
    }
	
	@GetMapping("/recentlist")
	@ApiOperation(value = "최근 본 상품 - wireframe 상 '최근 본 상품'탭의 정보")
	public ResponseEntity<List<RecentProductList>> searchRecentList(String email) {
		return new ResponseEntity<List<RecentProductList>>(myInfoRepository.searchRecentProduct(email), HttpStatus.OK);
    }
}

