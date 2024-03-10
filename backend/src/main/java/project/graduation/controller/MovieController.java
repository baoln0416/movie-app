//
package project.graduation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.graduation.dto.MovieDTO;
import project.graduation.dto.ReviewDTO;
import project.graduation.entity.LikeReview;
import project.graduation.entity.Movie;
import project.graduation.entity.Movie.Tag;
import project.graduation.entity.MovieActor;
import project.graduation.entity.MovieCategory;
import project.graduation.entity.Review;
import project.graduation.service.IMovieService;
import project.graduation.service.IReviewService;

/**
 * This class is .
 * 
 * @Description: .
 * @author: LNBao
 * @create_date: May 27, 2022
 * @version: 1.0
 * @modifier: LNBao
 * @modified_date: May 27, 2022
 */
@RestController
@RequestMapping(value = "api/v1/movies")
@CrossOrigin("*")
public class MovieController {

	@Autowired
	private IMovieService movieService;
	
	@Autowired(required = true)
	private IReviewService reviewService;

	@GetMapping
	public ResponseEntity<?> getListMovies() {

		List<Movie> movies = movieService.getListMovies();

		List<MovieDTO> dtos = new ArrayList<MovieDTO>();

		for (Movie movie : movies) {
			MovieDTO dto = new MovieDTO();
			dto.setId(movie.getId());
			dto.setName(movie.getName());
			dto.setScore(movie.getScore());
			dto.setPublishYear(movie.getPublishYear());
			dto.setDuration(movie.getDuration());
			dto.setRating(movie.getRating());
			dto.setDirector(movie.getDirector().getName());

			List<String> actors = new ArrayList<String>();
			for (MovieActor movieActor : movie.getMovieActors()) {
				actors.add(movieActor.getActor().getName());
			}

			List<String> categories = new ArrayList<String>();
			for (MovieCategory category : movie.getMovieCategories()) {
				categories.add(category.getCategory().getName());
			}

			dto.setActors(actors);
			dto.setCategories(categories);
			dto.setCountry(movie.getCountry());
			dto.setTag(movie.getTag().toString());
			dto.setPoster(movie.getPoster());
			dto.setBigPoster(movie.getBigPoster());
			dto.setTrailer(movie.getTrailer());
			dto.setPresentation(movie.getPresentation());
			dto.setDescription(movie.getDescription());
			dtos.add(dto);
		}

		return new ResponseEntity<List<MovieDTO>>(dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/country/{country}")
	public ResponseEntity<?> getMovieByCountry(@PathVariable(name = "country") String country) {

		List<Movie> movies = movieService.findByCountry(country);

		List<MovieDTO> dtos = new ArrayList<MovieDTO>();

		for (Movie movie : movies) {
			MovieDTO dto = new MovieDTO();
			dto.setId(movie.getId());
			dto.setName(movie.getName());
			dto.setScore(movie.getScore());
			dto.setPublishYear(movie.getPublishYear());
			dto.setDuration(movie.getDuration());
			dto.setRating(movie.getRating());
			dto.setDirector(movie.getDirector().getName());

			List<String> actors = new ArrayList<String>();
			for (MovieActor movieActor : movie.getMovieActors()) {
				actors.add(movieActor.getActor().getName());
			}

			List<String> categories = new ArrayList<String>();
			for (MovieCategory category : movie.getMovieCategories()) {
				categories.add(category.getCategory().getName());
				System.err.println(category.getCategory().getName());
			}
			dto.setCategories(categories);

			dto.setActors(actors);
			dto.setCountry(movie.getCountry());
			dto.setTag(movie.getTag().toString());
			dto.setPoster(movie.getPoster());
			dto.setBigPoster(movie.getBigPoster());
			dto.setTrailer(movie.getTrailer());
			dto.setPresentation(movie.getPresentation());
			dto.setDescription(movie.getDescription());
			dtos.add(dto);
		}

		return new ResponseEntity<List<MovieDTO>>(dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/category/{category}")
	public ResponseEntity<?> getMovieByCategory(@PathVariable(name = "category") String category) {

		List<Movie> movies = movieService.getListMovies();

		List<MovieDTO> dtos = new ArrayList<MovieDTO>();

		for (Movie movie : movies) {
			for (MovieCategory cate : movie.getMovieCategories()) {
				if (cate.getCategory().getName().equalsIgnoreCase(category)) {
					MovieDTO dto = new MovieDTO();
					dto.setId(movie.getId());
					dto.setName(movie.getName());
					dto.setScore(movie.getScore());
					dto.setPublishYear(movie.getPublishYear());
					dto.setDuration(movie.getDuration());
					dto.setRating(movie.getRating());
					dto.setDirector(movie.getDirector().getName());

					List<String> actors = new ArrayList<String>();
					for (MovieActor movieActor : movie.getMovieActors()) {
						actors.add(movieActor.getActor().getName());
					}

					List<String> categories = new ArrayList<String>();

					dto.setCategories(categories);

					dto.setActors(actors);
					dto.setCountry(movie.getCountry());
					dto.setTag(movie.getTag().toString());
					dto.setPoster(movie.getPoster());
					dto.setBigPoster(movie.getBigPoster());
					dto.setTrailer(movie.getTrailer());
					dto.setPresentation(movie.getPresentation());
					dto.setDescription(movie.getDescription());
					dtos.add(dto);

				}
			}
		}

		return new ResponseEntity<List<MovieDTO>>(dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/tag/{tag}")
	public ResponseEntity<?> getMoviesByTag(@PathVariable(name = "tag") String tag) {
		Tag[] tags = Movie.Tag.values();
		List<Movie> movies;
		List<MovieDTO> dtos = new ArrayList<MovieDTO>();
		boolean check = false;
		for (Tag tag1 : tags) {
			if (tag1.toString().equalsIgnoreCase(tag)) {
				check = true;
				movies = movieService.findByTag(tag1);
				dtos = new ArrayList<MovieDTO>();

				for (Movie movie : movies) {
					MovieDTO dto = new MovieDTO();
					dto.setId(movie.getId());
					dto.setName(movie.getName());
					dto.setScore(movie.getScore());
					dto.setPublishYear(movie.getPublishYear());
					dto.setDuration(movie.getDuration());
					dto.setRating(movie.getRating());
					dto.setDirector(movie.getDirector().getName());

					List<String> actors = new ArrayList<String>();
					for (MovieActor movieActor : movie.getMovieActors()) {
						actors.add(movieActor.getActor().getName());
					}

					dto.setActors(actors);
					dto.setCountry(movie.getCountry());
					dto.setTag(movie.getTag().toString());
					dto.setPoster(movie.getPoster());
					dto.setBigPoster(movie.getBigPoster());
					dto.setTrailer(movie.getTrailer());
					dto.setPresentation(movie.getPresentation());
					dto.setDescription(movie.getDescription());
					dtos.add(dto);
				}
			}

		}
		if (!check) {
			movies = movieService.getListMovies();
			dtos = new ArrayList<MovieDTO>();

			for (Movie movie : movies) {
				MovieDTO dto = new MovieDTO();
				dto.setId(movie.getId());
				dto.setName(movie.getName());
				dto.setScore(movie.getScore());
				dto.setPublishYear(movie.getPublishYear());
				dto.setDuration(movie.getDuration());
				dto.setRating(movie.getRating());
				dto.setDirector(movie.getDirector().getName());

				List<String> actors = new ArrayList<String>();
				for (MovieActor movieActor : movie.getMovieActors()) {
					actors.add(movieActor.getActor().getName());
				}

				dto.setActors(actors);
				dto.setCountry(movie.getCountry());
				dto.setTag(movie.getTag().toString());
				dto.setPoster(movie.getPoster());
				dto.setBigPoster(movie.getBigPoster());
				dto.setTrailer(movie.getTrailer());
				dto.setPresentation(movie.getPresentation());
				dto.setDescription(movie.getDescription());
				dtos.add(dto);
				System.err.println("ok");
			}

		}

		return new ResponseEntity<List<MovieDTO>>(dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getMovieById(@PathVariable(name = "id") int id) {

		Movie entity = movieService.getMovieById(id);

		MovieDTO dto = new MovieDTO();
		dto.setName(entity.getName());
		dto.setNameLength(entity.getName().length());
		dto.setScore(entity.getScore());
		dto.setPublishYear(entity.getPublishYear());
		dto.setDuration(entity.getDuration());
		dto.setRating(entity.getRating());
		dto.setDirector(entity.getDirector().getName());

		List<Review> reviews = reviewService.getListReviewByMovieID(id);

		List<ReviewDTO> dtos = new ArrayList<ReviewDTO>();
		for (Review review : reviews) {
			ReviewDTO dto1 = new ReviewDTO();
			dto1.setReviewId(review.getReviewId());
			dto1.setComment(review.getComment());
			dto1.setMovieID(review.getMovie().getId());
			dto1.setUserName(review.getUser().getUserName());
			dto1.setUserRole(review.getUser().getRole().toString());
			dto1.setUserAvatar(review.getUser().getAvatarUrl());
			dto1.setScore(review.getScore());
			dto1.setNumberOfLike(review.getNumberOfLike());
			
			List<String> likeByUsers = new ArrayList<String>();
			for (LikeReview likeReview : review.getLikes()) {
				likeByUsers.add(likeReview.getUser().getUserName());
			}
			
			dto1.setLikeByUsers(likeByUsers);
			dto1.setUploadDay(review.getUpdateDate());
			dto1.setUploadMonth(review.getUpdateDate());
			dto1.setUploadYear(review.getUpdateDate());
			dtos.add(dto1);
		}
		Collections.reverse(dtos);

		dto.setReviews(dtos);
		dto.setNumberOfReviews(dtos.size());

		List<String> actors = new ArrayList<String>();
		for (MovieActor movieActor : entity.getMovieActors()) {
			actors.add(movieActor.getActor().getName());
		}

		List<String> categories = new ArrayList<String>();
		for (MovieCategory category : entity.getMovieCategories()) {
			categories.add(category.getCategory().getName());
		}

		dto.setCategories(categories);
		dto.setActors(actors);
		dto.setCountry(entity.getCountry());
		dto.setTag(entity.getTag().toString());
		dto.setPoster(entity.getPoster());
		dto.setTrailer(entity.getTrailer());
		dto.setRanking(entity.getRanking());
		dto.setPosition(entity.getPosition());
		dto.setBudget(entity.getBudget());
		dto.setPresentation(entity.getPresentation());
		dto.setDescription(entity.getDescription());

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchMovieByName(@RequestParam String search) {

		List<Movie> movies = movieService.searchMovieByName(search);

		List<MovieDTO> dtos = new ArrayList<MovieDTO>();

		for (Movie movie : movies) {
			MovieDTO dto = new MovieDTO();
			dto.setId(movie.getId());
			dto.setName(movie.getName());
			dto.setScore(movie.getScore());
			dto.setPublishYear(movie.getPublishYear());
			dto.setDuration(movie.getDuration());
			dto.setRating(movie.getRating());
			dto.setDirector(movie.getDirector().getName());

			List<String> actors = new ArrayList<String>();
			for (MovieActor movieActor : movie.getMovieActors()) {
				actors.add(movieActor.getActor().getName());
			}

			List<String> categories = new ArrayList<String>();
			for (MovieCategory category : movie.getMovieCategories()) {
				categories.add(category.getCategory().getName());
			}

			dto.setActors(actors);
			dto.setCategories(categories);
			dto.setCountry(movie.getCountry());
			dto.setTag(movie.getTag().toString());
			dto.setPoster(movie.getPoster());
			dto.setBigPoster(movie.getBigPoster());
			dto.setTrailer(movie.getTrailer());
			dto.setPresentation(movie.getPresentation());
			dto.setDescription(movie.getDescription());
			dtos.add(dto);
		}

		return new ResponseEntity<List<MovieDTO>>(dtos, HttpStatus.OK);
	}
	
	@GetMapping("/sameCategories/{id}")
	public ResponseEntity<?> getMoviesHaveSameCategories(@PathVariable(name = "id") int id) {
		
		List<Movie> movies = movieService.getMoviesHaveSameCategories(id);
		List<MovieDTO> dtos = new ArrayList<MovieDTO>();
		
		for (Movie movie : movies) {
			MovieDTO dto = new MovieDTO();
			dto.setId(movie.getId());
			dto.setName(movie.getName());
			dto.setScore(movie.getScore());
			dto.setPublishYear(movie.getPublishYear());
			dto.setDuration(movie.getDuration());
			dto.setRating(movie.getRating());
			dto.setDirector(movie.getDirector().getName());

			List<String> actors = new ArrayList<String>();
			for (MovieActor movieActor : movie.getMovieActors()) {
				actors.add(movieActor.getActor().getName());
			}

			List<String> categories = new ArrayList<String>();
			for (MovieCategory category : movie.getMovieCategories()) {
				categories.add(category.getCategory().getName());
			}

			dto.setActors(actors);
			dto.setCategories(categories);
			dto.setCountry(movie.getCountry());
			dto.setTag(movie.getTag().toString());
			dto.setPoster(movie.getPoster());
			dto.setBigPoster(movie.getBigPoster());
			dto.setTrailer(movie.getTrailer());
			dto.setPresentation(movie.getPresentation());
			dto.setDescription(movie.getDescription());
			dtos.add(dto);
		}
		
		return new ResponseEntity<List<MovieDTO>>(dtos, HttpStatus.OK);
	}
}
