package com.papple.blog.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	private int id;
	private String seller;
	private String name;
	private int kg;
	private int price;
	private String img;
	private String content;
	private int isb;
	private String status;
	private String delevery_method;
	private String delevery_location;
	private int delevery_price;
	private String life_time;
	private String origin;
	private LocalDateTime date;
	private String law_origin;
	private String keep_method;
	private int score;
	private List<String> product_img;
	
	
	public Product() {
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", seller=" + seller + ", name=" + name + ", kg=" + kg + ", price=" + price
				+ ", img=" + img + ", content=" + content + ", isb=" + isb + ", status=" + status + ", delevery_method="
				+ delevery_method + ", delevery_location=" + delevery_location + ", delevery_price=" + delevery_price
				+ ", life_time=" + life_time + ", origin=" + origin + ", date=" + date + ", law_origin=" + law_origin
				+ ", keep_method=" + keep_method + ", score=" + score + ", product_img=" + product_img + "]";
	}
	
}
