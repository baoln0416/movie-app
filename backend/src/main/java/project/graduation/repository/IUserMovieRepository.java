package project.graduation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.graduation.entity.UserMovie;

@Repository
public interface IUserMovieRepository extends JpaRepository<UserMovie, Integer> {

	@Query(value = "SELECT * FROM `user_movie` WHERE `user_id` = :param", nativeQuery = true)
	public List<UserMovie> getListSubscibedMovies(@Param("param") int id);

	@Query(value = "SELECT * FROM `user_movie` WHERE `user_id` = :param1 AND `movie_id` = :param2", nativeQuery = true)
	public UserMovie isMovieSubscribed(@Param("param1") int userId, @Param("param2") int movieId);

}
