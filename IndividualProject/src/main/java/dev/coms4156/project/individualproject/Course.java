package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/** Represents a course. */
public class Course implements Serializable {

  @Serial private static final long serialVersionUID = 123456L;

  /** The maximum number of students that can enroll in the course. */
  private final int enrollCapacity;

  /**
   * The current number of students enrolled in the course. -- SETTER -- Set the currently enrolled
   * student count.
   *
   * @param count Currently enrolled student count.
   */
  @Setter private int enrolledStudentCount;

  /** The location where the course is held. */
  @Getter private String courseLocation;

  /** The name of the instructor teaching the course. */
  @Getter private String instructorName;

  /** The time slot when the course is conducted. */
  @Getter private String courseTimeSlot;

  /**
   * Creates a Course with specified parameters. Enrollment starts at 0.
   *
   * @param instructorName The name of the course instructor.
   * @param courseLocation The location of the course.
   * @param timeSlot The time slot of the course.
   * @param capacity The maximum number of students for the course.
   */
  public Course(String instructorName, String courseLocation, String timeSlot, int capacity) {
    this.courseLocation = courseLocation;
    this.instructorName = instructorName;
    this.courseTimeSlot = timeSlot;
    this.enrollCapacity = capacity;
    this.enrolledStudentCount = 0;
  }

  /**
   * Enrolls a student in the course if there is space available.
   *
   * @return true if the student is successfully enrolled, false otherwise.
   */
  public boolean enrollStudent() {
    boolean result = false;
    if (!isCourseFull()) {
      enrolledStudentCount++;
      result = true;
    }
    return result;
  }

  /**
   * Drops a student from the course if a student is enrolled.
   *
   * @return true if the student is successfully dropped, false otherwise.
   */
  public boolean dropStudent() {
    boolean result = false;
    if (this.enrolledStudentCount > 0) {
      enrolledStudentCount--;
      result = true;
    }
    return result;
  }

  @Override
  public String toString() {
    return "\nInstructor: "
        + instructorName
        + "; Location: "
        + courseLocation
        + "; Time: "
        + courseTimeSlot;
  }

  /**
   * Change the instructor of the course.
   *
   * @param newInstructorName The name of new instructor of the course.
   */
  public void reassignInstructor(String newInstructorName) {
    this.instructorName = newInstructorName;
  }

  /**
   * Change the location of the course.
   *
   * @param newLocation The new location of the course.
   */
  public void reassignLocation(String newLocation) {
    this.courseLocation = newLocation;
  }

  /**
   * Change the time of the course.
   *
   * @param newTime The new time slot of the course.
   */
  public void reassignTime(String newTime) {
    this.courseTimeSlot = newTime;
  }

  /**
   * Determine if the course is full or not.
   *
   * @return True if the course is full, false otherwise.
   */
  public boolean isCourseFull() {
    return enrollCapacity <= enrolledStudentCount;
  }
}
