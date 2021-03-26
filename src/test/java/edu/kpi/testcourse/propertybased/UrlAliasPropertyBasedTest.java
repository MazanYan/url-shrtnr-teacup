package edu.kpi.testcourse.propertybased;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.strings;

import edu.kpi.testcourse.dataservice.DataService;
import edu.kpi.testcourse.dataservice.UrlAlias;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class UrlAliasPropertyBasedTest {

  @Inject
  DataService dataService;

  @BeforeEach
  public void cleanup() {
    dataService.clear();
  }

  @Test
  public void exceptionWhenAddAliasUnderNonExistentUser_propertyBased() {
    qt().forAll(
      strings().basicLatinAlphabet().ofLengthBetween(0,10)
    ).checkAssert((String failUser) -> {
      var urlAlias = new UrlAlias("test", "test", failUser);
      assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> { dataService.addUrlAlias(urlAlias); });
    });
  }

  @Test
  public void getAlias_propertyBased() {
    qt().forAll(
      strings().basicLatinAlphabet().ofLengthBetween(2,10),
      strings().basicLatinAlphabet().ofLengthBetween(2,10),
      strings().basicLatinAlphabet().ofLengthBetween(2,10)
    ).checkAssert((String alias, String url, String user) -> {
    dataService.addUrlAlias(new UrlAlias(alias, url, user));

    var result = dataService.getUrlAlias(alias);
    assertThat(result.getUrl()).isEqualTo(url);
    });
  }

}
