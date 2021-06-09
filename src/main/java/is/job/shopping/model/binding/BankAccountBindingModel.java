package is.job.shopping.model.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BankAccountBindingModel {
    private String number;
    private LocalDate validTo;
    private int cvv;
    private String fullName;

    public BankAccountBindingModel() {
    }

    @NotBlank(message = "Card number cannot be empty")
    @Pattern(regexp = "\\d{16}", message = "Please enter a valid 16 digit card number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Card number expiry date cannot be in the present or past")
    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    @Min(value = 100, message = "CVV number must be exactly 3 digits")
    @Max(value = 999, message = "CVV number must be exactly 3 digits")
    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @NotBlank
    @Length(min = 5, message = "Full name must be at least 5 characters")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
