package pl.gruszm.ZephyrWork.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.DTOs.WorkSessionDTO;
import pl.gruszm.ZephyrWork.entities.User;
import pl.gruszm.ZephyrWork.entities.WorkSession;
import pl.gruszm.ZephyrWork.repostitories.UserRepository;
import pl.gruszm.ZephyrWork.repostitories.WorkSessionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkSessionService
{
    private WorkSessionRepository workSessionRepository;
    private UserRepository userRepository;

    @Autowired
    public WorkSessionService(WorkSessionRepository workSessionRepository, UserRepository userRepository)
    {
        this.workSessionRepository = workSessionRepository;
        this.userRepository = userRepository;
    }

    public WorkSession findById(int id)
    {
        Optional<WorkSession> optionalWorkSession = workSessionRepository.findById(id);

        return optionalWorkSession.isPresent() ? optionalWorkSession.get() : null;
    }

    public List<WorkSession> findByUserId(int id)
    {
        return workSessionRepository.findByUserId(id);
    }

    public List<WorkSessionDTO> findByUserEmail(String email)
    {
        List<WorkSession> workSessions = workSessionRepository.findByUserEmail(email, Sort.by("startTime").descending());
        List<WorkSessionDTO> workSessionDTOs = new ArrayList<>();

        for (WorkSession ws : workSessions)
        {
            StringBuilder sb = new StringBuilder();

            sb.append(ws.getUser().getFirstName())
                    .append(" ")
                    .append(ws.getUser().getLastName());

            workSessionDTOs.add(new WorkSessionDTO()
                    .setId(ws.getId())
                    .setStartTime(ws.getStartTime())
                    .setEndTime(ws.getEndTime())
                    .setEmployeeName(sb.toString())
                    .setWorkSessionState(ws.getWorkSessionState()));
        }

        return workSessionDTOs;
    }

    public WorkSession deleteById(int id)
    {
        Optional<WorkSession> optionalWorkSession = workSessionRepository.findById(id);

        if (optionalWorkSession.isPresent())
        {
            workSessionRepository.deleteById(id);

            return optionalWorkSession.get();
        }
        else
        {
            return null;
        }
    }

    public List<WorkSession> findWorkSessionsOfEmployees(String supervisorEmail)
    {
        User user = userRepository.findByEmail(supervisorEmail);

        if (user == null)
        {
            return null;
        }

        return workSessionRepository.findWorkSessionsOfEmployees(supervisorEmail);
    }

    @Transactional
    public WorkSession startWorkSessionForUser(String email)
    {
        User user = userRepository.findByEmail(email);
        WorkSession workSession, savedWorkSession;

        if ((user == null) || (user.hasActiveWorkSession()))
        {
            return null;
        }

        workSession = new WorkSession();

        user.setActiveWorkSession(true);
        workSession.setUser(user);

        userRepository.save(user);
        savedWorkSession = workSessionRepository.save(workSession);

        return savedWorkSession;
    }

    @Transactional
    public WorkSession stopWorkSessionForUser(String email)
    {
        User user = userRepository.findByEmail(email);
        List<WorkSession> workSessions;
        WorkSession latestWorkSession, savedWorkSession;

        if ((user == null) || (!user.hasActiveWorkSession()))
        {
            return null;
        }

        // Get work sessions ordered by startTime in descending order
        workSessions = workSessionRepository.findByUserEmail(email, PageRequest.of(0, 1, Sort.by("startTime").descending()));

        if (workSessions.isEmpty())
        {
            return null;
        }

        // Get the current active work session
        latestWorkSession = workSessions.get(0);

        user.setActiveWorkSession(false);
        latestWorkSession.setEndTime(LocalDateTime.now());

        userRepository.save(user);
        savedWorkSession = workSessionRepository.save(latestWorkSession);

        return savedWorkSession;
    }
}
