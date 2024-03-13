package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.gruszm.ZephyrWork.entities.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer>
{
    @Query("SELECT r FROM Role r WHERE r.user.id = :id")
    List<Role> findByUserId(@Param("id") int id);

    @Query("SELECT r FROM Role r WHERE r.user.email LIKE :email")
    List<Role> findByUserEmail(@Param("email") String email);
}
