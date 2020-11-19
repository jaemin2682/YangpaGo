package com.papple.blog.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.papple.blog.models.Store;
import com.papple.blog.payload.response.BestReview;
import com.papple.blog.payload.response.ProductInfo;
import com.papple.blog.payload.response.RecipeInfo;
import com.papple.blog.payload.response.StoreHome;
import com.papple.blog.payload.response.StoreInfo;
import com.papple.blog.payload.response.StoreInfoGet;
import com.papple.blog.payload.response.Store_ProductInfo;
import com.papple.blog.repository.StoreRepository;

import io.swagger.annotations.ApiOperation;

// http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/store")
public class StoreController {
	@Autowired
    private StoreRepository storeRepository;
	@Autowired
    RestTemplate restTemplate;
	
	@GetMapping("/storeInfo")
	@ApiOperation(value = "스토어 정보 가져오기 - wireframe의 '스토어 정보' 창에 있는 정보")
	public ResponseEntity<StoreInfoGet> searchStoreInfo(String email) {
		StoreInfoGet storeInfo = storeRepository.searchStoreInfo(email);
		
		Object score = storeRepository.searchStoreScore(email);	// 스토어 평점		
		if(score == null) storeInfo.setStore_score(0);
		else storeInfo.setStore_score(Math.round(Double.parseDouble(score.toString()) * 10) / 10.0);
		
		//긍정리뷰 추가해야함.
		int reviewCnt = storeRepository.reviewCount(email);
		int positiveCnt = storeRepository.positiveReviewCount(email);
		double percent = (double)((double)positiveCnt / (double)reviewCnt);
		storeInfo.setReview_percent((int)(percent*100));
		
		storeInfo.setReview_1(storeRepository.getReviewCount(1, email));
		storeInfo.setReview_2(storeRepository.getReviewCount(2, email));
		storeInfo.setReview_3(storeRepository.getReviewCount(3, email));
		storeInfo.setReview_4(storeRepository.getReviewCount(4, email));
		storeInfo.setReview_5(storeRepository.getReviewCount(5, email));
		
		return new ResponseEntity<StoreInfoGet>(storeInfo, HttpStatus.OK);
    }
	
	@PostMapping("/add")
	@ApiOperation(value = "스토어 추가 - Primary Key는 Email이며, Seller인 User에게만 노출되는 내 스토어 만들기 페이지가 있다고 생각하고 만듬")
	public ResponseEntity<String> addStore(@RequestBody Store store) {
		storeRepository.addStore(store);
		return new ResponseEntity<String>("success", HttpStatus.OK);
    }
	
	@PutMapping("/modify")
	@ApiOperation(value = "스토어 수정 - 수정하는 창 만들거야?")
	public ResponseEntity<String> modifyStore(@RequestBody Store store) {
		storeRepository.modifyStore(store);
		return new ResponseEntity<String>("success", HttpStatus.OK);
    }
	
	@GetMapping("/storeHome")
	@ApiOperation(value = "스토어 홈 정보 가져오기 - wireframe의 '스토어 홈' : 시세저렴, 베스트리뷰, 스토어인기 /// 시세저렴 구현 X ")
	public ResponseEntity<StoreHome> searchStoreHome(String email) {
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
    	
    	List<Store_ProductInfo> carrotList = storeRepository.CheaperProduct(email, curCarrotPrice, "당근");
    	List<Store_ProductInfo> garlicList = storeRepository.CheaperProduct(email, curGarlicPrice, "마늘");
    	List<Store_ProductInfo> baechuList = storeRepository.CheaperProduct(email, curBaechuPrice, "배추");
    	List<Store_ProductInfo> onionList = storeRepository.CheaperProduct(email, curOnionPrice, "양파");
    	
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
		
		
		StoreHome storeHome = new StoreHome(list, storeRepository.bestReview(email), storeRepository.PopularProductAll(email));
		return new ResponseEntity<StoreHome>(storeHome, HttpStatus.OK);
    }
	
