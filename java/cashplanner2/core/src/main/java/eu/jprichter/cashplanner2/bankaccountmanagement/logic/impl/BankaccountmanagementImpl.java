package eu.jprichter.cashplanner2.bankaccountmanagement.logic.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;

import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.Bankaccountmanagement;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntryEto;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntrySearchCriteriaTo;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.usecase.UcFindAccountingEntry;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.usecase.UcManageAccountingEntry;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.usecase.UcManageAccountingEntryAction;
import eu.jprichter.cashplanner2.general.logic.base.AbstractComponentFacade;

/**
 * Implementation of component interface of bankaccountmanagement
 */
@Named
public class BankaccountmanagementImpl extends AbstractComponentFacade implements Bankaccountmanagement {

  @Inject
  private UcFindAccountingEntry ucFindAccountingEntry;

  @Inject
  private UcManageAccountingEntry ucManageAccountingEntry;

  @Override
  public AccountingEntryEto findAccountingEntry(long id) {

    return this.ucFindAccountingEntry.findAccountingEntry(id);
  }

  @Override
  public Page<AccountingEntryEto> findAccountingEntrys(AccountingEntrySearchCriteriaTo criteria) {

    return this.ucFindAccountingEntry.findAccountingEntrys(criteria);
  }

  @Override
  public AccountingEntryEto saveAccountingEntry(AccountingEntryEto accountingentry) {

    return this.ucManageAccountingEntry.saveAccountingEntry(accountingentry);
  }

  @Override
  public boolean deleteAccountingEntry(long id) {

    return this.ucManageAccountingEntry.deleteAccountingEntry(id);
  }

  @Override
  public AccountingEntryEto importAccountingEntry(AccountingEntryEto accountingEnty,
      UcManageAccountingEntryAction action) {

    return this.ucManageAccountingEntry.importAccountingEntry(accountingEnty, action);
  }

  @Override
  public int importAccountingEntriesFromFile(String path) {

    return this.ucManageAccountingEntry.importAccountingEntriesFromFile(path);
  }

}
