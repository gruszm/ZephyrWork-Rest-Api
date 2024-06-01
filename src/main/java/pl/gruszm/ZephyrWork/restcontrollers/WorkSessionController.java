package pl.gruszm.ZephyrWork.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.ZephyrWork.DTOs.WorkSessionDTO;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.entities.WorkSession;
import pl.gruszm.ZephyrWork.enums.RoleType;
import pl.gruszm.ZephyrWork.enums.WorkSessionState;
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
    public ResponseEntity<List<WorkSessionDTO>> getWorkSessionsByToken(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        List<WorkSessionDTO> workSessionDTOs;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSessionDTOs = workSessionService.findByUserEmail(userDetails.getEmail());

        return ResponseEntity.ok(workSessionDTOs);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Void> approveWorkSession(@RequestHeader("Auth") String jwt, @PathVariable("id") int workSessionId)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        User user;
        WorkSession workSession;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        user = userService.findByEmail(userDetails.getEmail());

        // Regular employees cannot approve work sessions
        if (user.getRole().equals(RoleType.EMPLOYEE))
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        workSession = workSessionService.changeWorkSessionState(workSessionId, WorkSessionState.APPROVED, null);

        if (workSession == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity.ok(null);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelWorkSession(@RequestHeader("Auth") String jwt, @PathVariable("id") int workSessionId)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        WorkSession workSession;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSession = workSessionService.findById(workSessionId);

        // The work session must exist and must be in progress/under review/returned
        if ((workSession == null) ||
                ((!workSession.getWorkSessionState().equals(WorkSessionState.IN_PROGRESS))
                        && (!workSession.getWorkSessionState().equals(WorkSessionState.UNDER_REVIEW))
                        && (!workSession.getWorkSessionState().equals(WorkSessionState.RETURNED))))
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        workSession = workSessionService.changeWorkSessionState(workSessionId, WorkSessionState.CANCELLED, null);

        if (workSession == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity.ok(null);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<Void> returnWorkSession(@RequestHeader("Auth") String jwt, @PathVariable("id") int workSessionId, @RequestBody String notes)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        User user;
        WorkSession workSession;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        user = userService.findByEmail(userDetails.getEmail());

        // Regular employees cannot return work sessions
        if (user.getRole().equals(RoleType.EMPLOYEE))
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        workSession = workSessionService.changeWorkSessionState(workSessionId, WorkSessionState.RETURNED, notes);

        if (workSession == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity.ok(null);
    }

    @PostMapping("/resend/{id}")
    public ResponseEntity<Void> resendWorkSession(@RequestHeader("Auth") String jwt, @PathVariable("id") int workSessionId, @RequestBody String notes)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        WorkSession workSession;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSession = workSessionService.changeWorkSessionState(workSessionId, WorkSessionState.UNDER_REVIEW, notes);

        if (workSession == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity.ok(null);
    }

    @GetMapping("/by/supervisor")
    public ResponseEntity<List<WorkSessionDTO>> getWorkSessionsOfEmployees(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        List<WorkSessionDTO> workSessionDTOs;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSessionDTOs = workSessionService.findWorkSessionsOfEmployees(userDetails.getEmail());

        if (workSessionDTOs == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity.ok(workSessionDTOs);
    }

    @PostMapping("/start")
    public ResponseEntity<WorkSession> startWorkSession(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        WorkSession workSession;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSession = workSessionService.startWorkSessionForUser(userDetails.getEmail());

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

    @PostMapping("/stop")
    public ResponseEntity<WorkSession> endWorkSession(@RequestHeader("Auth") String jwt)
    {
        UserDetails userDetails = jwtUtils.readToken(jwt);
        WorkSession workSession;

        if (userDetails == null)
        {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        workSession = workSessionService.stopWorkSessionForUser(userDetails.getEmail());

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
