package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecentProductList {
	int id;
	String name;
	int price;
	String img;
	int kg;
	String store_name;
}
