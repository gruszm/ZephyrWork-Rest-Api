package pl.gruszm.ZephyrWork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "has_active_work_session", nullable = false)
    private boolean hasActiveWorkSession;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private User supervisor;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Role> roles;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<WorkSession> workSessions;

    public User()
    {
        this.hasActiveWorkSession = false;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getSupervisor()
    {
        return supervisor;
    }

    public void setSupervisor(User supervisor)
    {
        this.supervisor = supervisor;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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

    public boolean hasActiveWorkSession()
    {
        return hasActiveWorkSession;
    }

    public void setActiveWorkSession(boolean hasActiveWorkSession)
    {
        this.hasActiveWorkSession = hasActiveWorkSession;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public List<WorkSession> getWorkSessions()
    {
        return workSessions;
    }

    public void addRole(Role role)
    {
        if (roles == null)
        {
            roles = new ArrayList<>();
        }

        role.setUser(this);
        roles.add(role);
    }

    public void addRoles(Role ... roles)
    {
        if (this.roles == null)
        {
            this.roles = new ArrayList<>();
        }

        Arrays.stream(roles).forEach(r -> r.setUser(this));
        this.roles.addAll(Arrays.stream(roles).toList());
    }

    public void addWorkSession(WorkSession workSession)
    {
        if (workSessions == null)
        {
            workSessions = new ArrayList<>();
        }

        workSession.setUser(this);
        workSessions.add(workSession);
    }

    public void addWorkSessions(WorkSession ... workSessions)
    {
        if (this.workSessions == null)
        {
            this.workSessions = new ArrayList<>();
        }

        Arrays.stream(workSessions).forEach(ws -> ws.setUser(this));
        this.workSessions.addAll(Arrays.stream(workSessions).toList());
    }
}
