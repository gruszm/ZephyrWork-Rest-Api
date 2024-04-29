package pl.gruszm.ZephyrWork.restcontrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.ZephyrWork.DTOs.RegistrationDTO;
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
                .setRoleName(user.getRole().name());

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

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestHeader("Auth") String jwt, @Valid @RequestBody RegistrationDTO registrationDTO, BindingResult bindingResult)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        User newUser;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        if (bindingResult.hasErrors())
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Check, if an employee with this email already exists
        if (userService.findByEmail(registrationDTO.getEmail()) != null)
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        newUser = userService.processRegistration(registrationDTO);

        if (newUser == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity
                    .ok()
                    .build();
        }
    }
}
