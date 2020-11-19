package com.papple.blog.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.papple.blog.payload.response.Product;
import com.papple.blog.payload.response.ProductInfo;
import com.papple.blog.payload.response.RecipeInfo;
import com.papple.blog.payload.response.RecipeView;
import com.papple.blog.payload.response.SearchResult;
import com.papple.blog.payload.response.StoreHome;
import com.papple.blog.payload.response.Store_ProductInfo;
import com.papple.blog.payload.response.ViewProduct;
import com.papple.blog.repository.MainRepository;
import com.papple.blog.repository.ProductRepository;
import com.papple.blog.repository.ProductViewRepository;
import com.papple.blog.repository.RecipeRepository;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/main")
public class MainController {
	
	@Autowired
	MainRepository mainRepository;
	@Autowired
    RecipeRepository recipeRepository;
	@Autowired
    RestTemplate restTemplate;
	@Autowired
	ProductRepository productRepository;

	@GetMapping("/search")
	@ApiOperation(value = "메인 검색 - 상품 리스트")
	public ResponseEntity<List<ProductInfo>> mainSearch(String word, int page) {
		int pgStart = 10 * (page - 1);
		return new ResponseEntity<List<ProductInfo>>(mainRepository.searchProductByName(word, pgStart), HttpStatus.OK);
    }
	
	//최근 본 상품
	@GetMapping("/search/view/product/{email}")
	@ApiOperation(value = "이메일로 최근 본 상품 조회(limit 4개)")
	public ResponseEntity<List<ViewProduct>> mainSearchProductView(String email) {
		System.out.println("이메일로 최근 본 상품 조회(limit 4개)");
		List<ViewProduct> list = mainRepository.searchProductViewByEmail(email);
		
		return new ResponseEntity<List<ViewProduct>>(list, HttpStatus.OK);
    }
	
	//최근 본 레시피
	@GetMapping("/search/view/recipe/{email}")
	@ApiOperation(value = "이메일로 최근 본 레시피 조회(limit 4개)")
	public ResponseEntity<List<RecipeView>> mainSearchRecipeView(String email) {
		System.out.println("이메일로 최근 본 레시피 조회(limit 4개)");
		List<RecipeView> list = mainRepository.searchRecipeViewByEmail(email);
		
		return new ResponseEntity<List<RecipeView>>(list, HttpStatus.OK);
    }
	
