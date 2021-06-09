package is.job.shopping.service.impl;

import is.job.shopping.model.UserRole;
import is.job.shopping.model.enums.RoleEnum;
import is.job.shopping.repository.UserRoleRepository;
import is.job.shopping.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void seedRoles() {
        if (this.userRoleRepository.count() == 0){
            Arrays.stream(RoleEnum.values()).forEach(e -> {
                UserRole userRole = new UserRole();
                userRole.setRole(e);
                this.userRoleRepository.save(userRole);
            });
        }
    }
}
