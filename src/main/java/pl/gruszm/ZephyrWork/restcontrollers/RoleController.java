package pl.gruszm.ZephyrWork.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gruszm.ZephyrWork.entities.Role;
import pl.gruszm.ZephyrWork.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController
{
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService)
    {
        this.roleService = roleService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") int id)
    {
        Role role = roleService.findById(id);

        if (role == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(role);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name)
    {
        Role role = roleService.findByName(name);

        if (role == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(role);
        }
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<List<Role>> getRoleByUserEmail(@PathVariable("email") String email)
    {
        List<Role> roles = roleService.findByUserEmail(email);

        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role)
    {
        Role savedRole = roleService.save(role);

        if (savedRole == null)
        {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(savedRole);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable("id") int id)
    {
        Role role = roleService.deleteById(id);

        if (role == null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        else
        {
            return ResponseEntity.ok(role);
        }
    }
}
