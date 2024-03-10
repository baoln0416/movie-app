package project.graduation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.graduation.entity.UserActivities;

@Repository
public interface UserActivitiesRepository extends JpaRepository<UserActivities, Integer>, JpaSpecificationExecutor<UserActivities> {
	
	@Query(value = "SELECT * FROM `User_Activities` WHERE `user_id` = :param ORDER BY `time` DESC", nativeQuery = true)
	public List<UserActivities> getActivitiesFromUserId(@Param("param") int id);
	
}
