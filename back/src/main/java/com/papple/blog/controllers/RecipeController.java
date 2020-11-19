package com.papple.blog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.papple.blog.payload.response.RecipeDetail;
import com.papple.blog.payload.response.RecipeHome;
import com.papple.blog.payload.response.RecipeInfo;
import com.papple.blog.repository.RecipeRepository;

import io.swagger.annotations.ApiOperation;

// http://localhost:8081/swagger-ui.html
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
	@Autowired
    private RecipeRepository recipeRepository;
	@Autowired
    RestTemplate restTemplate;

	@GetMapping("/all")
	@ApiOperation(value = "레시피 전체 목록 - wireframe의 '레시피 전체 목록'탭 : 페이징 on")
	public ResponseEntity<List<RecipeInfo>> searchRecipeAll(int page) {
		int pgStart = 10 * (page - 1);
		List<RecipeInfo> list = recipeRepository.searchRecipeAll(pgStart);
    	return new ResponseEntity<List<RecipeInfo>>(list, HttpStatus.OK);
    }
	
	@GetMapping("/popular")
	@ApiOperation(value = "인기 레시피 목록 - wireframe의 '인기레시피'탭 : 페이징 on")
	public ResponseEntity<List<RecipeInfo>> searchPopularRecipe(int page) {
		int pgStart = 10 * (page - 1);
		List<RecipeInfo> list = recipeRepository.searchPopularRecipe(pgStart);
    	return new ResponseEntity<List<RecipeInfo>>(list, HttpStatus.OK);
    }
	
    @GetMapping("/detail")
	@ApiOperation(value = "레시피 상세 정보 가져오기 - 레시피정보 + 관련상품 + 추천 레시피  & View UserLog에 기록(유저기반 추천에 이용. 따라서 이메일 필요) & recipe 점수 1점 증가")
	public ResponseEntity<RecipeDetail> searchRecipeById(int id, String email) throws Exception {
    	RecipeDetail detail = recipeRepository.searchRecipeBasic(id);
    	detail.setIngredient(recipeRepository.searchRecipeIngredient(id));	// 재료 set
    	detail.setStep(recipeRepository.searchRecipeStep(id));				// 조리과정 set
    	detail.setRelative_product(recipeRepository.relativeProduct(id));	// 관련 상품 set
    	
    	// 추천 레시피 set
    	String obj = restTemplate.getForObject("http://127.0.0.1:5000/reciperec?no=" + id, String.class);
    	String res = decode(obj);
    	JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
    	JsonArray jsonArray = (JsonArray) jsonObject.get("info");
    	List<RecipeInfo> recipeList = new ArrayList<>();
    	for(int i=0;i<jsonArray.size();i++) {
    		JsonArray info = (JsonArray) jsonArray.get(i);
    		int no = info.get(0).getAsInt();						// 레시피 번호
    		String name = info.get(1).getAsString();				// 레시피 이름
    		String picture = info.get(2).getAsString();				// 레시피 사진
    		RecipeInfo recipeInfo = new RecipeInfo(no, name, picture);
    		recipeList.add(recipeInfo);
    	}
    	List<RecipeInfo> recRecipe = new ArrayList<>();
    	for(int i=0;i<4;i++) recRecipe.add(recipeList.get(i));
    	detail.setRecommend_recipe(recRecipe);
    	detail.setIsGood(recipeRepository.isGood(email, id));
  
    	recipeRepository.upScoreOne(id);				// 점수 증가
    	recipeRepository.insertRecipeView(id, email);	// 조회 기록
    	
    	return new ResponseEntity<RecipeDetail>(detail, HttpStatus.OK);
    }
        
    // 유저 로그 기반으로 만들어야함. 로그 없을땐 어떻게? 과잉 생산물 기반으로. 로그 충분할 떈 로그 기반 추천.
    @GetMapping("/recommend")
  	@ApiOperation(value = "추천 레시피 목록 (User Log 기반) - wireframe의 '추천 레시피'탭 : 페이징 on")
    public ResponseEntity<List<RecipeInfo>> searchRecommendRecipe(String email, int page) throws Exception {
    	List<RecipeInfo> recipeList = new ArrayList<>();
		
		String obj = restTemplate.getForObject("http://127.0.0.1:5000/recipebyuserlog?email=" + email, String.class);
    	String res = decode(obj);
    	System.out.println(res);
    	JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
    	JsonArray jsonArray = (JsonArray) jsonObject.get("info");
    	for(int i=0;i<jsonArray.size();i++) {
    		JsonArray info = (JsonArray) jsonArray.get(i);
    		System.out.println(info);
    		int no = info.get(0).getAsInt();						// 레시피 번호
    		String name = info.get(1).getAsString();				// 레시피 이름
    		String picture = info.get(2).getAsString();				// 레시피 사진
    		RecipeInfo recipeInfo = new RecipeInfo(no, name, picture);
    		recipeList.add(recipeInfo);
    	}
    	
    	// Parsing한 데이터기 때문에 DB에서 페이징이 불가능. 자체 페이징 기능
    	List<RecipeInfo> newRecipeList = new ArrayList<>();
    	int idxStart = (page-1) * 10;
    	for(int i = idxStart; i < idxStart + 10; i++) {
    		if(i >= recipeList.size()) break;
    		newRecipeList.add(recipeList.get(i));
    	}
        return new ResponseEntity<List<RecipeInfo>>(newRecipeList, HttpStatus.OK);
    }
    
    @GetMapping("/crawling_Insert")
    @ApiOperation(value = "Do not touch : 보내는 순간 DB 싹 다 지워야함. 재미니용 ^*^ 호기심 금지")
	public ResponseEntity<String> crawling(String product) throws Exception {
		String obj = restTemplate.getForObject("http://127.0.0.1:5000/crawling?product=" + product, String.class);
		System.out.println(obj);
		return new ResponseEntity<String>(obj, HttpStatus.OK);
	}
    
    @PostMapping("/myrecipe")
    @ApiOperation(value = "My CookBook에 추가 & recipe 점수 3점 증가")
    public ResponseEntity<String> insertMyRecipe(int id, String email) {
    	recipeRepository.insertMyRecipe(id, email);
    	recipeRepository.upScoreThree(id);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
    
    @DeleteMapping("/myrecipe")
    @ApiOperation(value = "My CookBook에서 삭제")
    public ResponseEntity<String> deleteMyRecipe(int id, String email) {
    	recipeRepository.deleteMyRecipe(id, email);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
    
    @GetMapping("/myrecipe")
    @ApiOperation(value = "My CookBook List 조회 - wireframe의 '나의 레시피 북'탭")
    public ResponseEntity<List<RecipeInfo>> searchMyRecipe(String email) {
		return new ResponseEntity<List<RecipeInfo>>(recipeRepository.searchMyRecipe(email), HttpStatus.OK);
	}
    
    @GetMapping("/search")
    @ApiOperation(value = "레시피 이름으로 조회")
    public ResponseEntity<List<RecipeInfo>> search(String word, int page) {
    	int pgStart = 10 * (page - 1);
		return new ResponseEntity<List<RecipeInfo>>(recipeRepository.search(word, pgStart), HttpStatus.OK);
	}
    
    @GetMapping("/recipeHome")
    @ApiOperation(value = "레시피 홈 - wireframe의 '레시피 홈'탭 : 추천레시피, 인기 레시피 - 요청대로 페이징 없이 전부 다 조회")
    public ResponseEntity<RecipeHome> recipeHome(String email) throws Exception {
    	List<RecipeInfo> popularList = recipeRepository.searchPopularRecipeAll();
    	List<RecipeInfo> list = new ArrayList<>();
    	for(int i=0;i<3;i++) list.add(popularList.get(i));
    	RecipeHome recipeHome = new RecipeHome(null, list);
    	List<RecipeInfo> recipeList = new ArrayList<>();
		String obj = restTemplate.getForObject("http://127.0.0.1:5000/recipebyuserlog?email=" + email, String.class);
    	String res = decode(obj);
    	System.out.println(res);
    	JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
    	JsonArray jsonArray = (JsonArray) jsonObject.get("info");
    	for(int i=0;i<3;i++) {		// 3개만 추출해주자.
    		JsonArray info = (JsonArray) jsonArray.get(i);
    		System.out.println(info);
    		int no = info.get(0).getAsInt();						// 레시피 번호
    		String name = info.get(1).getAsString();				// 레시피 이름
    		String picture = info.get(2).getAsString();				// 레시피 사진
    		RecipeInfo recipeInfo = new RecipeInfo(no, name, picture);
    		recipeList.add(recipeInfo);
    	}
    	recipeHome.setRecommend_recipe(recipeList);
    	
		return new ResponseEntity<RecipeHome>(recipeHome, HttpStatus.OK);
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

    

    
// 나중에 지울거
//    @GetMapping("/similar")
//    @ApiOperation(value = "레시피 상세 정보 - 추천 레시피")
//	public ResponseEntity<List<RecipeInfo>> getSimilar(int id) throws Exception {
//		
//		String obj = restTemplate.getForObject("http://127.0.0.1:5000/reciperec?no=" + id, String.class);
//		String res = decode(obj);
//		System.out.println(res);
//		JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
//		JsonArray jsonArray = (JsonArray) jsonObject.get("info");
//		
//		List<RecipeInfo> recipeList = new ArrayList<>();
//		
//		for(int i=0;i<jsonArray.size();i++) {
//			JsonArray info = (JsonArray) jsonArray.get(i);
//			
//			int no = info.get(0).getAsInt();						// 레시피 번호
//			String name = info.get(1).getAsString();				// 레시피 이름
//			String picture = info.get(2).getAsString();				// 레시피 사진
//			
//			JsonArray ingredientArray = (JsonArray) info.get(3);	// 레시피 재료
//			List<String> ingredient = new ArrayList<>();
//			for(int j=0;j<ingredientArray.size();j++) ingredient.add(ingredientArray.get(j).getAsString());
//			
//			JsonArray amountArray = (JsonArray) info.get(4);	// 레시피 재료 수량
//			List<String> amount = new ArrayList<>();
//			for(int j=0;j<amountArray.size();j++) amount.add(amountArray.get(j).getAsString());
//			
//			JsonArray stepArray = (JsonArray) info.get(5);			// 레시피 과정
//			List<String> step = new ArrayList<>();
//			for(int j=0;j<stepArray.size();j++) step.add(stepArray.get(j).getAsString());
//			
//			RecipeInfo recipeInfo = new RecipeInfo(no, name, picture, ingredient, amount, step);
//			recipeList.add(recipeInfo);
//		}
//		return new ResponseEntity<List<RecipeInfo>>(recipeList, HttpStatus.OK);
//	}

}