	@GetMapping("/popular")
	@ApiOperation(value = "인기 상품 리스트 - wireframe의 '인기상품' 탭")
	public ResponseEntity<List<Store_ProductInfo>> searchPopularProduct(String email, int page) {
		int pgStart = 10 * (page - 1);
		List<Store_ProductInfo> list = storeRepository.PopularProduct(email, pgStart);
		return new ResponseEntity<List<Store_ProductInfo>>(list, HttpStatus.OK);
    }
	
	@GetMapping("/all")
	@ApiOperation(value = "전체 상품 리스트 - wireframe의 '전체 상품 목록' 탭")
	public ResponseEntity<List<Store_ProductInfo>> searchAllProduct(String email, int page) {
		int pgStart = 10 * (page - 1);
		List<Store_ProductInfo> list = storeRepository.allProduct(email, pgStart);
		return new ResponseEntity<List<Store_ProductInfo>>(list, HttpStatus.OK);
    }
	
	@GetMapping("/recommend")
	@ApiOperation(value = "스토어 추천 상품")
	public ResponseEntity<List<Store_ProductInfo>> searchRecommendProduct(String email, int userid, int page) throws Exception {
		System.out.println("스토어 추천 상품");
		System.out.println(userid);
    	String obj = restTemplate.getForObject("http://127.0.0.1:5000/productrec/store?seller="+email+"&userid=" + userid+"&num="+100, String.class);
    	System.out.println(obj);
    	System.out.println("obj : " + obj.equals("{}\n"));
    	System.out.println("obj : " + obj.equals("{'info': []}\n"));
    	List<Store_ProductInfo> productList = new ArrayList<>();
    	if(!obj.equals("{}\n")) {
	    	String res = decode(obj);
	    	System.out.println(res);
	    	JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
	    	JsonArray jsonArray = (JsonArray) jsonObject.get("info");
	    	if(jsonArray.size() != 0) {
		    	for(int i=0;i<jsonArray.size();i++) {
		    		JsonArray info = (JsonArray) jsonArray.get(i);
		    		int id = info.get(0).getAsInt();						// 상품 번호
		    		String name = info.get(1).getAsString();				// 상품 이름
		    		String img = info.get(2).isJsonNull() ? "" : info.get(2).getAsString();	// 상품 사진
		    		int price = info.get(3).getAsInt();						//상품 가격
		    		int kg = info.get(4).getAsInt();						//상품 kg
		    		Store_ProductInfo product = new Store_ProductInfo(id, name, img, price, kg);
		    		productList.add(product);
		    	}
	    	}
    	}

    	
    	List<Store_ProductInfo> addProductList = storeRepository.searchAllProduct(userid, email);
    	System.out.println(addProductList);
    	for(int i = 0; i < addProductList.size(); i++) {		
			boolean flag = true;
			for(int j = 0; j < productList.size(); j++) {
				if(addProductList.get(i).getId()  == productList.get(j).getId()) {
					flag = false;
					break;
				}
			}
			if(flag == true) {
				productList.add(addProductList.get(i));
			}
		}
    	
		System.out.println(productList);
		int pgStart = 10 * (page - 1);
		// Parsing한 데이터기 때문에 DB에서 페이징이 불가능. 자체 페이징 기능
    	List<Store_ProductInfo> newProductList = new ArrayList<>();
    	int idxStart = (page-1) * 10;
    	for(int i = idxStart; i < idxStart + 10; i++) {
    		if(i >= productList.size()) break;
    		newProductList.add(productList.get(i));
    	}
		return new ResponseEntity<List<Store_ProductInfo>>(newProductList, HttpStatus.OK);
    }
	
	@GetMapping("/cheap")
	@ApiOperation(value = "시세보다 저렴한 상품  리스트 - wireframe의 '시저상' 탭")
	public ResponseEntity<List<Store_ProductInfo>> searchCheaperProduct(String email) throws Exception {
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
    	
    	List<Store_ProductInfo> carrotList = storeRepository.CheaperProduct(email, curCarrotPrice, "당근");
    	List<Store_ProductInfo> garlicList = storeRepository.CheaperProduct(email, curGarlicPrice, "마늘");
    	List<Store_ProductInfo> baechuList = storeRepository.CheaperProduct(email, curBaechuPrice, "배추");
    	List<Store_ProductInfo> onionList = storeRepository.CheaperProduct(email, curOnionPrice, "양파");
    	
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

