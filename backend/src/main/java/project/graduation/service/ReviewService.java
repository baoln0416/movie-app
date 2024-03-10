package project.graduation.service;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.graduation.dto.ReviewFormCreate;
import project.graduation.dto.ReviewUpdateForm;
import project.graduation.entity.Review;
import project.graduation.entity.LikeReview;
import project.graduation.entity.Movie;
import project.graduation.entity.User;
import project.graduation.repository.ILikeReviewRepository;
import project.graduation.repository.IMovieRepository;
import project.graduation.repository.IReviewRepository;

@Service
public class ReviewService implements IReviewService {

	@Autowired
	private IReviewRepository repository;

	@Autowired
	private IMovieRepository movieRepository;

	@Autowired(required = true)
	private IMovieService movieService;

	@Autowired(required = true)
	private IUserService userService;
	
	@Autowired
	private ILikeReviewRepository likeReviewRepository;

	public Review getByReviewID(int id) {
		return repository.findByReviewId(id);
	}

	public List<Review> getListReviewByMovieID(int id) {
		return repository.findByMovieId(id);
	}

	public List<Review> getListReviewByUserID(int id) {
		return repository.findByUserId(id);
	}

	public void createReview(ReviewFormCreate form) {
		Movie movie = movieService.getMovieById(form.getMovieId());
		User user = userService.findUserByUserName(form.getUserName());

		java.util.Date date = new java.util.Date();

		@SuppressWarnings("deprecation")
		Date sqlDate = new Date(date.getYear(), date.getMonth(), date.getDay());

		Review review = new Review(form.getComment(), movie, user, form.getScore(), sqlDate);

		repository.save(review);

		// Update new score
		float newScore = (float) ((movie.getScore() * movie.getNumberOfVote()) + form.getScore())
				/ (movie.getNumberOfVote() + 1);
		movie.setScore((float) Math.round(newScore * 10) / 10);
		movie.setNumberOfVote(movie.getNumberOfVote() + 1);
		movieRepository.save(movie);

		// Update ranking
		List<Movie> movies = movieService.getListMovies();

		Collections.sort(movies, new Comparator<Movie>() {

			@Override
			public int compare(Movie m1, Movie m2) {
				// TODO Auto-generated method stub
				return m1.getScore() >= m2.getScore() ? -1 : 1;
			}
		});

		int i = 1;

		for (Movie m : movies) {
			m.setPosition(m.getRanking() - i);
			m.setRanking(i);
			i += 1;
			movieRepository.save(m);
		}
	}

	public void updateReview(int id, ReviewUpdateForm form) {
		Review review = repository.findByReviewId(id);

		if (form.getNewScore() != 0) {
			// Update movie's average score
			Movie movie = movieRepository.findById(review.getMovie().getId()).get();

			float newAverageScore = (float) ((movie.getScore() * movie.getNumberOfVote()) - review.getScore()
					+ form.getNewScore()) / movie.getNumberOfVote();
			movie.setScore((float) Math.round(newAverageScore * 10) / 10);
			
			movieRepository.save(movie);

			// Update ranking
			List<Movie> movies = movieService.getListMovies();

			Collections.sort(movies, new Comparator<Movie>() {

				@Override
				public int compare(Movie m1, Movie m2) {
					// TODO Auto-generated method stub
					return m1.getScore() >= m2.getScore() ? -1 : 1;
				}
			});

			int i = 1;

			for (Movie m : movies) {
				m.setPosition(m.getRanking() - i);
				m.setRanking(i);
				i += 1;
				movieRepository.save(m);
			}

		}

		// Update review
		if (form.getNewScore() != 0) {
			review.setScore(form.getNewScore());
		}

		if (form.getNewComment() != null && form.getNewComment() != "") {
			review.setComment(form.getNewComment());
		}

		repository.save(review);

	}

	@Override
	public void deleteReview(int id) {
		// TODO Auto-generated method stub
		Review review = repository.findByReviewId(id);

		// Update movie's average score
		Movie movie = movieRepository.findById(review.getMovie().getId()).get();

		float newAverageScore = (float) ((movie.getScore() * movie.getNumberOfVote()) - review.getScore())
				/ (movie.getNumberOfVote() - 1);
		movie.setScore((float) Math.round(newAverageScore * 10) / 10);
		movie.setNumberOfVote(movie.getNumberOfVote() - 1);

		movieRepository.save(movie);

		// Update ranking
		List<Movie> movies = movieService.getListMovies();

		Collections.sort(movies, new Comparator<Movie>() {

			@Override
			public int compare(Movie m1, Movie m2) {
				// TODO Auto-generated method stub
				return m1.getScore() >= m2.getScore() ? -1 : 1;
			}
		});

		int i = 1;

		for (Movie m : movies) {
			m.setPosition(m.getRanking() - i);
			m.setRanking(i);
			i += 1;
			movieRepository.save(m);
		}

		repository.delete(review);
	}
	
	// ===== adding 04/10/22 ===== //
	public void likeReview(String userName, int reviewId) {
		User user = userService.findUserByUserName(userName);
		Review review = repository.findByReviewId(reviewId);
		LikeReview like = new LikeReview();
		like.setUser(user);
		like.setReview(review);
		review.setNumberOfLike(review.getNumberOfLike() + 1);
			
		likeReviewRepository.save(like);
		repository.save(review);
	}
	
	public void dislikeReview(String userName, int reviewId) {
		Review review = repository.findByReviewId(reviewId);
		int likeId = 0;
		
		for (LikeReview like : review.getLikes()) {
			if (like.getUser().getUserName().equalsIgnoreCase(userName)) {
				likeId = like.getId();
			}
		}
		
		review.setNumberOfLike(review.getNumberOfLike() - 1);
		repository.save(review);
		
		likeReviewRepository.deleteById(likeId);
	}
	
	public int numberOfReviewByUserId(int userId) {
		String str = repository.numberOfReviewByUserId(userId);
		if (str != null) {
			return Integer.parseInt(str);
		} else {
			return 0;
		}
	}
	
	public int numberOfLikeByUserId(int userId) {
		String str = repository.numberOfLikeByUserId(userId);
		if (str != null) {
			return Integer.parseInt(str);
		} else {
			return 0;
		}
	}
}
