package pl.gruszm.ZephyrWork.DTOs;

import jakarta.validation.constraints.*;
import pl.gruszm.ZephyrWork.enums.RoleType;

public class RegistrationDTO
{
    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    @Positive
    private Integer supervisorId;

    @NotNull
    private RoleType role;

    public String getEmail()
    {
        return email;
    }

    public RegistrationDTO setEmail(String email)
    {
        this.email = email;

        return this;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public RegistrationDTO setFirstName(String firstName)
    {
        this.firstName = firstName;

        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public RegistrationDTO setLastName(String lastName)
    {
        this.lastName = lastName;

        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public RegistrationDTO setPassword(String password)
    {
        this.password = password;

        return this;
    }

    public Integer getSupervisorId()
    {
        return supervisorId;
    }

    public RegistrationDTO setSupervisorId(Integer supervisorId)
    {
        this.supervisorId = supervisorId;

        return this;
    }

    public RoleType getRole()
    {
        return role;
    }

    public RegistrationDTO setRole(RoleType role)
    {
        this.role = role;

        return this;
    }
}
