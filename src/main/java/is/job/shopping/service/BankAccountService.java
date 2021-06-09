package is.job.shopping.service;

import java.io.IOException;
import java.time.LocalDate;

public interface BankAccountService {
    void seedAccounts() throws IOException;

    boolean checkIfBankIsValid(String number, int cvv, String fullName, LocalDate validTo);

    boolean checkExpirationDate(LocalDate validTo);

    boolean hasEnoughBalance(String orderId, String number);

    String createUnsuccessfulTransaction(String orderId, String number);

    String createSuccessfulTransaction(String orderId, String number);

    void removeBlockedAmountsAndTransferToShop();
}
