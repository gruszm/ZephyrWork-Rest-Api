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
    private int supervisorId;
    private List<String> roles;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public int getSupervisorId()
    {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId)
    {
        this.supervisorId = supervisorId;
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public void addRoles(Role ... roles)
    {
        if (this.roles == null)
        {
            this.roles = new ArrayList<>();
        }

        this.roles.addAll(Arrays.stream(roles).map(r -> r.getRoleName()).toList());
    }
}
