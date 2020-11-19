package com.papple.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Store {
	private String email;
	private String store_name;
	private String rep_name;
	private String type;
	private String number;
	private String tell;
	private String origin;
	private String address;
}
