package eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.usecase;

import java.util.List;

import org.springframework.data.domain.Page;

import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntryEto;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntrySearchCriteriaTo;

public interface UcFindAccountingEntry {

  /**
   * Returns a AccountingEntry by its id 'id'.
   *
   * @param id The id 'id' of the AccountingEntry.
   * @return The {@link AccountingEntryEto} with id 'id'
   */
  AccountingEntryEto findAccountingEntry(long id);

  /**
   * Returns a paginated list of AccountingEntrys matching the search criteria.
   *
   * @param criteria the {@link AccountingEntrySearchCriteriaTo}.
   * @return the {@link List} of matching {@link AccountingEntryEto}s.
   */
  Page<AccountingEntryEto> findAccountingEntrys(AccountingEntrySearchCriteriaTo criteria);

}
