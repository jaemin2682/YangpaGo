package com.papple.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPicture {
	int product_id;
	String path;
	
	public ProductPicture() {
	}
	
	public int getProductId() {
		return this.product_id;
	}
	
	public void setProductId(int product_id) {
		this.product_id = product_id;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ProductPicture [product_id=" + product_id + ", path=" + path + "]";
	}

}
