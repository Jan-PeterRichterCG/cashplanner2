package eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;

import eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.AccountingEntryEntity;
import eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.repo.AccountingEntryRepository;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntrySearchCriteriaTo;
import eu.jprichter.cashplanner2.general.common.base.test.ApplicationComponentTest;

/**
 * Test the AccountingEntryRepository
 *
 * @author jrichter
 * @since 0.0.1
 */
public class AccountingEntryRepoTest extends ApplicationComponentTest {

  private static final Logger LOG = LoggerFactory.getLogger(AccountingEntryRepoTest.class);

  @Inject
  AccountingEntryRepository accountingEntryRepo;

  /**
   * test basic saving operations of the DAO
   */
  @Test
  @Transactional
  public void testSaveAccountingEntry() {

    AccountingEntryEntity entity = new AccountingEntryEntity();

    entity.setDateOfBookkeepingEntry(LocalDate.of(2017, 12, 9));
    entity.setValueDate(LocalDate.of(2017, 12, 10));
    entity.setPostingText("Testbuchung");
    entity.setAmount(BigDecimal.valueOf(-420815, 2));
    entity.setCurrency(Currency.getInstance("EUR"));

    AccountingEntryEntity entitySaved = this.accountingEntryRepo.save(entity);

    Long id = entitySaved.getId();
    assertThat(id).isNotNull();

    AccountingEntryEntity retrievedEntity = this.accountingEntryRepo.find(id);
    assertThat(retrievedEntity.getDateOfBookkeepingEntry()).isEqualTo(entity.getDateOfBookkeepingEntry());
    assertThat(retrievedEntity.getValueDate()).isEqualTo(entity.getValueDate());
    assertThat(retrievedEntity.getPostingText()).isEqualTo(entity.getPostingText());
    assertThat(retrievedEntity.getAmount()).isEqualTo(entity.getAmount());
    assertThat(retrievedEntity.getCurrency()).isEqualTo(entity.getCurrency());

    LOG.info("Entity saved: " + entitySaved.toString());
  }

  /**
   * test basic finding operations of the DAO - based on the "master data" in the database
   */
  @Test
  @Transactional
  public void testFindUniqueAccountingEntryByCriteria() {

    AccountingEntrySearchCriteriaTo criteria = new AccountingEntrySearchCriteriaTo();
    criteria.setAmount(BigDecimal.valueOf(4299, 2));
    criteria.setCurrency(Currency.getInstance("EUR"));
    criteria.setDateOfBookkeepingEntry(LocalDate.of(2017, 12, 02));
    criteria.setPostingText("Test Data 1");
    criteria.setValueDate(LocalDate.of(2017, 12, 01));

    Sort sort = JpaSort.unsafe(Direction.ASC, "valueDate");
    Pageable pageable = PageRequest.of(0, 100, sort);
    criteria.setPageable(pageable);

    List<AccountingEntryEntity> results = this.accountingEntryRepo.findByCriteria(criteria).getContent();

    assertThat(results.size()).isEqualTo(1);

    AccountingEntryEntity entity = results.get(0);
    assertThat(entity.getId()).isEqualTo(10000);

    LOG.info("Entity found: " + entity.toString());
  }

  /**
   * test basic finding operations of the DAO - based on the "master data" in the database
   */
  @Test
  @Transactional
  public void testFindMutipleAccountingEntriesByCriteria() {

    AccountingEntrySearchCriteriaTo criteria = new AccountingEntrySearchCriteriaTo();
    criteria.setCurrency(Currency.getInstance("EUR"));

    Sort sort = JpaSort.unsafe(Direction.ASC, "valueDate");
    Pageable pageable = PageRequest.of(0, 100, sort);
    criteria.setPageable(pageable);

    List<AccountingEntryEntity> results = this.accountingEntryRepo.findByCriteria(criteria).getContent();

    assertThat(results.size()).isEqualTo(2);

    AccountingEntryEntity entity = results.get(0);
    assertThat(entity.getId()).isEqualTo(10002);
    LOG.info("Entity 0 found: " + entity.toString());

    entity = results.get(1);
    assertThat(entity.getId()).isEqualTo(10000);
    LOG.info("Entity 1 found: " + entity.toString());
  }

}
