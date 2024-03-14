package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.entities.WorkSession;
import pl.gruszm.ZephyrWork.repostitories.WorkSessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkSessionService
{
    private WorkSessionRepository workSessionRepository;

    @Autowired
    public WorkSessionService(WorkSessionRepository workSessionRepository)
    {
        this.workSessionRepository = workSessionRepository;
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

    public List<WorkSession> findByUserEmail(String email)
    {
        return workSessionRepository.findByUserEmail(email);
    }

    public WorkSession save(WorkSession workSession)
    {
        return workSessionRepository.save(workSession);
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
}
