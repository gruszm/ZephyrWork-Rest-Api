package pl.gruszm.ZephyrWork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gruszm.ZephyrWork.entities.Role;
import pl.gruszm.ZephyrWork.repostitories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService
{
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    public Role findById(int id)
    {
        Optional<Role> optionalRole = roleRepository.findById(id);

        return optionalRole.isPresent() ? optionalRole.get() : null;
    }

    public Role findByName(String name)
    {
        return roleRepository.findByRoleName(name);
    }

    public List<Role> findByUserId(int id)
    {
        return roleRepository.findByUserId(id);
    }

    public List<Role> findByUserEmail(String email)
    {
        return roleRepository.findByUserEmail(email);
    }

    public Role save(Role role)
    {
        return roleRepository.save(role);
    }

    public Role deleteById(int id)
    {
        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isPresent())
        {
            roleRepository.deleteById(id);

            return optionalRole.get();
        }
        else
        {
            return null;
        }
    }
}
