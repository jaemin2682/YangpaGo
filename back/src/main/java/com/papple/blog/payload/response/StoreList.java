package com.papple.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreList {
	private String email;
	private String store_name;
	private String nickname;
}
