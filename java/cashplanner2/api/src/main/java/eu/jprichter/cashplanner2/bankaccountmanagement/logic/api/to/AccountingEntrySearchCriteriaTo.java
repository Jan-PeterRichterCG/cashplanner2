package eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;

import eu.jprichter.cashplanner2.general.common.api.to.AbstractSearchCriteriaTo;

/**
 * {@link SearchCriteriaTo} to find instances of
 * {@link eu.jprichter.cashplanner2.bankaccountmanagement.common.api.AccountingEntry}s.
 */
public class AccountingEntrySearchCriteriaTo extends AbstractSearchCriteriaTo {

  private static final long serialVersionUID = 1L;

  private LocalDate dateOfBookkeepingEntry;

  private LocalDate valueDate;

  private String postingText;

  private BigDecimal amount;

  private Currency currency;

  private StringSearchConfigTo postingTextOption;

  /**
   * @return dateOfBookkeepingEntryId
   */

  public LocalDate getDateOfBookkeepingEntry() {

    return dateOfBookkeepingEntry;
  }

  /**
   * @param dateOfBookkeepingEntry setter for dateOfBookkeepingEntry attribute
   */

  public void setDateOfBookkeepingEntry(LocalDate dateOfBookkeepingEntry) {

    this.dateOfBookkeepingEntry = dateOfBookkeepingEntry;
  }

  /**
   * @return valueDateId
   */

  public LocalDate getValueDate() {

    return valueDate;
  }

  /**
   * @param valueDate setter for valueDate attribute
   */

  public void setValueDate(LocalDate valueDate) {

    this.valueDate = valueDate;
  }

  /**
   * @return postingTextId
   */

  public String getPostingText() {

    return postingText;
  }

  /**
   * @param postingText setter for postingText attribute
   */

  public void setPostingText(String postingText) {

    this.postingText = postingText;
  }

  /**
   * @return amountId
   */

  public BigDecimal getAmount() {

    return amount;
  }

  /**
   * @param amount setter for amount attribute
   */

  public void setAmount(BigDecimal amount) {

    this.amount = amount;
  }

  /**
   * @return currencyId
   */

  public Currency getCurrency() {

    return currency;
  }

  /**
   * @param currency setter for currency attribute
   */

  public void setCurrency(Currency currency) {

    this.currency = currency;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getPostingText() postingText}.
   */
  public StringSearchConfigTo getPostingTextOption() {

    return this.postingTextOption;
  }

  /**
   * @param postingTextOption new value of {@link #getPostingTextOption()}.
   */
  public void setPostingTextOption(StringSearchConfigTo postingTextOption) {

    this.postingTextOption = postingTextOption;
  }

}
