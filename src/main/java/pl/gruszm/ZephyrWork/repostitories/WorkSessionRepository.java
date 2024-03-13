package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gruszm.ZephyrWork.entities.WorkSession;

public interface WorkSessionRepository extends JpaRepository<WorkSession, Integer>
{

}
