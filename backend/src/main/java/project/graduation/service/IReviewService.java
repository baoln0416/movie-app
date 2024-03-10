package project.graduation.service;

import java.util.List;

import org.springframework.stereotype.Component;

import project.graduation.dto.ReviewFormCreate;
import project.graduation.dto.ReviewUpdateForm;
import project.graduation.entity.Review;

@Component
public interface IReviewService {

	public Review getByReviewID(int id);

	public void updateReview(int id, ReviewUpdateForm form);

	public List<Review> getListReviewByMovieID(int id);

	public List<Review> getListReviewByUserID(int id);

	void createReview(ReviewFormCreate comment);

	public void deleteReview(int id);
	
	public void likeReview(String userName, int reviewId);
	
	public void dislikeReview(String userName, int reviewId);
	
	public int numberOfReviewByUserId(int userId);
	
	public int numberOfLikeByUserId(int userId);
}
