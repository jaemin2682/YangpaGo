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

import com.papple.blog.payload.response.CartProduct;
import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.UserBehavior;
import com.papple.blog.repository.CartRepository;
import com.papple.blog.repository.PurchaseRepository;
import com.papple.blog.repository.UserBehaviorRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private UserBehaviorRepository userBehaviorRepository;
	@Autowired
	private CartRepository cartRepository;
	
	//이메일로 구매내역 조회
	@GetMapping("/search/email/{email}")
	@ApiOperation(value = "이메일로 구매내역 조회")
	public ResponseEntity<List<PurchaseProduct>> searchPurchaseByEmail(@PathVariable String email) throws Exception {
		System.out.println("이메일로 구매내역 조회");
		List<PurchaseProduct> list = purchaseRepository.searchPurchaseByEmail(email);
		
		return new ResponseEntity<List<PurchaseProduct>>(list, HttpStatus.OK);
	}
	
	//구매내역 넣기
	@PostMapping("/add")
	@ApiOperation(value = "구매내역에 상품 추가")
	public ResponseEntity<String> insertPurchase(@RequestBody PurchaseProduct purchaseProduct) throws Exception {
		System.out.println("구매내역에 상품 추가");
		int flag = purchaseRepository.addPurchase(purchaseProduct);
		cartRepository.deleteCart(purchaseProduct.getEmail(), purchaseProduct.getProduct_id());
		// Email로 UserId 가져와서, ub를 찾는다. ub가 없다면 그냥 삽입, ub가 있다면 ub 점수가 해당 점수보다 낮을 경우에만 삭제 후 삽입
		int userId = userBehaviorRepository.SearchUserId(purchaseProduct.getEmail());
		UserBehavior ub = userBehaviorRepository.SelectBehavior(userId, purchaseProduct.getProduct_id());
		if(ub != null) {
			if(ub.getRating() < 5) {
				userBehaviorRepository.DeleteBehavior(userId, purchaseProduct.getProduct_id());
				userBehaviorRepository.InsertBehavior(userId, purchaseProduct.getProduct_id(), 5);
			}
		} else {
			userBehaviorRepository.InsertBehavior(userId, purchaseProduct.getProduct_id(), 5);
		}
		
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		purchaseRepository.increaseScore(purchaseProduct.getProduct_id());
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//구매내역 상품 삭제
	@DeleteMapping("/delete/{email}/{productid}")
	@ApiOperation(value = "구매내역 상품 삭제")
	public ResponseEntity<String> deletePurchase(@PathVariable String email, @PathVariable int productid) throws Exception {
		int flag = purchaseRepository.deletePurchase(email, productid);
		System.out.println("구매내역 상품 삭제");
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
}
