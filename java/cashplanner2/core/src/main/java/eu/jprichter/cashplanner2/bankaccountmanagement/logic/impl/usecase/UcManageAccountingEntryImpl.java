package eu.jprichter.cashplanner2.bankaccountmanagement.logic.impl.usecase;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.AccountingEntryEntity;
import eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.repo.AccountingEntryRepository;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntryEto;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntrySearchCriteriaTo;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.usecase.UcManageAccountingEntry;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.usecase.UcManageAccountingEntryAction;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.base.usecase.AbstractAccountingEntryUc;
import eu.jprichter.cashplanner2.bankintegration.common.api.AccountTransactionReport.AccountTransaction;
import eu.jprichter.cashplanner2.bankintegration.logic.api.Bankintegration;
import eu.jprichter.cashplanner2.bankintegration.logic.api.to.AccountTransactionReportTo;

/**
 * Use case implementation for modifying and deleting AccountingEntrys
 */
@Named
@Validated
@Transactional
public class UcManageAccountingEntryImpl extends AbstractAccountingEntryUc implements UcManageAccountingEntry {

  @Inject
  Bankintegration bankintegration;

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageAccountingEntryImpl.class);

  @Override
  public boolean deleteAccountingEntry(long accountingEntryId) {

    AccountingEntryEntity accountingEntry = getAccountingEntryRepository().find(accountingEntryId);
    getAccountingEntryRepository().delete(accountingEntry);
    LOG.debug("The accountingEntry with id '{}' has been deleted.", accountingEntryId);
    return true;
  }

  @Override
  public AccountingEntryEto saveAccountingEntry(AccountingEntryEto accountingEntry) {

    Objects.requireNonNull(accountingEntry, "accountingEntry");

    AccountingEntryEntity accountingEntryEntity = getBeanMapper().map(accountingEntry, AccountingEntryEntity.class);

    // initialize, validate accountingEntryEntity here if necessary
    AccountingEntryEntity resultEntity = getAccountingEntryRepository().save(accountingEntryEntity);
    LOG.debug("AccountingEntry with id '{}' has been created.", resultEntity.getId());
    return getBeanMapper().map(resultEntity, AccountingEntryEto.class);
  }

  @Override
  public AccountingEntryEto importAccountingEntry(AccountingEntryEto accountingEntry,
      UcManageAccountingEntryAction action) {

    // check whether the parameters are legal
    Objects.requireNonNull(accountingEntry, "accountingEntry");
    Objects.requireNonNull(action, "action");
    if (null != accountingEntry.getId())
      throw new IllegalArgumentException(
          "Id field of AccountingEntryEto " + accountingEntry.toString() + " must be null!");

    AccountingEntryRepository accountingEntryRepo = getAccountingEntryRepository();
    AccountingEntryEto result = null;

    // prepare criteria to search for a matching entity already in the database
    AccountingEntrySearchCriteriaTo criteria = new AccountingEntrySearchCriteriaTo();
    criteria.setAmount(accountingEntry.getAmount());
    criteria.setCurrency(accountingEntry.getCurrency());
    criteria.setDateOfBookkeepingEntry(accountingEntry.getDateOfBookkeepingEntry());
    criteria.setPostingText(accountingEntry.getPostingText());
    criteria.setPageable(PageRequest.of(0, 100, JpaSort.unsafe(Direction.ASC, "valueDate")));

    List<AccountingEntryEntity> existingAccountingEntries = accountingEntryRepo.findByCriteria(criteria).getContent();

    if (existingAccountingEntries.size() == 0) {
      // no matching entity - save the new one
      AccountingEntryEntity newAccountingEntryEntity = getBeanMapper().map(accountingEntry,
          AccountingEntryEntity.class);
      newAccountingEntryEntity = accountingEntryRepo.save(newAccountingEntryEntity);
      accountingEntry.setId(newAccountingEntryEntity.getId());
      LOG.debug("New AccountEntry written: " + newAccountingEntryEntity.toString());
      result = accountingEntry;
    } else {
      if (existingAccountingEntries.size() > 1) {
        // a rare case - create a WARN line in the log
        StringBuilder logBuffer = new StringBuilder();
        for (AccountingEntryEntity entity : existingAccountingEntries) {
          logBuffer.append(entity.toString());
          if (logBuffer.length() > 500)
            break; // don't spam the log if there are too many matching entities
        }
        LOG.warn("More than one matching AccountEntries found (" + existingAccountingEntries.size() + " entities): "
            + logBuffer);
      }
      /*
       * we have no other choice than to take the fist one in the list even if the list contains more than one matching
       * AccountEntry
       */
      AccountingEntryEntity persistentAccountingEntryEntity = existingAccountingEntries.get(0);
      switch (action) {
        case UPDATE_VALUE_DATE:
          if (!persistentAccountingEntryEntity.getValueDate().equals(accountingEntry.getValueDate())) {
            persistentAccountingEntryEntity.setValueDate(accountingEntry.getValueDate());
            accountingEntryRepo.save(persistentAccountingEntryEntity);
            accountingEntry.setId(persistentAccountingEntryEntity.getId());
            LOG.debug("AccountEntry updated: " + persistentAccountingEntryEntity.toString());
            result = accountingEntry;
          } else
            LOG.debug("Account entry untouched (same value date): " + persistentAccountingEntryEntity.toString());
          // result remains null
          break;
        case NONE:
          // don't do anything
          LOG.debug("Account entry untouched (no action): " + persistentAccountingEntryEntity.toString());
          // result remains null
          break;
        default: // programming error
          throw new RuntimeException("Panic: Unknown action " + action + " encountered.");
      }
    }

    return result;
  }

  @Override
  public int importAccountingEntriesFromFile(String path) {

    AccountTransactionReportTo atr = this.bankintegration.readAccountTransactionReportFile(path);

    List<AccountTransaction> atl = atr.getAccountTransactions();

    int result = 0;
    if (atl != null && !atl.isEmpty()) {
      for (AccountTransaction at : atl) {
        if (at.getMarker() == null || at.getMarker().equals("")) {
          AccountingEntryEto ae = new AccountingEntryEto();
          ae.setAmount(at.getAmount());
          ae.setCurrency(at.getCurrency());
          ae.setPostingText(at.getPostingText());
          ae.setDateOfBookkeepingEntry(at.getDateOfBookkeepingEntry());
          ae.setValueDate(at.getValueDate());
          result += (importAccountingEntry(ae, UcManageAccountingEntryAction.UPDATE_VALUE_DATE) == null ? 0 : 1);
        }
      }
    }

    return result;
  }

}
