package com.papple.blog.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

import com.papple.blog.payload.response.CartProduct;
import com.papple.blog.payload.response.UserBehavior;
import com.papple.blog.repository.CartRepository;
import com.papple.blog.repository.UserBehaviorRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserBehaviorRepository userBehaviorRepository;
	
	//이메일로 장바구니 조회
	@GetMapping("/search/email/{email}")
	@ApiOperation(value = "이메일로 장바구니 조회")
	public ResponseEntity<HashMap<String, ArrayList<CartProduct>>> searchCartByEmail(@PathVariable String email) throws Exception {
		System.out.println("이메일로 장바구니 조회");
		List<CartProduct> list = cartRepository.searchCartByEmail(email);
		HashMap<String, ArrayList<CartProduct>> resultMap = new HashMap<String, ArrayList<CartProduct>>(list.size());
		for (CartProduct cartProduct : list) {
			String store_name = cartProduct.getStore_name();
			if(resultMap.containsKey(store_name)) {
				resultMap.get(store_name).add(cartProduct);
			} else {
				ArrayList<CartProduct> newList = new ArrayList<CartProduct>();
				newList.add(cartProduct);
				resultMap.put(store_name, newList);
			}
			
		}
		System.out.println(resultMap);
		return new ResponseEntity<HashMap<String, ArrayList<CartProduct>>>(resultMap, HttpStatus.OK);
	}
	
	//장바구니 넣기
	@PostMapping("/add")
	@ApiOperation(value = "장바구니에 상품 추가")
	public ResponseEntity<String> insertCart(@RequestBody CartProduct cartProduct) throws Exception {
		System.out.println("장바구니에 상품 추가");
		int flag = cartRepository.addCart(cartProduct);
		// Email로 UserId 가져와서, ub를 찾는다. ub가 없다면 그냥 삽입, ub가 있다면 ub 점수가 해당 점수보다 낮을 경우에만 삭제 후 삽입
		int userId = userBehaviorRepository.SearchUserId(cartProduct.getEmail());
		UserBehavior ub = userBehaviorRepository.SelectBehavior(userId, cartProduct.getProduct_id());
		if(ub != null) {
			if(ub.getRating() < 3) {
				userBehaviorRepository.DeleteBehavior(userId, cartProduct.getProduct_id());
				userBehaviorRepository.InsertBehavior(userId, cartProduct.getProduct_id(), 3);
			}
		} else {
			userBehaviorRepository.InsertBehavior(userId, cartProduct.getProduct_id(), 3);
		}
			
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//장바구니 상품 삭제
	@DeleteMapping("/delete/{email}/{productid}")
	@ApiOperation(value = "장바구니 상품 삭제")
	public ResponseEntity<String> deleteCart(@PathVariable String email, @PathVariable int productid) throws Exception {
		int flag = cartRepository.deleteCart(email, productid);
		System.out.println("장바구니 상품 삭제");
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	

}
