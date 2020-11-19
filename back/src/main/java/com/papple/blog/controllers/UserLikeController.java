package com.papple.blog.controllers;

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

import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.UserLike;
import com.papple.blog.repository.ProductViewRepository;
import com.papple.blog.repository.UserLikeRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/userlike")
public class UserLikeController {
	
	@Autowired
	private UserLikeRepository userLikeRepository;

	//이메일로 좋아요 조회
	@GetMapping("/search/userid/{userid}")
	@ApiOperation(value = "유저 아이디로 좋아요 조회")
	public ResponseEntity<List<UserLike>> searchUserLikeByEmail(@PathVariable int userid) throws Exception {
		System.out.println("유저 아이디로 구매내역 조회");
		List<UserLike> list = userLikeRepository.searchUserLikeById(userid);
		
		return new ResponseEntity<List<UserLike>>(list, HttpStatus.OK);
	}
	
	//좋아요 넣기
	@PostMapping("/add")
	@ApiOperation(value = "좋아요 상품 추가")
	public ResponseEntity<String> insertUserLike(@RequestBody UserLike userLike) throws Exception {
		System.out.println("좋아요 상품 추가");
		int flag = userLikeRepository.addUserLike(userLike);
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//좋아요 상품 삭제
	@DeleteMapping("/delete/{userid}/{productid}")
	@ApiOperation(value = "좋아요 상품 삭제")
	public ResponseEntity<String> deleteUserLike(@PathVariable int userid, @PathVariable int productid) throws Exception {
		int flag = userLikeRepository.deleteUserLike(userid, productid);
		System.out.println("좋아요 상품 삭제");
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}
