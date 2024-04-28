package pl.gruszm.ZephyrWork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import pl.gruszm.ZephyrWork.enums.RoleEnum;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleType;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "role")
    private List<User> users;

    public Role()
    {
        this.roleType = RoleEnum.EMPLOYEE;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public RoleEnum getRoleType()
    {
        return roleType;
    }

    public void setRoleType(RoleEnum roleType)
    {
        this.roleType = roleType;
    }
}
