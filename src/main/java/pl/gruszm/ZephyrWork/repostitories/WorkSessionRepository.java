package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.gruszm.ZephyrWork.entities.WorkSession;

import java.util.List;

public interface WorkSessionRepository extends JpaRepository<WorkSession, Integer>
{
    @Query("SELECT ws FROM WorkSession ws WHERE ws.user.id = :id")
    List<WorkSession> findByUserId(@Param("id") int id);

    @Query("SELECT ws FROM WorkSession ws WHERE ws.user.email LIKE :email")
    List<WorkSession> findByUserEmail(@Param("email") String email);
}
