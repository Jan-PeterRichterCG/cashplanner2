package eu.jprichter.cashplanner2.bankaccountmanagement.common.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import eu.jprichter.cashplanner2.general.common.api.ApplicationEntity;

public interface AccountingEntry extends ApplicationEntity {

  /**
   * @return dateOfBookkeepingEntryId
   */

  public LocalDate getDateOfBookkeepingEntry();

  /**
   * @param dateOfBookkeepingEntry setter for dateOfBookkeepingEntry attribute
   */

  public void setDateOfBookkeepingEntry(LocalDate dateOfBookkeepingEntry);

  /**
   * @return valueDateId
   */

  public LocalDate getValueDate();

  /**
   * @param valueDate setter for valueDate attribute
   */

  public void setValueDate(LocalDate valueDate);

  /**
   * @return postingTextId
   */

  public String getPostingText();

  /**
   * @param postingText setter for postingText attribute
   */

  public void setPostingText(String postingText);

  /**
   * @return amountId
   */

  public BigDecimal getAmount();

  /**
   * @param amount setter for amount attribute
   */

  public void setAmount(BigDecimal amount);

  /**
   * @return currencyId
   */

  public Currency getCurrency();

  /**
   * @param currency setter for currency attribute
   */

  public void setCurrency(Currency currency);

}
