package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class IndividualProjectApplicationTests {

  @InjectMocks private IndividualProjectApplication individualProjectApplication;

  @Mock private MyFileDatabase myFileDatabase;

  @Test
  public void testRunSetupArgument() {
    String[] args = {"setup"};
    individualProjectApplication = spy(new IndividualProjectApplication());

    individualProjectApplication.run(args);

    verify(individualProjectApplication, times(1)).resetDataFile();
    assertNotNull(IndividualProjectApplication.myFileDatabase);
  }

  @Test
  public void testRunWithoutSetupArgument() {

    String[] args = {};
    individualProjectApplication = spy(new IndividualProjectApplication());

    individualProjectApplication.run(args);

    assertNotNull(IndividualProjectApplication.myFileDatabase);
    verify(individualProjectApplication, never()).resetDataFile();
  }

  @Test
  public void testResetDataFile() {
    individualProjectApplication.resetDataFile();
    HashMap<String, Department> mapping =
        IndividualProjectApplication.myFileDatabase.getDepartmentMapping();

    assertEquals(7, mapping.size());
    assertTrue(mapping.containsKey("COMS"));
    assertTrue(mapping.get("COMS").getCourseSelection().containsKey("1004"));
  }

  @Test
  public void testOverrideDatabase() {
    MyFileDatabase testData = mock(MyFileDatabase.class);
    IndividualProjectApplication.overrideDatabase(testData);

    assertEquals(testData, IndividualProjectApplication.myFileDatabase);
  }
}
