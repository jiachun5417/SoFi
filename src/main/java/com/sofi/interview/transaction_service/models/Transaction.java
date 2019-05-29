package com.sofi.interview.transaction_service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class Transaction {

  @JsonProperty("user-id")
  private int userId;

  @JsonProperty
  private String merchant;

  @JsonProperty
  private double price;

  @JsonProperty("purchase-date")
  private String purchaseDate; // Leave it as string for demo purpose, can be able to convert to LocalDateTime upon input format

  @JsonProperty("tx-id")
  private int txId;

  public boolean validate() {
    return userId > 0 &&
        StringUtils.isNotEmpty(merchant) &&
        price > 0.0 &&
        txId > 0;
  }
}
