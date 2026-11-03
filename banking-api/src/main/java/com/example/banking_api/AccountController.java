package com.example.banking_api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private List<Account> accounts = new ArrayList<>();
    private AtomicLong counter = new AtomicLong(1);

    // Create Account
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        account.setId(counter.getAndIncrement());
        accounts.add(account);
        return account;
    }

    // Get All Accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accounts;
    }

    // Get Account By ID
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accounts.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update Account
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id,
                                 @RequestBody Account updatedAccount) {

        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                account.setAccountHolderName(
                    updatedAccount.getAccountHolderName());
                account.setBalance(updatedAccount.getBalance());
                return account;
            }
        }
        return null;
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accounts.removeIf(a -> a.getId().equals(id));
        return "Account deleted successfully";
    }
}