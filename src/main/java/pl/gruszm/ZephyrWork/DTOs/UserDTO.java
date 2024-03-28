package pl.gruszm.ZephyrWork.DTOs;

import pl.gruszm.ZephyrWork.entities.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDTO
{
    private String email;
    private String firstName;
    private String lastName;
    private Integer supervisorId;
    private List<String> roles;

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

    public List<String> getRoles()
    {
        return roles;
    }

    public UserDTO addRoles(Role ... roles)
    {
        if (this.roles == null)
        {
            this.roles = new ArrayList<>();
        }

        this.roles.addAll(Arrays.stream(roles).map(r -> r.getRoleName()).toList());

        return this;
    }
}
