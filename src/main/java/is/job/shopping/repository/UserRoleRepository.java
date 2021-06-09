package is.job.shopping.repository;


import is.job.shopping.model.UserRole;
import is.job.shopping.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findByRole(RoleEnum roleEnum);

}
