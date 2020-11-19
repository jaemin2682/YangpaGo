package com.papple.blog.controllers;
//import java.lang.StackWalker.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import com.papple.blog.models.ERole;
import com.papple.blog.models.Role;
import com.papple.blog.models.User;
import com.papple.blog.payload.request.LoginRequest;
import com.papple.blog.payload.request.SignupRequest;
import com.papple.blog.payload.request.UpdateRequest;
import com.papple.blog.payload.response.JwtResponse;
import com.papple.blog.payload.response.MessageResponse;
import com.papple.blog.repository.AuthRepository;
import com.papple.blog.repository.ProductRepository;
import com.papple.blog.repository.RoleRepository;
import com.papple.blog.repository.UserRepository;
import com.papple.blog.security.jwt.JwtUtils;
import com.papple.blog.security.services.UserDetailsImpl;

@CrossOrigin(origins = {"http://j3a404.p.ssafy.io", "http://localhost:8080" },allowedHeaders = "*",allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	AuthRepository authRepository;
	@Autowired
	ProductRepository productRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
 
	@PostMapping("/signin")
	@ApiOperation(value = "로그인")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		// 이메일 인증 받은 회원인지
		User userauth = userRepository.getUserByEmail(loginRequest.getEmail());
		if(userauth.getUsercertification() == 0){
			return ResponseEntity.badRequest().body("User authentication failed.");
		}

		// userType 검사
		User user = userRepository.findById(loginRequest.getEmail()).get();
		System.out.println("User : " + user);
		System.out.println("Request : " + loginRequest);
		if(user.getType() != loginRequest.getType()) return ResponseEntity.badRequest().body("User Type is not Matching");

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
												userDetails.getEmail(), 
												 userDetails.getUsername(), 
												 roles));
	}

	
	@PostMapping("/signup")
	@ApiOperation(value = "회원 가입")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		// Create new user's account
		User user = new User(signUpRequest.getEmail(), signUpRequest.getNickname(),
							encoder.encode(signUpRequest.getPassword()), signUpRequest.getType());
		System.out.println("유저 정보 출력");
		System.out.println(signUpRequest);
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		if(productRepository.auto() == null) user.setId(1);
		else {
			System.out.println((int)productRepository.auto() + 1);
			user.setId((int)productRepository.auto() + 1);
		}
		userRepository.save(user);
		userRepository.updateAuth(1, user.getEmail());	// 인증 절차 없이 바로 넣어주자
//		sendMail(signUpRequest.getEmail());				// 이메일 안보내줘도 됌
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/checkEmailDuplication")
	@ApiOperation(value = "이메일 중복 체크")
	public ResponseEntity<?> checkEmailDuplication(@RequestParam(required = true) final String email){
		 if (userRepository.existsById(email)) {
		 	return ResponseEntity
		 			.badRequest()
		 			.body(new MessageResponse("Error: Email is already in use!"));
		 }
		 return ResponseEntity.ok().body(new MessageResponse("Success"));
	}

	@GetMapping("/unregister")
	@ApiOperation(value = "회원탈퇴")
	public ResponseEntity<?> unregister(@RequestParam(required = true) final String email) {     
		userRepository.deleteById(email);				// 회원 삭제
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
	} 

	@GetMapping("/userInfo")
	@ApiOperation(value = "회원정보 조회")
    public ResponseEntity<?> userInfo(@RequestParam(required = true) final String email) {
		User user = userRepository.getUserByEmail(email);
		// User user = userRepository.getOne(email); 
		return ResponseEntity.ok(user);
	}

//	@PostMapping("/passwordUpdate")
//	@ApiOperation(value="계정 설정 [비밀번호]")
//	public ResponseEntity<?> passwordUpdate(@Valid @RequestBody UpdateRequest updateRequest){
//
//		User user = userRepository.getUserByEmail(updateRequest.getEmail());
//
//		// 사용자 인증
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(updateRequest.getEmail(), updateRequest.getPassword()));
//
//		// 인증된 사용자인 경우 업데이트
//		userRepository.resetPassword(encoder.encode(updateRequest.getNewpassword()), updateRequest.getEmail());	
//		return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
//
//	}

	@PostMapping("/resetPassword")
	@ApiOperation(value = "비밀번호 재설정")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody LoginRequest loginRequest) {

		userRepository.resetPassword(encoder.encode(loginRequest.getPassword()), loginRequest.getEmail());

		return ResponseEntity.ok(new MessageResponse("Password registered successfully!"));
	}
	
	
