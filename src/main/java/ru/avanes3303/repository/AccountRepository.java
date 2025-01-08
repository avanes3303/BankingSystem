package ru.avanes3303.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.avanes3303.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
     Account findByName(String name);
}

