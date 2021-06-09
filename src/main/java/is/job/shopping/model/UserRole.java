package is.job.shopping.model;


import is.job.shopping.model.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {
    private RoleEnum role;

    public UserRole() {
    }

    @Enumerated(EnumType.STRING)
    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
