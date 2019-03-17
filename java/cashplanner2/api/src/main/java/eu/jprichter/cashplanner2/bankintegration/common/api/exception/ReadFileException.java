package eu.jprichter.cashplanner2.bankintegration.common.api.exception;

import eu.jprichter.cashplanner2.general.common.api.NlsBundleApplicationRoot;
import eu.jprichter.cashplanner2.general.common.api.exception.ApplicationTechnicalException;

/**
 * This exception is thrown if a file could not be read for some technical reasons
 *
 * @author jrichter
 * @since 0.0.1
 */
public class ReadFileException extends ApplicationTechnicalException {

  /**
   * default serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param path to the file that caused the error
   */
  public ReadFileException(String path) {

    super(createBundle(NlsBundleApplicationRoot.class).errorReadFile(path));
  }

  /**
   * The other constructor.
   *
   * @param t the throwable caught - will be wrapped
   * @param path to the file that caused the error
   */
  public ReadFileException(Throwable t, String path) {

    super(t, createBundle(NlsBundleApplicationRoot.class).errorReadFile(path));
  }
}
