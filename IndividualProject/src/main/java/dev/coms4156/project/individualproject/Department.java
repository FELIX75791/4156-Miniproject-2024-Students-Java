package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import lombok.Getter;

/**
 * Represents a department within an educational institution. This class stores information about
 * the department, including its code, courses offered, department chair, and number of majors.
 */
public class Department implements Serializable {

  @Serial private static final long serialVersionUID = 234567L;

  /** How much info need to define a course. */
  private static final int COURSE_DETAILS_LEN = 4;

  /** A map of course codes to the corresponding Course objects in the department. */
  private final Map<String, Course> courses;

  /**
   * The name of the department chair. -- GETTER -- Gets the name of the department chair.
   *
   * @return The name of the department chair.
   */
  @Getter private final String departmentChair;

  /** The department's unique code. */
  private final String deptCode;

  /**
   * The number of majors enrolled in the department. -- GETTER -- Gets the number of majors in the
   * department.
   *
   * @return The number of majors.
   */
  @Getter private int numberOfMajors;

  /**
   * Constructs a new Department object with the given parameters.
   *
   * @param deptCode The code of the department.
   * @param courses A HashMap containing courses offered by the department.
   * @param departmentChair The name of the department chair.
   * @param numberOfMajors The number of majors in the department.
   */
  public Department(
      String deptCode, Map<String, Course> courses, String departmentChair, int numberOfMajors) {
    this.courses = courses;
    this.departmentChair = departmentChair;
    this.numberOfMajors = numberOfMajors;
    this.deptCode = deptCode;
  }

  /**
   * Gets the courses offered by the department.
   *
   * @return A HashMap containing courses offered by the department.
   */
  public Map<String, Course> getCourseSelection() {
    return this.courses;
  }

  /** Increases the number of majors in the department by one. */
  public void addMajorToDept() {
    numberOfMajors++;
  }

  /** Decreases the number of majors by one if it's greater than zero. */
  public void removeMajorFromDept() {
    if (this.numberOfMajors > 0) {
      numberOfMajors--;
    }
  }

  /**
   * Adds a new course to the department's course selection.
   *
   * @param courseId The ID of the course to add.
   * @param course The Course object to add.
   */
  public void addCourse(String courseId, Course course) {
    courses.put(courseId, course);
  }

  /**
   * Creates and adds a new course to the department's course selection.
   *
   * @param courseId The ID of the new course.
   * @param courseDetails Details of the course, including instructorName, courseLocation,
   *     courseTimeSlot, and enrollCapacity.
   */
  public void createCourse(String courseId, String... courseDetails) {
    if (courseDetails.length != COURSE_DETAILS_LEN) {
      throw new IllegalArgumentException("Invalid number of course details provided.");
    }

    Course newCourse =
        new Course(
            courseDetails[0],
            courseDetails[1],
            courseDetails[2],
            Integer.parseInt(courseDetails[3]));
    addCourse(courseId, newCourse);
  }

  /**
   * Returns a string representation of the department, including its code and the courses offered.
   *
   * @return A string representing the department.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, Course> entry : courses.entrySet()) {
      String key = entry.getKey();
      Course value = entry.getValue();
      result
          .append(deptCode)
          .append(' ')
          .append(key)
          .append(": ")
          .append(value.toString())
          .append('\n');
    }
    return result.toString();
  }
}
