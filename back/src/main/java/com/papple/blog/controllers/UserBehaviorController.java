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

import com.papple.blog.payload.response.UserBehavior;
import com.papple.blog.payload.response.UserLike;
import com.papple.blog.repository.UserBehaviorRepository;
import com.papple.blog.repository.UserLikeRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/userbehavior")
public class UserBehaviorController {
	@Autowired
	private UserBehaviorRepository userBehaviorRepository;

	@GetMapping("/test")
	@ApiOperation(value = "유저 아이디로 행동 데이터 조회")
	public ResponseEntity<String> search(int user_id, int product_id, int rating, String email) throws Exception {
		
//		userBehaviorRepository.InsertBehavior(1, 10, 5);
		System.out.println(userBehaviorRepository.SelectBehavior(user_id, product_id));
		userBehaviorRepository.DeleteBehavior(user_id, product_id);
		System.out.println(userBehaviorRepository.SearchUserId(email));
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

}
