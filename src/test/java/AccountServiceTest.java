import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.avanes3303.domain.Account;
import ru.avanes3303.repository.AccountRepository;
import ru.avanes3303.repository.TransactionRepository;
import ru.avanes3303.service.AccountService;
import java.math.BigDecimal;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account();
        account.setId(1L);
        account.setName("TestAccount");
        account.setPassword("password123");
        account.setBalance(BigDecimal.valueOf(100.00));
    }

    @Test
    void testDeposit() {
        BigDecimal depositAmount = BigDecimal.valueOf(50.00);
        when(accountRepository.findByName("TestAccount")).thenReturn(account);

        accountService.deposit("TestAccount", depositAmount);

        assertEquals(BigDecimal.valueOf(150.00), account.getBalance());
    }

    @Test
    void testWithdraw() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(50.00);
        when(accountRepository.findByName("TestAccount")).thenReturn(account);

        accountService.withdraw("TestAccount", withdrawAmount);

        assertEquals(BigDecimal.valueOf(50.00), account.getBalance());
    }

    @Test
    void testInsufficientFunds() {
        BigDecimal withdrawAmount = BigDecimal.valueOf(200.00);
        when(accountRepository.findByName("TestAccount")).thenReturn(account);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.withdraw("TestAccount", withdrawAmount));

        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testInvalidAmount() {
        BigDecimal depositAmount = BigDecimal.valueOf(-10.00);
        when(accountRepository.findByName("TestAccount")).thenReturn(account);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.deposit("TestAccount", depositAmount));

        assertEquals("Amount must be greater than zero", exception.getMessage());
    }

    @Test
    void testAccountNotFound() {
        when(accountRepository.findByName("TestAccount")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> accountService.deposit("TestAccount", BigDecimal.valueOf(50.00)));

        assertEquals("Account not found", exception.getMessage());
    }
}
