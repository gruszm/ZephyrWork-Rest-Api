package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.entities.Location;
import pl.gruszm.ZephyrWork.repostitories.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService
{
    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

    public Location findById(int id)
    {
        Optional<Location> optionalLocation = locationRepository.findById(id);

        return optionalLocation.isPresent() ? optionalLocation.get() : null;
    }

    public List<Location> findByWorkSessionId(int id)
    {
        return locationRepository.findByWorkSessionId(id);
    }

    public Location save(Location location)
    {
        return locationRepository.save(location);
    }

    public Location deleteById(int id)
    {
        Optional<Location> optionalLocation = locationRepository.findById(id);

        if (optionalLocation.isPresent())
        {
            locationRepository.deleteById(id);

            return optionalLocation.get();
        }
        else
        {
            return null;
        }
    }
}
