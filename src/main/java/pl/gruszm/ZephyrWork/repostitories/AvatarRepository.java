package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gruszm.ZephyrWork.entities.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Integer>
{

}
