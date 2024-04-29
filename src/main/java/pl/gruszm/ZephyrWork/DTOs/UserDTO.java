package pl.gruszm.ZephyrWork.DTOs;

public class UserDTO
{
    private String email;
    private String firstName;
    private String lastName;
    private Integer supervisorId;
    private String roleName;

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
}