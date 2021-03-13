package pl.bookstore.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bookstore.restapi.model.RoleEntity;
import pl.bookstore.restapi.model.UserEntity;
import pl.bookstore.restapi.repository.RoleRepository;
import pl.bookstore.restapi.repository.UserRepository;

import javax.transaction.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("MANAGER");
        createRoleIfNotFound("EMPLOYEE");
        createRoleIfNotFound("CUSTOMER");

        RoleEntity adminRole = roleRepository.findByName("ADMIN");
        UserEntity admin = new UserEntity();
        admin.setEmail("admin@gmail.com");
        admin.setLogin("admin");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setFirstName("John");
        admin.setLastName("Smith");
        admin.setPhone("123456789");
        admin.setRole(adminRole);
        userRepository.save(admin);
    }

    @Transactional
    RoleEntity createRoleIfNotFound(String name) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            roleRepository.save(role);
        }
        return role;
    }

}
