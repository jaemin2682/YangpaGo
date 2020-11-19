package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.CartProduct;
import com.papple.blog.payload.response.Product;
import com.papple.blog.payload.response.ProductInfo;
import com.papple.blog.payload.response.ProductList;
import com.papple.blog.payload.response.ProductPicture;
import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.RecipeView;
import com.papple.blog.payload.response.StoreList;
import com.papple.blog.payload.response.Store_ProductInfo;
import com.papple.blog.payload.response.ViewProduct;

@Mapper
public interface MainRepository{
	List<ProductInfo> searchProductByName(String word, int page);
	List<StoreList> searchStoreByName(String word);
	List<ViewProduct> searchProductViewByEmail(String email);
	List<RecipeView> searchRecipeViewByEmail(String email);
	List<ProductInfo> searchBProduct();
	List<ProductInfo> searchAllProduct();
	List<ProductInfo> searchTwoProduct(int userid);
	List<ProductInfo> searchNumProduct(int userid,int num);
	List<Store_ProductInfo> CheaperProduct(int price, String type);
}
