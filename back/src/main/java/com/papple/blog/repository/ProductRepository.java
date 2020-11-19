package com.papple.blog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.papple.blog.payload.response.BestReviewOne;
import com.papple.blog.payload.response.CartProduct;
import com.papple.blog.payload.response.Product;
import com.papple.blog.payload.response.ProductPicture;
import com.papple.blog.payload.response.PurchaseProduct;
import com.papple.blog.payload.response.ReviewByProductDetail;
import com.papple.blog.payload.response.ViewProduct;

@Mapper
public interface ProductRepository{
	List<Product> searchAllProduct();
	List<Product> searchByName(String name);
	Product searchById(int id);
	List<Product> searchByStatus(String status);
	int addProduct(Product product);
	int delete(int id);
	int update(Product product);
	List<ProductPicture> searchPictureById(int id);
	int addProductPicture(ProductPicture productPicture);
	int deletePicture(int id);
	int updatePicture(ProductPicture productPicture);
	BestReviewOne bestReviewOne(int product_id);
	List<ReviewByProductDetail> ReviewListProductDetail(int product_id);
	
	//auto_increment
	Object auto();
}
