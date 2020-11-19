package com.papple.blog.payload.response;

import lombok.Data;

@Data
public class MyInfo {
	private int type;
	private String email;
	private int isStore;
	
	public MyInfo(int type, String email) {
		this.type = type; this.email = email;
	}
}
