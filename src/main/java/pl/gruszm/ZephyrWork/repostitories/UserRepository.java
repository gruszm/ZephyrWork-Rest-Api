package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.gruszm.ZephyrWork.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.supervisor.email LIKE :email")
    List<User> findSubordinatesBySupervisorEmail(@Param("email") String email);
}
