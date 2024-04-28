package pl.gruszm.ZephyrWork.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.ZephyrWork.DTOs.UserDTO;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.security.JwtUtils;
import pl.gruszm.ZephyrWork.security.UserDetails;
import pl.gruszm.ZephyrWork.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    private JwtUtils jwtUtils;
    private UserService userService;

    @Autowired
    public UserController(JwtUtils jwtUtils, UserService userService)
    {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @GetMapping("/token")
    public ResponseEntity<UserDTO> getUserByToken(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        User user;
        UserDTO userDTO;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        user = userService.findByEmail(userDetails.getEmail());

        userDTO = new UserDTO()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setSupervisorId((user.getSupervisor() != null) ? user.getSupervisor().getId() : null)
                .setRoleName(user.getRole().getRoleType().name());

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/supervisor/token")
    public ResponseEntity<UserDTO> getSupervisorByToken(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        User user, supervisor;
        UserDTO supervisorDTO;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        user = userService.findByEmail(userDetails.getEmail());

        if (user.getSupervisor() == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        supervisor = user.getSupervisor();

        supervisorDTO = new UserDTO()
                .setFirstName(supervisor.getFirstName())
                .setLastName(supervisor.getLastName())
                .setEmail(supervisor.getEmail())
                .setSupervisorId(supervisor.getId());

        return ResponseEntity.ok(supervisorDTO);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user)
    {
        User savedUser = userService.save(user);

        if (savedUser == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(savedUser);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id)
    {
        User user = userService.deleteUserById(id);

        if (user == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(user);
        }
    }
}
