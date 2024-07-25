package pl.gruszm.ZephyrWork.restcontrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.ZephyrWork.DTOs.RegistrationDTO;
import pl.gruszm.ZephyrWork.DTOs.SubordinateEmpDataDTO;
import pl.gruszm.ZephyrWork.DTOs.UserDTO;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.enums.RoleType;
import pl.gruszm.ZephyrWork.security.JwtUtils;
import pl.gruszm.ZephyrWork.security.UserDetails;
import pl.gruszm.ZephyrWork.services.UserService;

import java.util.List;

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

    @PutMapping("/subordinates/update/{employeeId}")
    public ResponseEntity<Void> updateEmployeeSettings(@PathVariable("employeeId") int employeeId,
                                                       @RequestHeader("Auth") String jwt,
                                                       @RequestBody SubordinateEmpDataDTO subordinateEmpDataDTO)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        User supervisor, employeeToUpdate, updatedEmployee;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        supervisor = userService.findByEmail(userDetails.getEmail());

        if ((supervisor == null) || (supervisor.getRole().equals(RoleType.EMPLOYEE)))
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        employeeToUpdate = userService.findById(employeeId);

        if (employeeToUpdate == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        updatedEmployee = userService.updateEmployeeSettings(employeeToUpdate, subordinateEmpDataDTO);

        if (updatedEmployee == null)
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

    @GetMapping("/subordinates")
    public ResponseEntity<List<UserDTO>> getSubordinates(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        List<UserDTO> userDTOs;
        User user;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        user = userService.findByEmail(userDetails.getEmail());

        if ((user == null) || (user.getRole().equals(RoleType.EMPLOYEE)))
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        userDTOs = userService.findSubordinates(user);

        if (userDTOs == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity.ok(userDTOs);
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

        userDTO = new UserDTO(user);

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

        supervisorDTO = new UserDTO(supervisor);

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

        newUser = userService.processRegistration(userDetails, registrationDTO);

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

    @GetMapping("/supervisors")
    public ResponseEntity<List<UserDTO>> getAllSupervisors()
    {
        List<UserDTO> supervisors = userService.findSupervisors();

        return ResponseEntity.ok(supervisors);
    }

    @GetMapping("/worksession/id/{id}")
    public ResponseEntity<UserDTO> getUserByWorkSessionId(@RequestHeader("Auth") String jwt, @PathVariable("id") int id)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        UserDTO userDTO;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        userDTO = userService.findUserByWorkSessionId(userDetails.getEmail(), id);

        if (userDTO == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }


        return ResponseEntity.ok(userDTO);
    }
}
