package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gruszm.ZephyrWork.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>
{

}
