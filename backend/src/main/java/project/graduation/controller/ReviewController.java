package project.graduation.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.graduation.dto.ReviewDTO;
import project.graduation.dto.ReviewFormCreate;
import project.graduation.dto.ReviewUpdateForm;
import project.graduation.entity.LikeReview;
import project.graduation.entity.Review;
import project.graduation.service.IReviewService;

@RestController
@RequestMapping(value = "api/v1/reviews")
@CrossOrigin("*")
public class ReviewController {

	@Autowired
	private IReviewService reviewService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCommentByID(@PathVariable(name = "id") int id) {
		Review review = reviewService.getByReviewID(id);
		ReviewDTO dto = new ReviewDTO();
		dto.setReviewId(review.getReviewId());
		dto.setMovieID(id);
		dto.setUserName(review.getUser().getUserName());
		dto.setScore(review.getScore());
		dto.setNumberOfLike(review.getNumberOfLike());
		dto.setComment(review.getComment());
		dto.setUploadDay(review.getUpdateDate());
		dto.setUploadMonth(review.getUpdateDate());
		dto.setUploadYear(review.getUpdateDate());
		
		List<String> likeByUsers = new ArrayList<String>();
		for (LikeReview likeReview : review.getLikes()) {
			likeByUsers.add(likeReview.getUser().getUserName());
		}
		dto.setLikeByUsers(likeByUsers);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/movieId/{id}")
	public ResponseEntity<?> getCommentByMovieID(@PathVariable(name = "id") int id) {
		List<Review> reviews = reviewService.getListReviewByMovieID(id);

		List<ReviewDTO> dtos = new ArrayList<ReviewDTO>();
		for (Review review : reviews) {
			ReviewDTO dto = new ReviewDTO();
			dto.setReviewId(review.getReviewId());
			dto.setComment(review.getComment());
			dto.setMovieID(review.getMovie().getId());
			dto.setUserName(review.getUser().getUserName());
			dto.setUserRole(review.getUser().getRole().toString());
			dto.setUserAvatar(review.getUser().getAvatarUrl());
			dto.setScore(review.getScore());
			dto.setNumberOfLike(review.getNumberOfLike());
			
			List<String> likeByUsers = new ArrayList<String>();
			for (LikeReview likeReview : review.getLikes()) {
				likeByUsers.add(likeReview.getUser().getUserName());
			}
			dto.setLikeByUsers(likeByUsers);
			
			dto.setUploadDay(review.getUpdateDate());
			dto.setUploadMonth(review.getUpdateDate());
			dto.setUploadYear(review.getUpdateDate());
			
			dtos.add(dto);
		}
		Collections.reverse(dtos);

		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> createComment(@RequestBody ReviewFormCreate form) {
		reviewService.createReview(form);
		return new ResponseEntity<String>("Create successfully!", HttpStatus.OK);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateComment(@PathVariable(name = "id") int id, @RequestBody ReviewUpdateForm form) {
		reviewService.updateReview(id, form);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable(name = "id") int id) {
		reviewService.deleteReview(id);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
	}
	
	@PostMapping(value = "/likeReview")
	public ResponseEntity<?> likeReview(@RequestParam String userName, @RequestParam int reviewId) {
		reviewService.likeReview(userName, reviewId);
		return new ResponseEntity<String>("You have gived a like to a comment", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/dislikeReview")
	public ResponseEntity<?> dislikeReview(Authentication authentication, @RequestParam int reviewId) {
		reviewService.dislikeReview(authentication.getName(), reviewId);
		return new ResponseEntity<String>("You have disliked comment", HttpStatus.OK);
	} 
}
