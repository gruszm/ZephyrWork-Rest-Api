package pl.gruszm.ZephyrWork.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.ZephyrWork.DTOs.LocationDTO;
import pl.gruszm.ZephyrWork.entities.Location;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.security.JwtUtils;
import pl.gruszm.ZephyrWork.security.UserDetails;
import pl.gruszm.ZephyrWork.services.LocationService;
import pl.gruszm.ZephyrWork.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController
{
    private JwtUtils jwtUtils;
    private LocationService locationService;
    private UserService userService;

    @Autowired
    public LocationController(JwtUtils jwtUtils, LocationService locationService, UserService userService)
    {
        this.jwtUtils = jwtUtils;
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") int id, @RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        Location location;
        User user;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        location = locationService.findById(id);

        if (location == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        user = userService.findByEmail(userDetails.getEmail());

        // Make sure that this location belongs to this user
        if (!user.getWorkSessions().stream().map(ws -> ws.getId()).toList().contains(location.getWorkSession().getId()))
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        return ResponseEntity.ok(location);
    }

    @GetMapping("/worksession/{id}")
    public ResponseEntity<List<LocationDTO>> getLocationsByWorkSessionId(@PathVariable("id") int id, @RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        List<LocationDTO> locationList;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        locationList = locationService.findByWorkSessionId(id);

        return ResponseEntity.ok(locationList);
    }

    @PostMapping("/token")
    public ResponseEntity<Location> saveLocationForUser(@RequestHeader("Auth") String jwt, @RequestBody LocationDTO locationDTO)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        Location location;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        location = locationService.saveLocationForUser(userDetails.getEmail(), locationDTO);

        if (location == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(location);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable("id") int id)
    {
        Location location = locationService.deleteById(id);

        if (location == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(location);
        }
    }
}
