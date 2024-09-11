package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains unit tests for Department.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentUnitTests {

  /** The test course instance used for testing. */
  public static Department testDept;

  @BeforeAll
  static void setUpDepartmentForTesting() {
    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};
    // data for coms dept
    Course coms1004 = new Course("Adam Cannon", locations[0], times[0], 400);
    coms1004.setEnrolledStudentCount(249);
    Course coms3134 = new Course("Brian Borowski", locations[2], times[1], 250);
    coms3134.setEnrolledStudentCount(242);

    HashMap<String, Course> courses = new HashMap<>();
    courses.put("1004", coms1004);
    courses.put("3134", coms3134);

    testDept = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  /** Test the getNumberOfMajors method. */
  @Test
  public void testGetNumberOfMajors() {
    setUpDepartmentForTesting();
    assertEquals(2700, testDept.getNumberOfMajors());
  }

  /** Test the getDepartmentChair method. */
  @Test
  public void testGetDepartmentChair() {
    setUpDepartmentForTesting();
    assertEquals("Luca Carloni", testDept.getDepartmentChair());
  }

  /** Test adding a course to the department. */
  @Test
  public void testAddCourse() {
    Course newCourse = new Course("John Kender", "102 Mudd", "1:10-2:25", 200);
    testDept.addCourse("4276", newCourse);
    assertEquals(newCourse, testDept.getCourseSelection().get("4276"));
  }

  /** Test creating and adding a new course. */
  @Test
  public void testCreateCourse() {
    testDept.createCourse("4995", "Andrew Gelman", "305 Schermerhorn", "10:10-11:25", "100");
    Course createdCourse = testDept.getCourseSelection().get("4995");
    assertNotNull(createdCourse);
    assertEquals("Andrew Gelman", createdCourse.getInstructorName());
    assertEquals("305 Schermerhorn", createdCourse.getCourseLocation());
    assertEquals("10:10-11:25", createdCourse.getCourseTimeSlot());
  }

  /** Test incrementing the number of majors. */
  @Test
  public void testAddMajorToDept() {
    setUpDepartmentForTesting();
    testDept.addMajorToDept();
    assertEquals(2701, testDept.getNumberOfMajors());
  }

  /** Test decrementing the number of majors. */
  @Test
  public void testRemoveMajorFromDept() {
    setUpDepartmentForTesting();
    testDept.removeMajorFromDept();
    assertEquals(2699, testDept.getNumberOfMajors());
    HashMap<String, Course> courses = new HashMap<>();
    testDept = new Department("COMS", courses, "Luca Carloni", 0);
    assertEquals(0, testDept.getNumberOfMajors());
  }

  /** Test the toString method. */
  @Test
  public void testToString() {
    setUpDepartmentForTesting();
    String expectedOutput =
        "COMS 1004: \nInstructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n"
            + "COMS 3134: \nInstructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n";
    assertEquals(expectedOutput, testDept.toString());
  }
}
