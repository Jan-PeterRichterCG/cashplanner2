package eu.jprichter.cashplanner2.general.common.api;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the {@link NlsBundle} for this application.
 */
public interface NlsBundleApplicationRoot extends NlsBundle {

  /**
   * @see eu.jprichter.cashplanner2.general.common.api.exception.NoActiveUserException
   *
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("There is currently no user logged in")
  NlsMessage errorNoActiveUser();

  /**
   * @see eu.jprichter.cashplanner2.bankintegration.common.api.exception.ReadFileException
   *
   * @param filename is the filename used to read when the error occurred.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The file {path} could not be read!")
  NlsMessage errorReadFile(@Named("path") Object path);

  /**
   * @see eu.jprichter.cashplanner2.bankintegration.common.api.exception.SyntaxErrorException
   *
   * @param syntaxError is the syntax error that occurred while parsing the input.
   * @return the {@link NlsMessage}.
   */
  @NlsBundleMessage("The input contains a syntax error at or near \"{syntaxError}\".")
  NlsMessage errorSyntaxError(@Named("syntaxError") Object syntaxError);

}
