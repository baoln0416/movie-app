package project.graduation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.graduation.dto.UserActivityCreate;
import project.graduation.entity.User;
import project.graduation.service.IUserActivitiesService;
import project.graduation.service.IUserService;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/activities")
public class UserActivitiesController {

	@Autowired
	private IUserActivitiesService service;

	@Autowired
	private IUserService userService;

	@GetMapping()
	public ResponseEntity<?> getActivitiesFromUser(Authentication authentication) {

		User user = userService.findUserByUserName(authentication.getName());

		return new ResponseEntity<>(service.getActivitiesFromUser(user), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> createActivity(@RequestBody UserActivityCreate activity, Authentication authen) {
		User user = userService.findUserByUserName(authen.getName());
		service.createActivity(activity, user);
		return new ResponseEntity<String>("Save activity successfully!", HttpStatus.OK);
	}
}
