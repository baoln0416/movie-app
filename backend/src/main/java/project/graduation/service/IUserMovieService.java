package project.graduation.service;

import java.util.List;

import project.graduation.entity.User;
import project.graduation.entity.UserMovie;

public interface IUserMovieService {
	List<UserMovie> getListSubscribedMovies(int id);
	
	void subscribesAMovie(User user, int movieId);
	
	public boolean isMovieIsSubscribed(int userId, int movieId);
	
	public void unsubscribe(int userId, int movieId);
}
