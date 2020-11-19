package com.papple.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreInfoGet {
	private String email;
	private String store_name;
	private String rep_name;
	private String type;
	private String number;
	private String tell;
	private String origin;
	private String address;
	private String nickname;
	private double store_score;
	private int review_percent;
	private int review_1;
	private int review_2;
	private int review_3;
	private int review_4;
	private int review_5;
	public StoreInfoGet(String email, String store_name, String rep_name, String type, String number, String tell,
			String origin, String address, String nickname) {
		super();
		this.email = email;
		this.store_name = store_name;
		this.rep_name = rep_name;
		this.type = type;
		this.number = number;
		this.tell = tell;
		this.origin = origin;
		this.address = address;
		this.nickname = nickname;
	}
}
