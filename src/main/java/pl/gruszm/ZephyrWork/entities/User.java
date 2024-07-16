package pl.gruszm.ZephyrWork.entities;

import jakarta.persistence.*;
import pl.gruszm.ZephyrWork.enums.RoleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class User
{
    private static final int DEFAULT_INTERVAL = 30;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role;

    @Column(name = "registration_interval")
    private int locationRegistrationInterval;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private User supervisor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<WorkSession> workSessions;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Avatar avatar;

    public User()
    {
        this.role = RoleType.EMPLOYEE;
        this.hasActiveWorkSession = false;
        this.locationRegistrationInterval = DEFAULT_INTERVAL;
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

    public List<WorkSession> getWorkSessions()
    {
        return workSessions;
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

    public void addWorkSessions(WorkSession... workSessions)
    {
        if (this.workSessions == null)
        {
            this.workSessions = new ArrayList<>();
        }

        Arrays.stream(workSessions).forEach(ws -> ws.setUser(this));
        this.workSessions.addAll(Arrays.stream(workSessions).toList());
    }

    public Avatar getAvatar()
    {
        return avatar;
    }

    public void setAvatar(Avatar avatar)
    {
        this.avatar = avatar;
    }

    public RoleType getRole()
    {
        return role;
    }

    public void setRole(RoleType role)
    {
        this.role = role;
    }

    public int getLocationRegistrationInterval()
    {
        return locationRegistrationInterval;
    }

    public void setLocationRegistrationInterval(int locationRegistrationInterval)
    {
        this.locationRegistrationInterval = locationRegistrationInterval;
    }
}
