package pl.gruszm.ZephyrWork.DTOs;

import pl.gruszm.ZephyrWork.enums.WorkSessionState;

import java.time.LocalDateTime;

public class WorkSessionDTO
{
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String employeeName;
    private WorkSessionState workSessionState;
    private String notesFromSupervisor;
    private String notesFromEmployee;


    public int getId()
    {
        return id;
    }

    public WorkSessionDTO setId(int id)
    {
        this.id = id;

        return this;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public WorkSessionDTO setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;

        return this;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public WorkSessionDTO setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;

        return this;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public WorkSessionDTO setEmployeeName(String employeeName)
    {
        this.employeeName = employeeName;

        return this;
    }

    public WorkSessionState getWorkSessionState()
    {
        return workSessionState;
    }

    public WorkSessionDTO setWorkSessionState(WorkSessionState workSessionState)
    {
        this.workSessionState = workSessionState;

        return this;
    }

    public String getNotesFromSupervisor()
    {
        return notesFromSupervisor;
    }

    public WorkSessionDTO setNotesFromSupervisor(String notesFromSupervisor)
    {
        this.notesFromSupervisor = notesFromSupervisor;

        return this;
    }

    public String getNotesFromEmployee()
    {
        return notesFromEmployee;
    }

    public WorkSessionDTO setNotesFromEmployee(String notesFromEmployee)
    {
        this.notesFromEmployee = notesFromEmployee;

        return this;
    }
}
