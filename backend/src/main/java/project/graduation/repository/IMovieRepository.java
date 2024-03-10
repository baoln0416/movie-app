//
package project.graduation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface IMovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {
	
	public Movie findByName(String movieName);
	
	public List<Movie> findByTag(Tag tag);
	
	public List<Movie> findByCountry (String country);
	
	@Query(value = "SELECT * FROM `movie` WHERE `movie_name` LIKE %:parameter%", nativeQuery = true)
	public List<Movie> searchMovieByName(@Param("parameter") String search);
	
}
