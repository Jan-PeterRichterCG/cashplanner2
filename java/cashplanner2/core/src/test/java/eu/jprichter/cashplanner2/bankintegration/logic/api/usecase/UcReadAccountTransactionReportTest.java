package eu.jprichter.cashplanner2.bankintegration.logic.api.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import eu.jprichter.cashplanner2.bankintegration.common.api.AccountTransactionReport.AccountTransaction;
import eu.jprichter.cashplanner2.bankintegration.common.api.exception.ReadFileException;
import eu.jprichter.cashplanner2.bankintegration.logic.api.Bankintegration;
import eu.jprichter.cashplanner2.bankintegration.logic.api.to.AccountTransactionReportTo;
import eu.jprichter.cashplanner2.general.common.base.test.ApplicationComponentTest;

/**
 * TODO jrichter This type ...
 *
 * @author jrichter
 * @since 0.0.1
 */
public class UcReadAccountTransactionReportTest extends ApplicationComponentTest {

  @Inject
  Bankintegration bankintegration;

  @SuppressWarnings("javadoc")
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  /**
   * Tests that in case of an improper filename a ReadFileException is thrown.
   *
   * @throws ReadFileException ReadFileException
   */
  @Test
  public void testReadAccountTransactionReportFileThrowsReadFileExcpetion() throws ReadFileException {

    this.thrown.expect(ReadFileException.class);
    this.thrown.expectMessage(Matchers.containsString("The file foo could not be read"));

    this.bankintegration.readAccountTransactionReportFile("foo"); // non-existing file name
    // never reached
  }

  /**
   * Tests that in case of an improper filename a ReadFileException is thrown.
   *
   */
  @Test
  public void testReadAccountTransactionReportReadsFile() {

    // TODO jrichter: make this portable
    String path = "C:/Devon-dist_3.0.0/workspaces/cashplanner2/java/cashplanner2/core/src/test/resources/file/umsaetze-47110815-2017-12-21-21-30-49.csv";

    AccountTransactionReportTo atr = this.bankintegration.readAccountTransactionReportFile(path);

    assertThat(atr.getAccountHolderName()).isEqualTo("Zaphod Beeblebrox");
    assertThat(atr.getAccountNumber()).isEqualTo("47110815");
    assertThat(atr.getBalanceAmount()).isEqualTo(BigDecimal.valueOf(-123456, 2));
    assertThat(atr.getCustomerId()).isEqualTo("1234567");
    assertThat(atr.getTitle()).isEqualTo("Kontoumsätze Girokonto");
    assertThat(atr.getBalanceCurrency()).isEqualTo(Currency.getInstance("EUR"));
    assertThat(atr.getEndDate()).isEqualTo(LocalDate.of(2017, 12, 21));
    assertThat(atr.getStartDate()).isEqualTo(LocalDate.of(2017, 10, 01));
    assertThat(atr.getAdditionalSearchOptions()).isEqualTo("keine");

    List<AccountTransaction> list = atr.getAccountTransactions();
    assertThat(list.size()).isEqualTo(10);

    int index = 0;
    for (AccountTransaction transaction : list) {
      switch (index) {
        case 0:
          assertThat(transaction.getAmount()).isEqualTo(BigDecimal.valueOf(-43210, 2));
          assertThat(transaction.getMarker()).isEqualTo("*");
          assertThat(transaction.getPostingText()).isEqualTo("Vielen Dank für den Fisch");
          assertThat(transaction.getCurrency()).isEqualTo(Currency.getInstance("EUR"));
          assertThat(transaction.getDateOfBookkeepingEntry()).isEqualTo(LocalDate.of(2017, 12, 21));
          assertThat(transaction.getValueDate()).isEqualTo(LocalDate.of(2017, 12, 21));
          break;
      }
      index++;
    }

  }

}