//	@PostMapping("/profile")
//	@ApiOperation(value = "path 변수가 비었을 때는 서버에 파일 저장 + 유저 대표사진 update + profile history 등록,  path가 있을 때는 대표사진만 update")
//	public ResponseEntity<String> fileUpload(@RequestParam("filename") MultipartFile mFile, @RequestParam String email, 
//			@RequestParam(required = false) String path, HttpServletRequest request) {
//		if(path == null || path.equals("")) {	// path 변수가 안들어오면 (새 첨부 파일로 대표이미지를 등록하면(
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//			Date nowdate = new Date();
//			String dateString = formatter.format(nowdate);	//현재시간 문자열
//			
//			String real_path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/profile/" + 
//					dateString + "_" + mFile.getOriginalFilename();	//경로 + 날짜시간 + _ +파일이름으로 저장
//			String access_path = "http://i3a604.p.ssafy.io/images/profile/" + dateString + "_" + mFile.getOriginalFilename();
//			try {
//				mFile.transferTo(new File(real_path));					// 서버에 파일 저장
//				userRepository.updateProfile(access_path, email);		// 유저 대표사진 update
//				profileRepository.insertProfile(email, access_path);	// profile history 등록
//				return new ResponseEntity<String>(access_path, HttpStatus.OK);
//			} catch(Exception e) {
//				System.out.println("파일 업로드 실패");
//				return new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
//			}
//		}
//		else {		//path 변수가 들어오면
//			userRepository.updateProfile(path, email);		// 유저 대표사진 updatedd
//			profileRepository.updateProfile(email, path);	// 시간 재등록
//			return new ResponseEntity<String>(path, HttpStatus.OK);
//		}
//		
//		
//	}
	
//	@DeleteMapping("/delprofile")
//	@ApiOperation(value = "서버에 있는 프로필 사진 파일을 삭제 + 프로필 히스토리에서 삭제")
//	public ResponseEntity<String> fileDelete(String filePath, String email) {
//		
//		User user = userRepository.getUserByEmail(email);
//		
//		if(!user.getProfile().equals(filePath)) {
//			String tem = filePath.replace("/profile", "+");
//			StringTokenizer st = new StringTokenizer(tem, "+");
//			
//			String prev = st.nextToken();	//http://i3a604.p.ssafy.io/images
//			String next = st.nextToken();	///"/" + dateString + "_" + mFile.getOriginalFilename();
//			
//			String path = "/home/ubuntu/s03p13a604/back/src/main/webapp/resources/profile" + next;
//			
//			File delFile = new File(path);
//			if(delFile.exists()) delFile.delete();		//해당 path의 서버의 파일 삭제
//		}
//		
//		System.out.println(profileRepository.deleteProfile(email, filePath));	// 프로필 히스토리에서 삭제
//		
//		return new ResponseEntity<String>("success", HttpStatus.OK);
//	}
//	
//	@PutMapping("/unprofile")
//	@ApiOperation(value = "프로필 사진을 기본 사진으로 setting")
//	public ResponseEntity<String> fileUnUpload(String email) {
//		userRepository.deleteProfile(email);
////		profileRepository.unProfile(email);
//		return new ResponseEntity<String>("success", HttpStatus.OK);
//	}

//	@GetMapping("/notificationSetting")
//	@ApiOperation(value = "알림설정")
//	public ResponseEntity<String> getMethodName(@RequestParam final String email, 
//															@RequestParam String notification) {
//
//		User user = userRepository.getUserByEmail(email);
//		user.setNotification(notification);	// 1111111  (1은 ON, 0은 OFF)
//		userRepository.save(user);
//
//		return new ResponseEntity<String>("success", HttpStatus.OK);
//	}
	
//	@GetMapping("/loginSuccess")
//	public RedirectView loginSucess(HttpSession httpSession){
//		SessionUser user = (SessionUser) httpSession.getAttribute("user");
//		User checkUser =  userRepository.getUserByEmail(user.getEmail());
//
//		// 처음 로그인하는 계정인 경우 
//		if(checkUser.getUsercertification()==null){
//			checkUser.setUsercertification(1);
//			userRepository.save(checkUser);
//			BlogConfig config = new BlogConfig(user.getEmail(), "개발자의 블로그", "개발자의 블로그 입니다.", 
//			"http://i3a604.p.ssafy.io/images/blogRep/blog_basic.jpg");
//			configRepository.save(config);
//
//			return new RedirectView("http://i3a604.p.ssafy.io/account/setNickname/"+user.getEmail());
//
//		} else{
//
//			return new RedirectView("http://i3a604.p.ssafy.io/main/home/"+user.getEmail());
//			
//		}	
//		
//	}
}

