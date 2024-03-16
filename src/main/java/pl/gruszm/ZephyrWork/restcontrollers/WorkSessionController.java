package pl.gruszm.ZephyrWork.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.entities.WorkSession;
import pl.gruszm.ZephyrWork.security.JwtUtils;
import pl.gruszm.ZephyrWork.security.UserDetails;
import pl.gruszm.ZephyrWork.services.UserService;
import pl.gruszm.ZephyrWork.services.WorkSessionService;

import java.util.List;

@RestController
@RequestMapping("/api/worksessions")
public class WorkSessionController
{
    private JwtUtils jwtUtils;
    private WorkSessionService workSessionService;
    private UserService userService;

    @Autowired
    public WorkSessionController(JwtUtils jwtUtils, WorkSessionService workSessionService, UserService userService)
    {
        this.jwtUtils = jwtUtils;
        this.workSessionService = workSessionService;
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<WorkSession> getWorkSessionById(@PathVariable("id") int id, @RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        WorkSession workSession;
        User user;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSession = workSessionService.findById(id);

        if (workSession == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        user = userService.findByEmail(userDetails.getEmail());

        // Make sure that this work session belongs to this user
        if (!user.getWorkSessions().stream().map(ws -> ws.getId()).toList().contains(id))
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        return ResponseEntity.ok(workSession);
    }

    @GetMapping("/user/token")
    public ResponseEntity<List<WorkSession>> getWorkSessionsByToken(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        List<WorkSession> workSessions;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSessions = workSessionService.findByUserEmail(userDetails.getEmail());

        return ResponseEntity.ok(workSessions);
    }

    @PostMapping
    public ResponseEntity<WorkSession> saveWorkSession(@RequestBody WorkSession workSession)
    {
        WorkSession savedWorkSession = workSessionService.save(workSession);

        if (savedWorkSession == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(savedWorkSession);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<WorkSession> deleteWorkSession(@PathVariable("id") int id)
    {
        WorkSession workSession = workSessionService.deleteById(id);

        if (workSession == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(workSession);
        }
    }
}
