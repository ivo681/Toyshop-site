package is.job.shopping.model.binding;

import is.job.shopping.model.validators.FieldMatch;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegisterBindingModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotBlank
    @Length(min = 3, message = "First name must be at least 3 characters")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @Length(min = 3, message = "Last name must be at least 3 characters")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotBlank
    @Email(message = "Please enter a valid email address")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank(message = "Password field cannot be empty")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=])(?=\\S+$).{8,}", message = "Password must be at least 8 characters long, including: 1 uppercase, 1 lowercase, 1 number, 1 special character")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "Password field cannot be empty")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=])(?=\\S+$).{8,}", message = "Password must be at least 8 characters long, including: 1 uppercase, 1 lowercase, 1 number, 1 special character")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
