package project.graduation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.graduation.dto.AccountDTO;
import project.graduation.dto.ChangePublicProfileDTO;
import project.graduation.dto.ChangeUserPublicInfoDTO;
import project.graduation.dto.CreateUserForm;
import project.graduation.dto.ProfileDTO;
import project.graduation.dto.UserDTO;
import project.graduation.dto.filter.UserFilter;
import project.graduation.entity.LikeReview;
import project.graduation.entity.User;
import project.graduation.service.IReviewService;
import project.graduation.service.IUserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/users")
@Validated
public class UserController {
//	@Autowired(required = true)
//	private ICommentService commentService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IReviewService reviewService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping()
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<?> getAllUsers(Pageable pageable, @RequestParam(required = false) String search,
			UserFilter filter) {
		Page<User> entities = userService.getAllUsers(pageable, filter, search);

		List<AccountDTO> dtos = modelMapper.map(entities.getContent(), new TypeToken<List<AccountDTO>>() {
		}.getType());
		
		for (AccountDTO dto : dtos) {
			int numberOfReview = reviewService.numberOfReviewByUserId(dto.getId());
			int numberOfLike = reviewService.numberOfLikeByUserId(dto.getId());
			dto.setNumberOfReview(numberOfReview);
			dto.setNumberOfLike(numberOfLike);
		}

		Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entities.getTotalElements());

