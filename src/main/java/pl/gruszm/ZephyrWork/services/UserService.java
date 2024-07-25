package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.DTOs.RegistrationDTO;
import pl.gruszm.ZephyrWork.DTOs.SubordinateEmpDataDTO;
import pl.gruszm.ZephyrWork.DTOs.UserDTO;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.entities.WorkSession;
import pl.gruszm.ZephyrWork.enums.RoleType;
import pl.gruszm.ZephyrWork.repostitories.UserRepository;
import pl.gruszm.ZephyrWork.repostitories.WorkSessionRepository;
import pl.gruszm.ZephyrWork.security.UserDetails;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WorkSessionRepository workSessionRepository;

    public User findById(int id)
    {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User processRegistration(UserDetails registeringUserDetails, RegistrationDTO registrationDTO)
    {
        User registeringUser, supervisor, newUser;

        registeringUser = findByEmail(registeringUserDetails.getEmail());

        // Make sure, that the user registering a new employee exists
        if (registeringUser == null)
        {
            return null;
        }

        // A manager can register regular employees, but a CEO can register both managers and regular employees
        // This condition also covers attempts of registering a new employee by another employee
        if (registeringUser.getRole().compareTo(registrationDTO.getRole()) <= 0)
        {
            return null;
        }

        supervisor = findById(registrationDTO.getSupervisorId());

        // A regular employee cannot be a supervisor
        if (supervisor == null || supervisor.getRole().equals(RoleType.EMPLOYEE))
        {
            return null;
        }

        newUser = new User();

        newUser.setEmail(registrationDTO.getEmail());
        newUser.setFirstName(registrationDTO.getFirstName());
        newUser.setLastName(registrationDTO.getLastName());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        newUser.setSupervisor(supervisor);
        newUser.setRole(registrationDTO.getRole());

        return userRepository.save(newUser);
    }

    public List<UserDTO> findSupervisors()
    {
        List<User> allUsers = userRepository.findAll();
        List<User> allSupervisors = allUsers.stream().filter(u -> (u.getRole() == RoleType.CEO || u.getRole() == RoleType.MANAGER)).toList();
        List<UserDTO> allSupervisorDTOs = new ArrayList<>();

        for (User u : allSupervisors)
        {
            allSupervisorDTOs.add(new UserDTO(u));
        }

        return allSupervisorDTOs;
    }

    public UserDTO findUserByWorkSessionId(String email, int workSessionId)
    {
        User requestingUser = findByEmail(email);
        User userRelatedWithWorkSession;
        UserDTO userDTO;
        Optional<WorkSession> workSessionOptional;

        if ((requestingUser == null) || (requestingUser.getRole().equals(RoleType.EMPLOYEE)))
        {
            return null;
        }

        workSessionOptional = workSessionRepository.findById(workSessionId);

        if (workSessionOptional.isEmpty())
        {
            return null;
        }

        userRelatedWithWorkSession = workSessionOptional.get().getUser();

        userDTO = new UserDTO(userRelatedWithWorkSession);

        return userDTO;
    }

    public List<UserDTO> findSubordinates(User user)
    {
        List<User> users = userRepository.findSubordinatesBySupervisorEmail(user.getEmail());
        List<UserDTO> userDTOs = users.stream().map(u -> new UserDTO(u)).toList();

        return userDTOs;
    }

    public User updateEmployeeSettings(User employeeToUpdate, SubordinateEmpDataDTO subordinateEmpDataDTO)
    {
        employeeToUpdate.setStartingTime(LocalTime.of(subordinateEmpDataDTO.getStartingHour(), subordinateEmpDataDTO.getStartingMinute()));
        employeeToUpdate.setEndingTime(LocalTime.of(subordinateEmpDataDTO.getEndingHour(), subordinateEmpDataDTO.getEndingMinute()));
        employeeToUpdate.setLocationRegistrationInterval(subordinateEmpDataDTO.getLocationRegistrationInterval());
        employeeToUpdate.setForceStartWorkSession(subordinateEmpDataDTO.isForceStartWorkSession());

        return userRepository.save(employeeToUpdate);
    }

    public int getLocationRegistrationInterval(String email)
    {
        User user = userRepository.findByEmail(email);

        if (user != null)
        {
            return user.getLocationRegistrationInterval();
        }
        else
        {
            return -1;
        }
    }
}
