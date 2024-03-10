package project.graduation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.graduation.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	public boolean existsByUserName(String userName);

	public boolean existsByEmail(String email);
	
	@Query("	SELECT 	status 		"
			+ "	FROM 	User 		"
			+ " WHERE 	email = :email")
	public User.UserStatus findStatusByEmail(@Param("email") String email);

	public User findByUserName(String name);
	
	public User findByEmail(String email);
	
	public User findByFacebookID(String facebookID);
	
	public void deleteByIdIn(List<Integer> ids);
	
}
