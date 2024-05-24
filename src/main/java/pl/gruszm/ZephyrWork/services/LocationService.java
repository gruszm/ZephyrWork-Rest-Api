package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.DTOs.LocationDTO;
import pl.gruszm.ZephyrWork.entities.Location;
import pl.gruszm.ZephyrWork.entities.WorkSession;
import pl.gruszm.ZephyrWork.repostitories.LocationRepository;
import pl.gruszm.ZephyrWork.repostitories.WorkSessionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService
{
    private LocationRepository locationRepository;
    private WorkSessionRepository workSessionRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, WorkSessionRepository workSessionRepository)
    {
        this.locationRepository = locationRepository;
        this.workSessionRepository = workSessionRepository;
    }

    public Location findById(int id)
    {
        Optional<Location> optionalLocation = locationRepository.findById(id);

        return optionalLocation.isPresent() ? optionalLocation.get() : null;
    }

    public List<LocationDTO> findByWorkSessionId(int id)
    {
        List<Location> locations = locationRepository.findByWorkSessionId(id);
        List<LocationDTO> locationDTOs = locations.stream().map(loc -> new LocationDTO(loc.getLocationTime().toString(), loc.getLatitude(), loc.getLongitude())).toList();

        return locationDTOs;
    }

    public Location saveLocationForUser(String email, LocationDTO locationDTO)
    {
        // Get the most recent work session
        List<WorkSession> workSessions = workSessionRepository.findByUserEmail(email,
                PageRequest.of(0, 1, Sort.by("startTime").descending()));
        WorkSession workSession;
        Location location, savedWorkSession;
        LocalDateTime locationTime;

        if (workSessions.isEmpty())
        {
            return null;
        }

        workSession = workSessions.get(0);

        // Check, if the work session is active
        if (workSession.getEndTime() != null)
        {
            return null;
        }

        try
        {
            locationTime = LocalDateTime.parse(locationDTO.getLocationTimeAsString());
        }
        catch (DateTimeParseException e)
        {
            return null;
        }

        location = new Location();
        location.setLocationTime(locationTime);
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setWorkSession(workSession);

        savedWorkSession = locationRepository.save(location);

        return savedWorkSession;
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
