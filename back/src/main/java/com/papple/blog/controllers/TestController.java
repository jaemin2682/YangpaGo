//package com.papple.blog.controllers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.papple.blog.payload.response.RecipeInfo;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/test")
//
//public class TestController {
//	
//	@Autowired
//    RestTemplate restTemplate;
//	
//	
////	@GetMapping("/getSimilar")
////	public List<RecipeInfo> getSimilar(int recipe_no) throws Exception {
////		
////		String obj = restTemplate.getForObject("http://127.0.0.1:5000/?no=" + recipe_no, String.class);
////		String res = decode(obj);
////		
////		JsonObject jsonObject = (JsonObject) JsonParser.parseString(res);
////		JsonArray jsonArray = (JsonArray) jsonObject.get("info");
////		
////		List<RecipeInfo> recipeList = new ArrayList<>();
////		
////		for(int i=0;i<jsonArray.size();i++) {
////			JsonArray info = (JsonArray) jsonArray.get(i);
////			
////			int no = info.get(0).getAsInt();						// 레시피 번호
////			String name = info.get(1).getAsString();				// 레시피 이름
////			String picture = info.get(2).getAsString();				// 레시피 사진
////			
////			JsonArray ingredientArray = (JsonArray) info.get(3);	// 레시피 재료
////			List<String> ingredient = new ArrayList<>();
////			for(int j=0;j<ingredientArray.size();j++) ingredient.add(ingredientArray.get(j).getAsString());
////			
////			JsonArray amountArray = (JsonArray) info.get(4);	// 레시피 재료 수량
////			List<String> amount = new ArrayList<>();
////			for(int j=0;j<amountArray.size();j++) amount.add(amountArray.get(j).getAsString());
////			
////			JsonArray stepArray = (JsonArray) info.get(5);			// 레시피 과정
////			List<String> step = new ArrayList<>();
////			for(int j=0;j<stepArray.size();j++) step.add(stepArray.get(j).getAsString());
////			
////			RecipeInfo recipeInfo = new RecipeInfo(no, name, picture, ingredient, amount, step);
////			recipeList.add(recipeInfo);
////		}
////		
////		System.out.println(recipeList);
////		System.out.println(recipeList.size());
////		
////		return recipeList;
////	}
//	
//	
//	// 유니코드 디코더
//	public static String decode(String unicode) throws Exception {
//        StringBuffer str = new StringBuffer();
//
//        char ch = 0;
//        for( int i= unicode.indexOf("\\u"); i > -1; i = unicode.indexOf("\\u") ){
//            ch = (char)Integer.parseInt( unicode.substring( i + 2, i + 6 ) ,16);
//            str.append( unicode.substring(0, i) );
//            str.append( String.valueOf(ch) );
//            unicode = unicode.substring(i + 6);
//        }
//        str.append( unicode );
//
//        return str.toString();
//    }
//}
