package edu.kpi.testcourse.propertybased;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.strings;

import edu.kpi.testcourse.dataservice.DataService;
import edu.kpi.testcourse.dataservice.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class UsersPropertyBasedTest {
  @Inject
  DataService dataService;

  @Test
  public void shouldAddUser_propertyBased() {
    qt().forAll(
      strings().betweenCodePoints(97,122).ofLengthBetween(0,10),
      strings().basicLatinAlphabet().ofLengthBetween(0,10)
    ).check((String username, String password) -> {
      User testUser = new User(username, password);

      boolean result = dataService.addUser(testUser);

      if (username.length() == 0 || password.length() == 0 && !result) {
        return true;
      }
      else if (dataService.getUser(username) != null && !result) {
        return true;
      }
      return result;
    });
  }
}
