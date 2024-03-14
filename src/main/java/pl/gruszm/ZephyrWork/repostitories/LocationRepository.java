package pl.gruszm.ZephyrWork.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.gruszm.ZephyrWork.entities.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer>
{
    @Query("SELECT l FROM Location l WHERE l.workSession.id = :id")
    List<Location> findByWorkSessionId(@Param("id") int id);
}
