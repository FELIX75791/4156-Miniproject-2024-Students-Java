package dev.coms4156.project.individualproject;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/** Contains unit tests for each endpoint in RouteController. */
@WebMvcTest(RouteController.class)
public class RouteControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private MyFileDatabase mockDatabase;

  private HashMap<String, Department> mockDepartments;
  private HashMap<String, Course> mockCourses;

  /** Set up the mock behavior of the static database in IndividualProjectApplication. */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // Setup mock data
    mockDepartments = new HashMap<>();
    mockCourses = new HashMap<>();

    // Add mock data to departments and courses
    Course mockCourse = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400);
    mockCourse.setEnrolledStudentCount(5);
    mockCourses.put("1004", mockCourse);
    Department mockDepartment = new Department("COMS", mockCourses, "Luca Carloni", 2700);
    mockDepartments.put("COMS", mockDepartment);

    when(mockDatabase.getDepartmentMapping()).thenReturn(mockDepartments);

    // Override static database
    IndividualProjectApplication.overrideDatabase(mockDatabase);
  }

  @Test
  public void testIndex() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(
                    "Welcome, in order to make an API call direct your browser "
                        + "or Postman to an endpoint \n\n This can be done using the following "
                        + "format: \n\n http:127.0.0.1:8080/endpoint?arg=value"));
  }

  @Test
  public void testRetrieveDepartmentSuccess() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/retrieveDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string(mockDepartments.get("COMS").toString()));
  }

  @Test
  public void testRetrieveCourseSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/retrieveCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string(mockCourses.get("1004").toString()));
  }

  @Test
  public void testIsCourseFullSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/isCourseFull")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string(String.valueOf(mockCourses.get("1004").isCourseFull())));
  }

  @Test
  public void testGetMajorCtFromDeptSuccess() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/getMajorCountFromDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(
                    "There are: "
                        + mockDepartments.get("COMS").getNumberOfMajors()
                        + " majors in the department"));
  }

  @Test
  public void testIdentifyDeptChairSuccess() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/idDeptChair").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(
                    mockDepartments.get("COMS").getDepartmentChair()
                        + " is the department chair."));
  }

  @Test
  public void testFindCourseLocationSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/findCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(
                    mockCourses.get("1004").getCourseLocation()
                        + " is where the course is located."));
  }

  @Test
  public void testFindCourseInstructorSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/findCourseInstructor")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(
                    mockCourses.get("1004").getInstructorName()
                        + " is the instructor for the course."));
  }

  @Test
  public void testFindCourseTimeSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/findCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string("The course meets at: " + mockCourses.get("1004").getCourseTimeSlot()));
  }

  @Test
  public void testAddMajorToDeptSuccess() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.patch("/addMajorToDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated successfully"));
  }

  @Test
  public void testRemoveMajorFromDeptSuccess() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.patch("/removeMajorFromDept").param("deptCode", "COMS"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attribute was updated or is at minimum"));
  }

  @Test
  public void testDropStudentSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "1004"))
        .andExpect(status().isOk())
        .andExpect(content().string("Student has been dropped."));
  }

  @Test
  public void testSetEnrollmentCountSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("count", "20"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void testChangeCourseTimeSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/changeCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("time", "12:00-13:00"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void testChangeCourseTeacherSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/changeCourseTeacher")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("teacher", "Jane Smith"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void testChangeCourseLocationSuccess() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/changeCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "1004")
                .param("location", "501 IAB"))
        .andExpect(status().isOk())
        .andExpect(content().string("Attributed was updated successfully."));
  }

  @Test
  public void testRetrieveDepartmentNotFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/retrieveDept").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRetrieveCourseDepartmentNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/retrieveCourse")
                .param("deptCode", "MATH")
                .param("courseCode", "1004"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRetrieveCourseNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/retrieveCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "9999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testIsCourseFullNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/isCourseFull")
                .param("deptCode", "COMS")
                .param("courseCode", "9999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testGetMajorCtFromDeptNotFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/getMajorCountFromDept").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testIdentifyDeptChairNotFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/idDeptChair").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testFindCourseLocationNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/findCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "9999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseInstructorNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/findCourseInstructor")
                .param("deptCode", "COMS")
                .param("courseCode", "9999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testFindCourseTimeNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/findCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "9999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testAddMajorToDeptNotFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.patch("/addMajorToDept").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testRemoveMajorFromDeptNotFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.patch("/removeMajorFromDept").param("deptCode", "MATH"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Department Not Found"));
  }

  @Test
  public void testDropStudentNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/dropStudentFromCourse")
                .param("deptCode", "COMS")
                .param("courseCode", "9999"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testSetEnrollmentCountNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/setEnrollmentCount")
                .param("deptCode", "COMS")
                .param("courseCode", "9999")
                .param("count", "20"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseTimeNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/changeCourseTime")
                .param("deptCode", "COMS")
                .param("courseCode", "9999")
                .param("time", "12:00-13:00"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseTeacherNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/changeCourseTeacher")
                .param("deptCode", "COMS")
                .param("courseCode", "9999")
                .param("teacher", "Jane Smith"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }

  @Test
  public void testChangeCourseLocationNotFound() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/changeCourseLocation")
                .param("deptCode", "COMS")
                .param("courseCode", "9999")
                .param("location", "501 IAB"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Course Not Found"));
  }
}
