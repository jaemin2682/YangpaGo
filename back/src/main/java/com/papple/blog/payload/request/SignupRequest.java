package com.papple.blog.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.Data;
 
@Data
public class SignupRequest {
    @NotBlank
    @Email
    private String email;
    private String nickname;
    private int type;
    private Set<String> role;
    
    @NotBlank
    private String password;
}
