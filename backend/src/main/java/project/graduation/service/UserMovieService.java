package project.graduation.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.graduation.entity.Movie;
import project.graduation.entity.User;
import project.graduation.entity.UserMovie;
import project.graduation.repository.IMovieRepository;
import project.graduation.repository.IUserMovieRepository;

@Service
public class UserMovieService implements IUserMovieService {

	@Autowired
	private IUserMovieRepository repository;

	@Autowired
	private IMovieRepository movieRepository;

	@Override
	public List<UserMovie> getListSubscribedMovies(int id) {
		// TODO Auto-generated method stub
		return repository.getListSubscibedMovies(id);
	}

	@Override
	public void subscribesAMovie(User user, int movieId) {
		// TODO Auto-generated method stub
		UserMovie userMovie = new UserMovie();
		Movie movie = movieRepository.findById(movieId).get();
		Date date = new Date();

		userMovie.setUser(user);
		userMovie.setMovie(movie);
		userMovie.setRecordTime(date);

		repository.save(userMovie);
	}

	public boolean isMovieIsSubscribed(int userId, int movieId) {
		if (repository.isMovieSubscribed(userId, movieId) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void unsubscribe(int userId, int movieId) {
		UserMovie userMovie = repository.isMovieSubscribed(userId, movieId);
		repository.delete(userMovie);
	}
}
