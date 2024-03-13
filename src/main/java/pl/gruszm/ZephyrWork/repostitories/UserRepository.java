package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gruszm.ZephyrWork.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByEmail(String email);
}
