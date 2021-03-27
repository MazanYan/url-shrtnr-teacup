package edu.kpi.testcourse.propertybased;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.strings;

import edu.kpi.testcourse.dataservice.DataService;
import edu.kpi.testcourse.dataservice.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Objects;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class UsersPropertyBased {

  @Inject
  DataService dataService;

  @BeforeEach
  public void cleanup() {
    dataService.clear();
  }

  @Test
  public void returnNullIfUserDoesNotExists_propertyBased() {
    qt().forAll(
      strings().basicLatinAlphabet().ofLengthBetween(0,10)
    ).checkAssert((String user) -> assertThat(dataService.getUser(user)).isNull());
  }

  @Test
  public void addUser_propertyBased() {
    qt().forAll(
      strings().basicLatinAlphabet().ofLengthBetween(0, 10),
      strings().betweenCodePoints(97,122).ofLengthBetween(0,10)
    ).checkAssert((String user, String pass) -> {
      var result = dataService.addUser(new User(user, pass));

      assertThat(result).isTrue();
    });
  }
}
