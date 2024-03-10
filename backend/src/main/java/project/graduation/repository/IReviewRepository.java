package project.graduation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.graduation.entity.Review;

public interface IReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {

	public List<Review> findByMovieId(int movieId);

	public List<Review> findByUserId(int userId);

	public Review findByReviewId(int reviewId);
	
	@Query(value = "SELECT COUNT(`review_id`) FROM `user_review` WHERE `user_id` = :parameter", nativeQuery = true)
	public String numberOfReviewByUserId(@Param("parameter") int userId);
	
	@Query(value = "SELECT SUM(`number_of_like`) FROM `user_review` WHERE `user_id` = :parameter", nativeQuery = true)
	public String numberOfLikeByUserId(@Param("parameter") int userId);
}