		return new ResponseEntity<>(dtoPages, HttpStatus.OK);
	}

	@GetMapping(value = "/roles")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<?> getAllRoles() {
		return new ResponseEntity<>(userService.getAllRoles(), HttpStatus.OK);
	}

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<?> existsUserByEmail(@PathVariable(name = "email") String email) {
		// get entity
		boolean result = userService.existsUserByEmail(email);

		// return result
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/userName/{userName}")
	public ResponseEntity<?> existsUserByUserName(@PathVariable(name = "userName") String userName) {
		// get entity
		boolean result = userService.existsUserByUserName(userName);

		// return result
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserForm form) {
		// create User
		userService.createUser(form.toEntity());

		return new ResponseEntity<>("We have sent an email. Please check email to active account!", HttpStatus.OK);
	}

	@GetMapping("/activeUser")
	// validate: check exists, check not expired
	public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {
		// active user
		userService.activeUser(token);

		return new ResponseEntity<>("Active success!", HttpStatus.OK);
	}

	// resend confirm
	@GetMapping("/userRegistrationConfirmRequest")
	// validate: email exists, email not active
	public ResponseEntity<?> resendConfirmRegistrationViaEmail(@RequestParam String email) {

		userService.sendConfirmUserRegistrationViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to active account!", HttpStatus.OK);
	}

	// reset password confirm
	@GetMapping("/resetPasswordRequest")
	// validate: email exists, email not active
	public ResponseEntity<?> sendResetPasswordViaEmail(@RequestParam String email) {

		userService.resetPasswordViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to reset password!", HttpStatus.OK);
	}

	// resend reset password
	@GetMapping("/resendResetPassword")
	// validate: email exists, email not active
	public ResponseEntity<?> resendResetPasswordViaEmail(@RequestParam String email) {

		userService.sendResetPasswordViaEmail(email);

		return new ResponseEntity<>("We have sent an email. Please check email to reset password!", HttpStatus.OK);
	}

	@GetMapping("/resetPassword")
	// validate: check exists, check not expired
	public ResponseEntity<?> resetPasswordViaEmail(@RequestParam String token, @RequestParam String newPassword) {

		// reset password
		userService.resetPassword(token, newPassword);

		return new ResponseEntity<>("Reset Password success!", HttpStatus.OK);
	}

	@GetMapping("/profile")
	// validate: check exists, check not expired
	public ResponseEntity<?> getUserProfile(Authentication authentication) {

		// get user name from token
		String username = authentication.getName();

		// get user info
		User user = userService.findUserByUserName(username);
		
		Date presentDate = new Date();

		int activeTime = (int) ((presentDate.getTime() - user.getActiveDate().getTime()) / 86400000);

		ProfileDTO profileDto = new ProfileDTO(user.getUserName(), user.getEmail(), user.getFirstName(),
				user.getLastName(), user.getRole().toString(), user.getStatus().toString(), user.getAvatarUrl(),
				user.getLiveIn(), user.getCountry());

		profileDto.setActiveDay(user.getActiveDate());
		profileDto.setActiveMonth(user.getActiveDate());
		profileDto.setActiveYear(user.getActiveDate());
		profileDto.setActiveTime(activeTime);
		profileDto.setPresentDay(presentDate);
		profileDto.setPresentMonth(presentDate);
		profileDto.setPresentYear(presentDate);
		profileDto.setPhoneNumber(user.getPhoneNumber(), user.getCountry());
		
		List<Integer> likes = new ArrayList<>();
		for (LikeReview likeReview : user.getLikes()) {
			likes.add(likeReview.getReview().getReviewId());
		}
		profileDto.setLikes(likes);

		return new ResponseEntity<>(profileDto, HttpStatus.OK);

	}

	@GetMapping("/userName")
	// validate: check exists, check not expired
	public ResponseEntity<?> findUserByUserName(@RequestParam String userName) {

		// get user info
		User user = userService.findUserByUserName(userName);

		ProfileDTO profileDto = new ProfileDTO(user.getUserName(), user.getEmail(), user.getFirstName(),
				user.getLastName(), user.getRole().toString(), user.getStatus().toString(), user.getAvatarUrl(),
				user.getLiveIn(), user.getCountry());

		profileDto.setPhoneNumber(user.getPhoneNumber(), user.getCountry());

		return new ResponseEntity<>(profileDto, HttpStatus.OK);
	}

	@GetMapping("/facebookID")
	// validate: check exists, check not expired
	public ResponseEntity<?> findUserByFacebookID(@RequestParam String facebookID) {

		// get user info
		User user = userService.findByFacebookID(facebookID);

		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		} else {
			// convert user entity to user dto
			ProfileDTO profileDto = new ProfileDTO(user.getUserName(), user.getEmail(), user.getFirstName(),
					user.getLastName(), user.getRole().toString(), user.getStatus().toString(), user.getAvatarUrl(),
					user.getLiveIn(), user.getCountry());

			profileDto.setPhoneNumber(user.getPhoneNumber(), user.getCountry());

			return new ResponseEntity<>(profileDto, HttpStatus.OK);
		}
	}

	@GetMapping("/resetImportantInfoRequest")
	// validate: email exists, email not active
	public ResponseEntity<?> sendResetImportantInfoPasswordViaEmail(@RequestParam String email) {

		userService.sendResetImportantInfoViaEmail(email);

		return new ResponseEntity<>("We have sent OTP. Please check email!", HttpStatus.OK);
	}

	@PutMapping("/profile")
	// validate: check exists, check not expired
	public ResponseEntity<?> changeUserProfile(Authentication authentication, @RequestBody ChangePublicProfileDTO dto) {
		// get user name from token
		String username = authentication.getName();

		try {
			userService.changeUserProfile(username, dto);
			System.err.println(dto.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return new ResponseEntity<>("Change Profile Successfully!", HttpStatus.OK);
	}

	@PutMapping("/changeRole")
	@PreAuthorize("hasAuthority('Admin')")
	// validate: check exists, check not expired
	public ResponseEntity<?> changeUserRole(@RequestBody ChangePublicProfileDTO dto) {

		userService.changeUserRole(dto.getUserName(), dto.getRole());

		return new ResponseEntity<>("Change Profile Successfully!", HttpStatus.OK);
	}

	// ===== Adding day 09-06-22 ===== //
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(name = "id") int id) {
		User user = userService.getUserById(id);
		UserDTO dto = modelMapper.map(user, UserDTO.class);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{ids}")
	public ResponseEntity<?> deleteUsers(@PathVariable(name = "ids") List<Integer> ids) {
		userService.deleteUsers(ids);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
	}
	
	// ===== Adding day 19-09-22 ===== //
	@PutMapping(value = "/publicInfo")
	public ResponseEntity<?> changeUserPublicInfo(Authentication authentication, @RequestBody ChangeUserPublicInfoDTO dto) {
		userService.changeUserPublicInfo(authentication.getName(), dto);
		return new ResponseEntity<String>("Changed public info successfully!", HttpStatus.OK);
	}
	
	// ===== Adding 03/10/22 ===== //
	@GetMapping("/resetPasswordOTP")
	public ResponseEntity<?> sendOTPViaEmail(@RequestParam String email) {
		String newOTP = userService.sendOTPViaEmail(email);

		return new ResponseEntity<String>(newOTP, HttpStatus.OK);
	}
	
	
}
