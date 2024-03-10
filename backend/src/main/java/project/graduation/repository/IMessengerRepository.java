package project.graduation.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.graduation.entity.Messenger;

@Repository
public interface IMessengerRepository extends JpaRepository<Messenger, Integer>, JpaSpecificationExecutor<Messenger> {

	public List<Messenger> findByUserId(int userId );
	
	@Query(value = "SELECT m.user_id FROM Messenger m GROUP BY m.user_id", nativeQuery = true)
	public List<Integer> FindAllUserId();
	
}
