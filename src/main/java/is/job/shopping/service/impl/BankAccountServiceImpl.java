package is.job.shopping.service.impl;

import com.google.gson.Gson;
import is.job.shopping.model.BankAccount;
import is.job.shopping.model.BankTransaction;
import is.job.shopping.model.dtos.BankAccountSeedDto;
import is.job.shopping.model.enums.TransactionStatusEnum;
import is.job.shopping.repository.BankAccountRepository;
import is.job.shopping.repository.BankTransactionRepository;
import is.job.shopping.service.BankAccountService;
import is.job.shopping.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final static String BANK_ACCOUNTS_PATH = "src/main/resources/static/json/bankAccounts.json";
    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankTransactionRepository bankTransactionRepository, OrderService orderService, ModelMapper modelMapper, Gson gson) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedAccounts() throws IOException {
        if (this.bankAccountRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(BANK_ACCOUNTS_PATH)));
            BankAccountSeedDto[] bankAccountSeedDtos = this.gson.fromJson(content, BankAccountSeedDto[].class);
            for (BankAccountSeedDto bankAccountSeedDto : bankAccountSeedDtos) {
                BankAccount bankAccount = this.modelMapper.map(bankAccountSeedDto, BankAccount.class);
                bankAccount.setValidTo(bankAccount.getValidFrom().plusYears(3));
                this.bankAccountRepository.save(bankAccount);
            }

        }
    }

    @Override
    public boolean checkIfBankIsValid(String number, int cvv, String fullName, LocalDate validTo) {
        LocalDate lower = LocalDate.of(validTo.getYear(), validTo.getMonth(), 1);
        LocalDate upper = LocalDate.of(validTo.getYear(), validTo.getMonth().plus(1), 1);
        return this.bankAccountRepository.findAccountByDetails(number, cvv, fullName, lower, upper).isPresent();
    }

    @Override
    public boolean checkExpirationDate(LocalDate validTo) {
        return false;
    }

    @Override
    public boolean hasEnoughBalance(String orderId, String number) {
        BigDecimal orderTotal = this.orderService.getOrderTotal(orderId);
        return this.bankAccountRepository.checkIfAccountHasEnoughBalance(number, orderTotal).isPresent();
    }

    @Override
    public String createUnsuccessfulTransaction(String orderId, String number) {
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setBankAccount(this.bankAccountRepository.findByNumber(number).get());
        bankTransaction.setTransactionStatus(TransactionStatusEnum.DECLINED);
        bankTransaction.setAmount(this.orderService.getOrderTotal(orderId));
        bankTransaction.setDate(LocalDate.now());
        bankTransaction = this.bankTransactionRepository.save(bankTransaction);
        return bankTransaction.getId();
    }

    @Override
    public String createSuccessfulTransaction(String orderId, String number) {
        BankAccount bankAccount = this.bankAccountRepository.findByNumber(number).get();
        BigDecimal orderTotal = this.orderService.getOrderTotal(orderId);
        bankAccount.setBlockedAmount(bankAccount.getBlockedAmount().add(orderTotal));
        bankAccount = this.bankAccountRepository.save(bankAccount);
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setTransactionStatus(TransactionStatusEnum.SUCCESSFULL);
        bankTransaction.setAmount(orderTotal);
        bankTransaction.setDate(LocalDate.now());
        bankTransaction = this.bankTransactionRepository.save(bankTransaction);
        return bankTransaction.getId();
    }

    //This is only for display, should be in separate Banking application
    @Override
    public void removeBlockedAmountsAndTransferToShop() {
        List<BankAccount> bankAccounts = this.bankAccountRepository.findAll();
        BigDecimal dailyProfits = BigDecimal.ZERO;
        for (BankAccount bankAccount : bankAccounts) {
            BigDecimal blockedAmount = bankAccount.getBlockedAmount();
            bankAccount.setBalance(bankAccount.getBalance().subtract(blockedAmount));
            bankAccount.setBlockedAmount(BigDecimal.ZERO);
            dailyProfits = dailyProfits.add(blockedAmount);
            this.bankAccountRepository.save(bankAccount);
        }
        BankAccount shopAccount = this.bankAccountRepository.findByNumber("1111222233334444").get();
        shopAccount.setBalance(shopAccount.getBalance().add(dailyProfits));
        this.bankAccountRepository.save(shopAccount);
    }
}
