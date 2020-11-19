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
import com.papple.blog.payload.response.UserBehavior;
import com.papple.blog.payload.response.ViewProduct;
import com.papple.blog.repository.ProductViewRepository;
import com.papple.blog.repository.PurchaseRepository;
import com.papple.blog.repository.UserBehaviorRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/view")
public class ProductViewController {

	@Autowired
	private ProductViewRepository productViewRepository;
	@Autowired
	private UserBehaviorRepository userBehaviorRepository;

	
	//이메일로 최근 본 상품 조회
	@GetMapping("/search/email/{email}")
	@ApiOperation(value = "이메일로 최근 본 상품 조회")
	public ResponseEntity<List<ViewProduct>> searchViewByEmail(@PathVariable String email) throws Exception {
		System.out.println("이메일로 최근 본 상품 조회");
		List<ViewProduct> list = productViewRepository.searchViewByEmail(email);
		
		return new ResponseEntity<List<ViewProduct>>(list, HttpStatus.OK);
	}
	
	//최근 본 상품 넣기
	@PostMapping("/add")
	@ApiOperation(value = "최근 본 상품에 상품 추가")
	public ResponseEntity<String> insertView(@RequestBody ViewProduct viewProduct) throws Exception {
		System.out.println("최근 본 상품에 상품 추가");
		productViewRepository.deleteView(viewProduct.getEmail(), viewProduct.getProduct_id());
		int flag = productViewRepository.addView(viewProduct);
		
		// Email로 UserId 가져와서, ub를 찾는다. ub가 없다면 그냥 삽입, ub가 있다면 ub 점수가 해당 점수보다 낮을 경우에만 삭제 후 삽입
		int userId = userBehaviorRepository.SearchUserId(viewProduct.getEmail());
		UserBehavior ub = userBehaviorRepository.SelectBehavior(userId, viewProduct.getProduct_id());
		if(ub != null) {
			if(ub.getRating() < 1) {
				userBehaviorRepository.DeleteBehavior(userId, viewProduct.getProduct_id());
				userBehaviorRepository.InsertBehavior(userId, viewProduct.getProduct_id(), 1);
			}
		} else {
			userBehaviorRepository.InsertBehavior(userId, viewProduct.getProduct_id(), 1);
		}
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(value = "이메일, 상품 아이디로 최근 본 상품 삭제")
	public ResponseEntity<Integer> delete(String email, int product_id) throws Exception {
		return new ResponseEntity<Integer>(productViewRepository.deleteView(email, product_id), HttpStatus.OK);
	}	
	
}
