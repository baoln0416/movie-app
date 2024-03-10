package project.graduation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.graduation.dto.MovieDTO;
import project.graduation.entity.User;
import project.graduation.entity.UserMovie;
import project.graduation.service.IUserMovieService;
import project.graduation.service.IUserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/subscribe")
public class UserMovieController {

	@Autowired
	private IUserMovieService service;

	@Autowired
	private IUserService userService;

	@GetMapping()
	public ResponseEntity<?> getListSubscribedMovies(Authentication authentication) {
		User user = userService.findUserByUserName(authentication.getName());

		List<UserMovie> userMovies = service.getListSubscribedMovies(user.getId());

		List<MovieDTO> subscribedMovies = new ArrayList<MovieDTO>();

		for (UserMovie userMovie : userMovies) {
			MovieDTO subscribedMovie = new MovieDTO();
			subscribedMovie.setId(userMovie.getMovie().getId());
			subscribedMovie.setName(userMovie.getMovie().getName());
			;
			subscribedMovie.setPoster(userMovie.getMovie().getPoster());
			subscribedMovie.setScore(userMovie.getMovie().getScore());
			subscribedMovie.setDirector(userMovie.getMovie().getDirector().getName());
			subscribedMovie.setCountry(userMovie.getMovie().getCountry());
			subscribedMovie.setDuration(userMovie.getMovie().getDuration());

			subscribedMovies.add(subscribedMovie);
		}

		return new ResponseEntity<List<MovieDTO>>(subscribedMovies, HttpStatus.OK);
	}

	@PostMapping(value = "/{movieId}")
	public ResponseEntity<?> subscribesAMovie(@PathVariable(name = "movieId") int movieId, Authentication authen) {
		User user = userService.findUserByUserName(authen.getName());
		service.subscribesAMovie(user, movieId);
		return new ResponseEntity<String>("Subsrcibe successfully!", HttpStatus.OK);
	}

	@GetMapping(value = "/{movieId}")
	public ResponseEntity<?> isMovieIsSubscribed(@PathVariable(name = "movieId") int movieId, Authentication authen) {
		User user = userService.findUserByUserName(authen.getName());
		return new ResponseEntity<>(service.isMovieIsSubscribed(user.getId(), movieId), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{movieId}")
	public ResponseEntity<?> unsubscribed(@PathVariable(name = "movieId") int movieId, Authentication authen) {
		User user = userService.findUserByUserName(authen.getName());
		service.unsubscribe(user.getId(), movieId);
		return new ResponseEntity<>("Deleted successfully!", HttpStatus.OK);
	}
}
