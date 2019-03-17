package eu.jprichter.cashplanner2.bankaccountmanagement.logic.impl.usecase;

import java.util.Optional;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.AccountingEntryEntity;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntryEto;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntrySearchCriteriaTo;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.usecase.UcFindAccountingEntry;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.base.usecase.AbstractAccountingEntryUc;

/**
 * Use case implementation for searching, filtering and getting AccountingEntrys
 */
@Named
@Validated
@Transactional
public class UcFindAccountingEntryImpl extends AbstractAccountingEntryUc implements UcFindAccountingEntry {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcFindAccountingEntryImpl.class);

  @Override
  public AccountingEntryEto findAccountingEntry(long id) {

    LOG.debug("Get AccountingEntry with id {} from database.", id);
    Optional<AccountingEntryEntity> foundEntity = getAccountingEntryRepository().findById(id);
    if (foundEntity.isPresent())
      return getBeanMapper().map(foundEntity.get(), AccountingEntryEto.class);
    else
      return null;
  }

  @Override
  public Page<AccountingEntryEto> findAccountingEntrys(AccountingEntrySearchCriteriaTo criteria) {

    Page<AccountingEntryEntity> accountingentrys = getAccountingEntryRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(accountingentrys, AccountingEntryEto.class);
  }

}