	//추천 상품
	@GetMapping("/recommend/product")
    @ApiOperation(value = "추천 상품(2개)")
    public ResponseEntity<List<ProductInfo>> getSimilar(int userId) throws Exception {
		System.out.println("추천 상품(2개)");
		System.out.println(userId);
    	String obj = restTemplate.getForObject("http://127.0.0.1:5000/productrec?userid=" + userId+"&num="+2, String.class);
    	List<ProductInfo> productList = new ArrayList<>();
    	if(!obj.equals("{}\n")) {
	    	String res = decode(obj);
	    	System.out.println(res);
	    	JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
	    	JsonArray jsonArray = (JsonArray) jsonObject.get("info");
	    	
	    	for(int i=0;i<jsonArray.size();i++) {
	    		JsonArray info = (JsonArray) jsonArray.get(i);
	    		int id = info.get(0).getAsInt();						// 상품 번호
	    		String name = info.get(1).getAsString();				// 상품 이름
	    		String img = info.get(2).isJsonNull() ? "" : info.get(2).getAsString();	// 상품 사진
	    		Product product = productRepository.searchById(id);
	    		ProductInfo productInfo = new ProductInfo(id, img, name, product.getPrice(), product.getKg());
	    		productList.add(productInfo);
	    	}
    	}  	
    	//추천 상품이 2개보다 적을 떄
    	
    	if(productList.size() < 2) {
    		int addNum = 2 - productList.size();
    		//상품 두 개 가져오기
    		List<ProductInfo> addProductList = mainRepository.searchTwoProduct(userId);
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
	
	//과잉 생산 농산물(3개)(지은언니 에측 완성되면 구현 예정)
	@GetMapping("/over")
	@ApiOperation(value = "시세보다 저렴한 상품/과잉생산  리스트")
	public ResponseEntity<List<Store_ProductInfo>> searchCheaperProduct() throws Exception {
		
		String[] ArrayLabel = {"2020.10.06","2020.10.07","2020.10.08","2020.10.09","2020.10.10","2020.10.11","2020.10.12","2020.10.13","2020.10.14","2020.10.15","2020.10.16","2020.10.17","2020.10.18","2020.10.19"};
		Double[] ArrayCarot = {3331.64794921875,3317.736328125,3309.42236328125,3339.37841796875,3298.75390625,3363.484130859375,3364.513671875,3385.170654296875,3366.322998046875,3396.1318359375,3404.2939453125,3433.27197265625,3387.472412109375,3407.304931640625};
		Double[] ArrayGarlic = {6204.00048828125,6247.49755859375,6179.69970703125,6226.5341796875,6279.935546875,6234.6005859375,6279.533203125,6283.72509765625,6262.98095703125,6257.75390625,6322.6875,6316.01318359375,6392.88671875,6450.1025390625};
		Double[] ArrayBaechu = {6720.4921875,6749.1201171875,6688.1474609375,6480.4267578125,6281.455078125,6047.9287109375,6089.67822265625,6003.35498046875,6014.869140625,5921.361328125,5725.43115234375,5456.39404296875,5144.77783203125,5104.8671875};
		Double[] ArrayOnion = {1715.40625,1705.05810546875,1708.54150390625,1705.0257568359375,1706.385498046875,1702.568115234375,1693.806396484375,1679.8662109375,1685.710205078125,1685.301513671875,1702.799560546875,1716.7734375,1712.994873046875,1732.1595458984375};
		
    	// 현재 날짜
    	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
    	Date date = new Date();
    	String formattedDate = format.format(date);
    	
    	int idx = -1;
    	for(int i=0;i<14;i++) if(formattedDate.equals(ArrayLabel[i])) { idx = i; break; }
    	int curCarrotPrice = (int) Math.round(ArrayCarot[idx]);
    	int curGarlicPrice = (int) Math.round(ArrayGarlic[idx]);
    	int curBaechuPrice = (int) Math.round(ArrayBaechu[idx]);
    	int curOnionPrice = (int) Math.round(ArrayOnion[idx]);
    	
    	List<Store_ProductInfo> carrotList = mainRepository.CheaperProduct(curCarrotPrice, "당근");
    	List<Store_ProductInfo> garlicList = mainRepository.CheaperProduct(curGarlicPrice, "마늘");
    	List<Store_ProductInfo> baechuList = mainRepository.CheaperProduct(curBaechuPrice, "배추");
    	List<Store_ProductInfo> onionList = mainRepository.CheaperProduct(curOnionPrice, "양파");
    	
    	PriorityQueue<Store_ProductInfo> productList = new PriorityQueue<>(new Comparator<Store_ProductInfo>() {
			@Override
			public int compare(Store_ProductInfo o1, Store_ProductInfo o2) {
				return Integer.compare(o1.getPrice(), o2.getPrice());
			}
		});
    	productList.addAll(carrotList);
    	productList.addAll(garlicList);
    	productList.addAll(baechuList);
    	productList.addAll(onionList);
    	
    	List<Store_ProductInfo> list = new ArrayList<>(productList);
		return new ResponseEntity<List<Store_ProductInfo>>(list, HttpStatus.OK);
    }
	
	
	
	
	//가격추세 데이터(지은언니 에측 완성되면 구현 예정)
	
	//B급 농산물(3개)
	@GetMapping("/search/bgrade")
	@ApiOperation(value = "b급 상품 조회하기")
	public ResponseEntity<List<ProductInfo>> mainSearchProductByStatus() throws Exception {
		System.out.println("b급 상품 조회하기");
		List<ProductInfo> list = mainRepository.searchBProduct();
		
		return new ResponseEntity<List<ProductInfo>>(list, HttpStatus.OK);
	}
	
	//전체 상품 리스트(6개)
	@GetMapping("/search/product/all")
	@ApiOperation(value = "아이템 리스트 조회(6개)")
	public ResponseEntity<List<ProductInfo>> searchAll() throws Exception {
		System.out.println("아이템 리스트 조회(6개)");
		List<ProductInfo> list = mainRepository.searchAllProduct();
		
		return new ResponseEntity<List<ProductInfo>>(list, HttpStatus.OK);
	}
	
	//레시피 추천 리스트
	@GetMapping("/search/recipe")
	@ApiOperation(value = "농산물 이름으로 해당 농산물이 주재료인 레시피 리스트 반환")
	public ResponseEntity<List<RecipeInfo>> search(String word, int page) {
		int pgStart = 10 * (page - 1);
		return new ResponseEntity<List<RecipeInfo>>(recipeRepository.search(word, pgStart), HttpStatus.OK);
	}

}