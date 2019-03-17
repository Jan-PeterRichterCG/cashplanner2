package eu.jprichter.cashplanner2.bankaccountmanagement.logic.base.usecase;

import javax.inject.Inject;

import eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.repo.AccountingEntryRepository;
import eu.jprichter.cashplanner2.general.logic.base.AbstractUc;

/**
 * Abstract use case for AccountingEntrys, which provides access to the commonly necessary data access objects.
 */
public class AbstractAccountingEntryUc extends AbstractUc {

  /** @see #getAccountingEntryRepository() */
  @Inject
  private AccountingEntryRepository accountingEntryRepository;

  /**
   * Returns the field 'accountingEntryRepository'.
   * 
   * @return the {@link AccountingEntryRepository} instance.
   */
  public AccountingEntryRepository getAccountingEntryRepository() {

    return this.accountingEntryRepository;
  }

}
