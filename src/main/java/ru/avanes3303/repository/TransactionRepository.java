package ru.avanes3303.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avanes3303.domain.Account;
import ru.avanes3303.domain.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}
