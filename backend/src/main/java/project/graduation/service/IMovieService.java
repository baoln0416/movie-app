//
package project.graduation.service;

import java.util.List;

import org.springframework.stereotype.Component;

import project.graduation.entity.Movie;
import project.graduation.entity.Movie.Tag;

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
@Component
public interface IMovieService {

	Movie getMovieById(int id);
	
	List<Movie> getListMovies();
	
	List<Movie> findByTag(Tag tag);
	
	List<Movie> findByCountry(String country);
	
	List<Movie> searchMovieByName(String search);
	
	public List<Movie> getMoviesHaveSameCategories(int id);
}
