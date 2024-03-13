package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.repostitories.UserRepository;

import java.util.Optional;

@Service
public class UserService
{
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
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

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public User deleteUserById(int id)
    {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent())
        {
            userRepository.deleteById(id);

            return userOptional.get();
        }
        else
        {
            return null;
        }
    }
}
