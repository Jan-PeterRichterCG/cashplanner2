package eu.jprichter.cashplanner2.bankintegration.logic.impl;

import javax.inject.Inject;
import javax.inject.Named;

import eu.jprichter.cashplanner2.bankintegration.logic.api.Bankintegration;
import eu.jprichter.cashplanner2.bankintegration.logic.api.to.AccountTransactionReportTo;
import eu.jprichter.cashplanner2.bankintegration.logic.api.usecase.UcReadAccountTransactionReport;
import eu.jprichter.cashplanner2.general.logic.base.AbstractComponentFacade;

/**
 * Implementation of component interface of bankintegration
 *
 * @author jrichter
 * @since 0.0.1
 */
@Named
public class BankintegrationImpl extends AbstractComponentFacade implements Bankintegration {

  @Inject
  private UcReadAccountTransactionReport ucReadAccountTransactionReport;

  /**
   * The constructor.
   */
  public BankintegrationImpl() {

    super();
  }

  @Override
  public AccountTransactionReportTo readAccountTransactionReportFile(String filename) {

    return this.ucReadAccountTransactionReport.readAccountTransactionReportFile(filename);
  }

}
