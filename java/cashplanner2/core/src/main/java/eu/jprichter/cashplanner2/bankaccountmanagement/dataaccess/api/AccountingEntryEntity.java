package eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import javax.persistence.Entity;
import javax.persistence.Table;

import eu.jprichter.cashplanner2.bankaccountmanagement.common.api.AccountingEntry;
import eu.jprichter.cashplanner2.general.dataaccess.api.ApplicationPersistenceEntity;

/**
 * The implementation of the {@link AccountingEntry}
 *
 * @author jrichter
 * @since 0.0.1
 */
@Entity
@Table(name = "AccountingEntry")
public class AccountingEntryEntity extends ApplicationPersistenceEntity implements AccountingEntry {

  private static final long serialVersionUID = 1L;

  private LocalDate dateOfBookkeepingEntry;

  private LocalDate valueDate;

  private String postingText;

  private BigDecimal amount;

  private Currency currency;

  @SuppressWarnings("javadoc")
  public LocalDate getDateOfBookkeepingEntry() {

    return this.dateOfBookkeepingEntry;
  }

  @SuppressWarnings("javadoc")
  public void setDateOfBookkeepingEntry(LocalDate dateOfBookkeepingEntry) {

    this.dateOfBookkeepingEntry = dateOfBookkeepingEntry;
  }

  @SuppressWarnings("javadoc")
  public LocalDate getValueDate() {

    return this.valueDate;
  }

  @SuppressWarnings("javadoc")
  public void setValueDate(LocalDate valueDate) {

    this.valueDate = valueDate;
  }

  @SuppressWarnings("javadoc")
  public String getPostingText() {

    return this.postingText;
  }

  @SuppressWarnings("javadoc")
  public void setPostingText(String postingText) {

    this.postingText = postingText;
  }

  @SuppressWarnings("javadoc")
  public BigDecimal getAmount() {

    return this.amount;
  }

  @SuppressWarnings("javadoc")
  public void setAmount(BigDecimal amount) {

    this.amount = amount;
  }

  @SuppressWarnings("javadoc")
  public Currency getCurrency() {

    return this.currency;
  }

  @SuppressWarnings("javadoc")
  public void setCurrency(Currency currency) {

    this.currency = currency;
  }

  @SuppressWarnings("javadoc")
  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();

    result.append("[");
    result.append("ID: ");
    result.append(getId());
    result.append(", Date of Bookkeeping: ");
    result.append(this.dateOfBookkeepingEntry);
    result.append(", ValueDate: ");
    result.append(this.valueDate);
    result.append(", PostingText: ");
    result.append(this.postingText);
    result.append(", Amount/Currency: ");
    result.append(this.amount);
    result.append(" ");
    result.append(this.currency);
    result.append("]");

    return result.toString();
  }

}
