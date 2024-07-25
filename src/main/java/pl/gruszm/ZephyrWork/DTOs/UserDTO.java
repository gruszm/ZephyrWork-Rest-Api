package pl.gruszm.ZephyrWork.DTOs;

import pl.gruszm.ZephyrWork.entities.User;

public class UserDTO
{
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer supervisorId;
    private String roleName;
    private int locationRegistrationInterval;
    private int startingHour;
    private int startingMinute;
    private int endingHour;
    private int endingMinute;
    private boolean forceStartWorkSession;

    public UserDTO(User user)
    {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
        setSupervisorId((user.getSupervisor() != null) ? user.getSupervisor().getId() : null);
        setRoleName(user.getRole().name());
        setLocationRegistrationInterval(user.getLocationRegistrationInterval());
        setStartingHour(user.getStartingTime().getHour());
        setStartingMinute(user.getStartingTime().getMinute());
        setEndingHour(user.getEndingTime().getHour());
        setEndingMinute(user.getEndingTime().getMinute());
        setForceStartWorkSession(user.isForceStartWorkSession());
    }

    public int getId()
    {
        return id;
    }

    public UserDTO setId(int id)
    {
        this.id = id;

        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public UserDTO setEmail(String email)
    {
        this.email = email;

        return this;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public UserDTO setFirstName(String firstName)
    {
        this.firstName = firstName;

        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public UserDTO setLastName(String lastName)
    {
        this.lastName = lastName;

        return this;
    }

    public Integer getSupervisorId()
    {
        return supervisorId;
    }

    public UserDTO setSupervisorId(Integer supervisorId)
    {
        this.supervisorId = supervisorId;

        return this;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public UserDTO setRoleName(String roleName)
    {
        this.roleName = roleName;

        return this;
    }

    public int getLocationRegistrationInterval()
    {
        return locationRegistrationInterval;
    }

    public UserDTO setLocationRegistrationInterval(int locationRegistrationInterval)
    {
        this.locationRegistrationInterval = locationRegistrationInterval;

        return this;
    }

    public int getStartingHour()
    {
        return startingHour;
    }

    public UserDTO setStartingHour(int startingHour)
    {
        this.startingHour = startingHour;

        return this;
    }

    public int getStartingMinute()
    {
        return startingMinute;
    }

    public UserDTO setStartingMinute(int startingMinute)
    {
        this.startingMinute = startingMinute;

        return this;
    }

    public int getEndingHour()
    {
        return endingHour;
    }

    public UserDTO setEndingHour(int endingHour)
    {
        this.endingHour = endingHour;

        return this;
    }

    public int getEndingMinute()
    {
        return endingMinute;
    }

    public UserDTO setEndingMinute(int endingMinute)
    {
        this.endingMinute = endingMinute;

        return this;
    }

    public boolean isForceStartWorkSession()
    {
        return forceStartWorkSession;
    }

    public UserDTO setForceStartWorkSession(boolean forceStartWorkSession)
    {
        this.forceStartWorkSession = forceStartWorkSession;

        return this;
    }
}
