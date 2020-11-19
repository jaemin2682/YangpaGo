package com.papple.blog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.papple.blog.payload.response.Product;
import com.papple.blog.payload.response.ProductInfo;
import com.papple.blog.payload.response.ProductList;
import com.papple.blog.repository.MainRepository;
import com.papple.blog.repository.ProductListRepository;
import com.papple.blog.repository.ProductRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/productlist")
public class ProductListController {
	
	@Autowired
	private ProductListRepository productListRepository;
	@Autowired
	MainRepository mainRepository;
	@Autowired
    RestTemplate restTemplate;
	@Autowired
	private ProductRepository productRepository;
	
	//모든 상품 리스트 보기
	@GetMapping("/all")
	@ApiOperation(value = "모든 상품 리스트 보기")
	public ResponseEntity<List<ProductList>> searchAll(int page) throws Exception {
		System.out.println("모든 상품 리스트 출력");
		int pgStart = 9 * (page - 1);
		List<ProductList> list = productListRepository.searchAllProductList(pgStart);
		
		return new ResponseEntity<List<ProductList>>(list, HttpStatus.OK);
	}
	
	//b급 농산물 조회
	@GetMapping("/bgrade")
	@ApiOperation(value = "B급 상품 리스트 보기")
	public ResponseEntity<List<ProductList>> searchBGrade(int page) throws Exception {
		System.out.println("B급 상품 리스트 보기");
		int pgStart = 9 * (page - 1);
		List<ProductList> list = productListRepository.searchBGradeProductList(pgStart);
		
		return new ResponseEntity<List<ProductList>>(list, HttpStatus.OK);
	}
	
	//상품 이름으로 리스트 검색
	@GetMapping("/search")
	@ApiOperation(value = "이름으로 상품 리스트 검색하기")
	public ResponseEntity<List<ProductList>> searchByEmail(String name, int page) throws Exception {
		System.out.println("이름으로 상품 리스트 검색하기");
		int pgStart = 9 * (page - 1);
		List<ProductList> list = productListRepository.searchByName(name, pgStart);
		
		return new ResponseEntity<List<ProductList>>(list, HttpStatus.OK);
	}
	
	//상품 아이디로 검색
	@GetMapping("/search/pid/{id}")
	@ApiOperation(value = "아이디로 상품 리스트 검색하기")
	public ResponseEntity<List<ProductList>> searchById(@PathVariable int id) throws Exception {
		System.out.println("아이디로 상품 리스트 검색하기");
		List<ProductList> list = productListRepository.searchById(id);
		
		return new ResponseEntity<List<ProductList>>(list, HttpStatus.OK);
	}

	//추천 상품
	@GetMapping("/recommend")
    @ApiOperation(value = "추천 상품")
    public ResponseEntity<List<ProductInfo>> getSimilar(int userId, int num) throws Exception {
		System.out.println("추천 상품");
		System.out.println(userId);
    	String obj = restTemplate.getForObject("http://127.0.0.1:5000/productrec?userid=" + userId+"&num="+num, String.class);
    	String res = decode(obj);
    	System.out.println(res);
    	JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
    	JsonArray jsonArray = (JsonArray) jsonObject.get("info");
    	List<ProductInfo> productList = new ArrayList<>();
    	for(int i=0;i<jsonArray.size();i++) {
    		JsonArray info = (JsonArray) jsonArray.get(i);
    		int id = info.get(0).getAsInt();						// 상품 번호
    		String name = info.get(1).getAsString();				// 상품 이름
    		String img = info.get(2).isJsonNull() ? "" : info.get(2).getAsString();	// 상품 사진
    		Product product = productRepository.searchById(id);
    		ProductInfo productInfo = new ProductInfo(id, img, name, product.getPrice(), product.getKg());
    		productList.add(productInfo);
    	}
    	
    	//추천 상품이 num개보다 적을 떄	
    	if(productList.size() < num) {
    		int addNum = num - productList.size();
    		//상품 두 개 가져오기
    		List<ProductInfo> addProductList = mainRepository.searchNumProduct(userId, num);
    		for(int i = 0; i < addProductList.size(); i++) {
    			if(addNum == 0) break;
    			
    			boolean flag = true;
    			for(int j = 0; j < productList.size(); j++) {
    				if(addProductList.get(i).getId()  == productList.get(j).getId()) {
    					flag = false;
    					break;
    				}
    			}
    			if(flag == true) {
    				productList.add(addProductList.get(i));
    				addNum--;
    			}
    		}
    	}
    	return new ResponseEntity<List<ProductInfo>>(productList, HttpStatus.OK);
    }
	
	// 유니코드 디코더
	public static String decode(String unicode) throws Exception {
        StringBuffer str = new StringBuffer();

        char ch = 0;
        for( int i= unicode.indexOf("\\u"); i > -1; i = unicode.indexOf("\\u") ){
            ch = (char)Integer.parseInt( unicode.substring( i + 2, i + 6 ) ,16);
            str.append( unicode.substring(0, i) );
            str.append( String.valueOf(ch) );
            unicode = unicode.substring(i + 6);
        }
        str.append( unicode );
        return str.toString();
    }

}
