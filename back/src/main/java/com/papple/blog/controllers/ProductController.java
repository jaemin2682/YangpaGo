package com.papple.blog.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.papple.blog.payload.response.BestReviewOne;
import com.papple.blog.payload.response.Product;
import com.papple.blog.payload.response.ProductInfo;
import com.papple.blog.payload.response.ProductPicture;
import com.papple.blog.payload.response.ReviewByProductDetail;
import com.papple.blog.payload.response.ReviewList;
import com.papple.blog.repository.ProductRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
    RestTemplate restTemplate;
	
	//모든 상품 보기
	@GetMapping("/all")
	@ApiOperation(value = "모든 상품 보기")
	public ResponseEntity<List<Product>> searchAll() throws Exception {
		System.out.println("모든 상품 출력");
		List<Product> list = productRepository.searchAllProduct();
		
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	
	//상품 이름으로 검색
	@GetMapping("/search/pname/{name}")
	@ApiOperation(value = "이름으로 상품 검색하기")
	public ResponseEntity<List<Product>> searchByEmail(@PathVariable String name) throws Exception {
		System.out.println("이름으로 상품 검색하기");
		List<Product> list = productRepository.searchByName(name);
		
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	
	//상품 아이디로 검색
	@GetMapping("/search/pid/{id}")
	@ApiOperation(value = "아이디로 상품 검색하기")
	public ResponseEntity<Product> searchById(@PathVariable int id) throws Exception {
		System.out.println("아이디로 상품 검색하기");
		Product list = productRepository.searchById(id);
		return new ResponseEntity<Product>(list, HttpStatus.OK);
	}
	
	//상품 등급으로 검색
	@GetMapping("/search/status/{status}")
	@ApiOperation(value = "상품 등급으로 검색하기")
	public ResponseEntity<List<Product>> searchByStatus(@PathVariable String status) throws Exception {
		System.out.println("상품 등급으로 검색하기");
		List<Product> list = productRepository.searchByStatus(status);
		
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}
	
	//상품 등록
	@PostMapping("/add")
	@ApiOperation(value = "상품 등록")
	public ResponseEntity<String> insert(@RequestBody Product product) {
		System.out.println("상품 등록");
		int flag = productRepository.addProduct(product);	// 상품 등록
		System.out.println(product);
		if(flag != 0) {
			int id = product.getId();
			//이미지 등록
			List<String> product_img = product.getProduct_img();
			System.out.println(product_img);
			for (int i = 0; i < product_img.size(); i++) {
				ProductPicture productPicture = new ProductPicture();
				productPicture.setProduct_id(id);
				productPicture.setPath(product_img.get(i));
				int flag_picture = productRepository.addProductPicture(productPicture);
				if(flag_picture == 0)
					return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	//상품 삭제
	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "상품 삭제")
	public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
		int flag = productRepository.delete(id);
		System.out.println("상품 삭제");
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//상품 수정
	@PutMapping(value = "/update")
	@ApiOperation(value = "상품 수정")
	public ResponseEntity<String> update(@RequestBody Product product) throws Exception {
		int flag = productRepository.update(product);
		System.out.println("상품 수정");
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@GetMapping("/recommend")
    @ApiOperation(value = "상품 상세 정보 - 추천 상품")
    public ResponseEntity<List<ProductInfo>> getSimilar(int userId, int num) throws Exception {
		System.out.println("상품 상세 정보 - 추천 상품");
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
	
	// 상품 아이디로 상품 사진 조회
	@GetMapping("/search/picture/{id}")
	@ApiOperation(value = "상품 아이디로 상품 사진 검색")
	public ResponseEntity<List<ProductPicture>> searchPictureById(@PathVariable int id) throws Exception {
		System.out.println("상품 아이디로 상품 사진 검색");
		List<ProductPicture> list = productRepository.searchPictureById(id);
		
		return new ResponseEntity<List<ProductPicture>>(list, HttpStatus.OK);
	}
	
	// 상품 사진 등록
	@PostMapping("/add/picture")
	@ApiOperation(value = "상품 아이디로 상품 사진 등록")
	public ResponseEntity<String> insertPicture(@RequestBody ProductPicture productPicture) {
//		System.out.println(productPicture);
//		int id = productRepository.searchMaxProductId(); //등록 된 상품의 아이디 조회
//		productPicture.setProductId(id);
		int flag = productRepository.addProductPicture(productPicture);	// 상품 메뉴 등록
		
		System.out.println("상품 아이디로 상품 사진 등록");
		if(flag == 0) return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	//상품 사진 삭제
	@DeleteMapping("/delete/picture/{id}")
	@ApiOperation(value = "상품 사진 삭제")
	public ResponseEntity<String> deletePicture(@PathVariable int id) throws Exception {
		int flag = productRepository.deletePicture(id);
		System.out.println("상품 사진 삭제");
		
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//상품 사진 수정
	@PutMapping(value = "/update/picture")
	@ApiOperation(value = "상품 사진 수정")
	public ResponseEntity<String> updatePicture(@RequestBody ProductPicture productPicture) throws Exception {
		int flag = productRepository.updatePicture(productPicture);
		System.out.println("상품 사진 수정");
		if (flag == 0) {
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PutMapping("/upload")
	@ApiOperation(value = "사진 업로드")
	public ResponseEntity<String> fileUpload(@RequestParam("filename") MultipartFile mFile, HttpServletRequest request){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowdate = new Date();
		String dateString = formatter.format(nowdate);	//현재시간 문자열
		
		String real_path = "/home/ubuntu/s03p23a404/back/src/main/webapp/resources/postRep/" + 
				dateString + "_" + mFile.getOriginalFilename();	//경로 + 날짜시간 + _ +파일이름으로 저장

		String access_path = "http://j3a404.p.ssafy.io/images/postRep/" + dateString + "_" + mFile.getOriginalFilename();
		
		try {
			mFile.transferTo(new File(real_path));	//실제경로로 파일을 저장
			return new ResponseEntity<String>(access_path, HttpStatus.OK);	//접근경로 return
		} catch (IOException e) {
			System.out.println("파일 업로드 실패");
			return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("/review")
	@ApiOperation(value = "상품 상세 페이지의 리뷰 정보 return(베스트리뷰 + 리뷰 리스트). 분석결과는 3이 긍긍정, 2가 긍정, 1이 부정")
	public ResponseEntity<ReviewList> searchReviewList(int product_id) throws Exception {
		//베스트 리뷰 1개
		BestReviewOne bro = productRepository.bestReviewOne(product_id);
		List<ReviewByProductDetail> rbd = productRepository.ReviewListProductDetail(product_id);
		for(ReviewByProductDetail review : rbd) {
			int percent = review.getAnalysis_result();
			if(percent >= 70) review.setAnalysis_result(3);
			else if(percent >= 50) review.setAnalysis_result(2);
			else review.setAnalysis_result(1);
		}
		ReviewList list = new ReviewList(bro, rbd);
		return new ResponseEntity<ReviewList>(list, HttpStatus.OK);
	}
	
	
	
}
