package net.chrisrichardson.ftgo.accountingservice.web;

public class GetAccountResponse {
  private Long accountId;

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public GetAccountResponse() {
  }

  public GetAccountResponse(Long accountId) {
    this.accountId = accountId;
  }
}
