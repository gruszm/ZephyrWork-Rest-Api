package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.DTOs.RegistrationDTO;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.enums.RoleType;
import pl.gruszm.ZephyrWork.repostitories.UserRepository;
import pl.gruszm.ZephyrWork.security.UserDetails;

import java.util.Optional;

@Service
public class UserService
{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

        // Check, if password and repeat password fields are the different
        if (registrationDTO.arePasswordFieldsDifferent())
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
}
