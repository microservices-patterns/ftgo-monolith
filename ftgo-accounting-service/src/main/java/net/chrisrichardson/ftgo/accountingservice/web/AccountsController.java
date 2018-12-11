package net.chrisrichardson.ftgo.accountingservice.web;

import net.chrisrichardson.ftgo.accountingservice.domain.Account;
import net.chrisrichardson.ftgo.accountingservice.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/accounts")
public class AccountsController {

  @Autowired
  private AccountRepository accountRepository;

  @RequestMapping(path="/{accountId}", method= RequestMethod.GET)
  public ResponseEntity<GetAccountResponse> getAccount(@PathVariable Long accountId) {
    return accountRepository
            .findById(accountId)
            .map(Account::getId)
            .map(id -> new ResponseEntity<>(new GetAccountResponse(id), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
