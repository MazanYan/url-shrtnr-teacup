package edu.kpi.testcourse.propertybased;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.strings;

import edu.kpi.testcourse.auth.PasswordHash;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.Test;

@MicronautTest
public class PasswordHashPropertyBasedTest {
  @Test
  public void shouldHashAndValidatePasswords_propertyBased() {
    qt().forAll(
      strings().basicLatinAlphabet().ofLengthBetween(0, 10)
    ).check((String password) -> {
      String secret = "testSecret";
      String secretHash = "";
      try {
        secretHash = PasswordHash.createHash(secret);
        boolean validation = PasswordHash.validatePassword(secret, secretHash);
      } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        return false;
      }
      return true;
    });
  }
}
