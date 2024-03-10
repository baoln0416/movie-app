//
package project.graduation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.graduation.entity.Category;
import project.graduation.entity.Movie;
import project.graduation.entity.Movie.Tag;
import project.graduation.entity.MovieCategory;
import project.graduation.repository.IMovieRepository;

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
@Service
public class MovieService implements IMovieService {

	@Autowired
	private IMovieRepository movieRepository;

	public Movie getMovieById(int id) {
		return movieRepository.findById(id).get();
	}

	public List<Movie> getListMovies() {
		return movieRepository.findAll();
	}

	public List<Movie> findByTag(Tag tag) {
		return movieRepository.findByTag(tag);
	}

	public List<Movie> findByCountry(String country) {
		return movieRepository.findByCountry(country);
	}

	public List<Movie> searchMovieByName(String name) {
		return movieRepository.searchMovieByName(name);
	}
	
	public List<Movie> getMoviesHaveSameCategories(int id) {
		Movie movie = movieRepository.findById(id).get();
		
		List<Category> categories = new ArrayList<Category>();
		
		for (MovieCategory movieCategory : movie.getMovieCategories()) {
			categories.add(movieCategory.getCategory());
		}
		
		List<Movie> movies = movieRepository.findAll();
		
		List<Movie> sameKindMovies = new ArrayList<Movie>();
		
		int count = 0;
		
		for (Movie m : movies) {
			for (int i = 1; i < categories.size(); i++) {
				for (MovieCategory movieCategory : m.getMovieCategories()) {
					if (movieCategory.getCategory().getName().equals(categories.get(i).getName())) {
						count+=1;
						break;
					}
				}
			}
			
			if (count >= 2 && (m.getId() != id)) {
				sameKindMovies.add(m);
				count = 0;
			} else {
				count = 0;
			}
		}
		
		while (sameKindMovies.size() <= 5) {
			for (Movie m : movies) {
				if (m.getId() != id) {
					sameKindMovies.add(m);
					movies.remove(m);
					break;
				}
			}
		}
		
		return sameKindMovies;
	}
}
