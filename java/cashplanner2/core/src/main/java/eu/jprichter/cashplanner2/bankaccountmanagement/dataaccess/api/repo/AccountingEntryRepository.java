package eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

import eu.jprichter.cashplanner2.bankaccountmanagement.dataaccess.api.AccountingEntryEntity;
import eu.jprichter.cashplanner2.bankaccountmanagement.logic.api.to.AccountingEntrySearchCriteriaTo;

/**
 * {@link DefaultRepository} for {@link AccountingEntryEntity}
 */
public interface AccountingEntryRepository extends DefaultRepository<AccountingEntryEntity> {

  /**
   * @param criteria the {@link AccountingEntrySearchCriteriaTo} with the criteria to search.
   * @param pageRequest {@link Pageable} implementation used to set page properties like page size
   * @return the {@link Page} of the {@link AccountingEntryEntity} objects that matched the search.
   */
  default Page<AccountingEntryEntity> findByCriteria(AccountingEntrySearchCriteriaTo criteria) {

    AccountingEntryEntity alias = newDslAlias();
    JPAQuery<AccountingEntryEntity> query = newDslQuery(alias);

    LocalDate dateOfBookkeepingEntry = criteria.getDateOfBookkeepingEntry();
    if (dateOfBookkeepingEntry != null) {
      query.where($(alias.getDateOfBookkeepingEntry()).eq(dateOfBookkeepingEntry));
    }
    LocalDate valueDate = criteria.getValueDate();
    if (valueDate != null) {
      query.where($(alias.getValueDate()).eq(valueDate));
    }
    String postingText = criteria.getPostingText();
    if (postingText != null && !postingText.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getPostingText()), postingText, criteria.getPostingTextOption());
    }
    BigDecimal amount = criteria.getAmount();
    if (amount != null) {
      query.where($(alias.getAmount()).eq(amount));
    }
    Currency currency = criteria.getCurrency();
    if (currency != null) {
      query.where($(alias.getCurrency()).eq(currency));
    }
    addOrderBy(query, alias, criteria.getPageable().getSort());

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }

  /**
   * Add sorting to the given query on the given alias
   *
   * @param query to add sorting to
   * @param alias to retrieve columns from for sorting
   * @param sort specification of sorting
   */
  public default void addOrderBy(JPAQuery<AccountingEntryEntity> query, AccountingEntryEntity alias, Sort sort) {

    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch (next.getProperty()) {
          case "dateOfBookkeepingEntry":
            if (next.isAscending()) {
              query.orderBy($(alias.getDateOfBookkeepingEntry()).asc());
            } else {
              query.orderBy($(alias.getDateOfBookkeepingEntry()).desc());
            }
            break;
          case "valueDate":
            if (next.isAscending()) {
              query.orderBy($(alias.getValueDate()).asc());
            } else {
              query.orderBy($(alias.getValueDate()).desc());
            }
            break;
          case "postingText":
            if (next.isAscending()) {
              query.orderBy($(alias.getPostingText()).asc());
            } else {
              query.orderBy($(alias.getPostingText()).desc());
            }
            break;
          case "amount":
            if (next.isAscending()) {
              query.orderBy($(alias.getAmount()).asc());
            } else {
              query.orderBy($(alias.getAmount()).desc());
            }
            break;
          // TODO jrichter: make result sets of AccountingEntries sortable by currency
          // case "currency":
          // if (next.isAscending()) {
          // query.orderBy($(alias.getCurrency()).asc());
          // } else {
          // query.orderBy($(alias.getCurrency()).desc());
          // }
          // break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '" + next.getProperty() + "'");
        }
      }
    }
  }

}