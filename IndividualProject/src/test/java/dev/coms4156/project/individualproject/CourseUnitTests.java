package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  /** The test course instance used for testing. */
  public static Course testCourse;

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void testToString() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void testReassignInstructor() {
    testCourse.reassignInstructor("Adam Cannon");
    assertEquals("Adam Cannon", testCourse.getInstructorName());
  }

  @Test
  public void testReassignLocation() {
    testCourse.reassignLocation("309 HAV");
    assertEquals("309 HAV", testCourse.getCourseLocation());
  }

  @Test
  public void testReassignTime() {
    testCourse.reassignTime("4:10-5:25");
    assertEquals("4:10-5:25", testCourse.getCourseTimeSlot());
  }

  @Test
  public void testSetEnrolledStudentCount() {
    testCourse.setEnrolledStudentCount(248);
    assertFalse(testCourse.isCourseFull());
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }

  @Test
  public void testEnrollStudent() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.enrollStudent());
  }

  @Test
  public void testDropStudent() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(testCourse.dropStudent());
    assertFalse(testCourse.dropStudent());
  }
}
