package pl.gruszm.ZephyrWork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class SecurityController
{
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @Autowired
    public SecurityController(UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest loginRequest)
    {
        User foundUser = userService.findByEmail(loginRequest.getEmail());
        String encodedPassword;

        if (foundUser == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        encodedPassword = foundUser.getPassword();

        if (passwordEncoder.matches(loginRequest.getPassword(), encodedPassword))
        {
            return ResponseEntity
                    .ok()
                    .body(jwtUtils.createToken(loginRequest.getEmail(), foundUser.getRoles().stream().map(role -> role.getRoleName()).toList()));
        }
        else
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
    }
}