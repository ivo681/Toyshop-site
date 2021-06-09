package is.job.shopping.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankAccountServiceModel {
    private String number;
    private LocalDate validTo;
    private BigDecimal balance;
    private BigDecimal blockedAmount;
    private int cvv;
}
