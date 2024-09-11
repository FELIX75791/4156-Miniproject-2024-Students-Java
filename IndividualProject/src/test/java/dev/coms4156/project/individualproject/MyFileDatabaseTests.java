package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

/** Contains unit tests for MyFileDatabase. */
public class MyFileDatabaseTests {

  private static final String FILE_PATH = "./testdata.txt";
  private MyFileDatabase database;

  /** Clean up the test file before each test. */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    File file = new File(FILE_PATH);
    if (file.exists()) {
      file.delete();
    }
  }

  @Test
  public void testConstructorFlagZero() {
    // Mock a department mapping
    Map<String, Department> mockMapping = new HashMap<>();
    mockMapping.put("COMS", new Department("COMS", new HashMap<>(), "Chair", 0));

    // Serialize mockMapping to file
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
      out.writeObject(mockMapping);
    } catch (IOException e) {
      fail("Failed to serialize mock data to file.");
    }

    // Create database with flag 0
    database = new MyFileDatabase(0, FILE_PATH);
    Map<String, Department> resultMapping = database.getDepartmentMapping();

    assertNotNull(resultMapping);
    assertEquals(1, resultMapping.size());
    assertTrue(resultMapping.containsKey("COMS"));
  }

  @Test
  public void testConstructorFlagNonZero() {
    // Create database with flag not equal to 0
    database = new MyFileDatabase(1, FILE_PATH);
    assertNull(database.getDepartmentMapping());
  }

  @Test
  public void testSetMapping() {
    database = new MyFileDatabase(1, FILE_PATH);

    Map<String, Department> testMapping = new HashMap<>();
    testMapping.put("IEOR", new Department("IEOR", new HashMap<>(), "Jay Sethuraman", 67));

    database.setMapping(testMapping);

    assertEquals(testMapping, database.getDepartmentMapping());
  }

  @Test
  public void testSaveContentsToFile() throws IOException {
    database = new MyFileDatabase(1, FILE_PATH);

    Map<String, Department> testMapping = new HashMap<>();
    testMapping.put("CHEM", new Department("CHEM", new HashMap<>(), "Laura J. Kaufman", 200));

    database.setMapping(testMapping);
    database.saveContentsToFile();

    // Verify the file content
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
      Object obj = in.readObject();
      assertInstanceOf(Map.class, obj);
      Map<String, Department> fileMapping = (Map<String, Department>) obj;
      assertTrue(fileMapping.containsKey("CHEM"));
    } catch (ClassNotFoundException e) {
      fail("Class not found during deserialization.");
    }
  }

  @Test
  public void testToString() {
    database = new MyFileDatabase(1, FILE_PATH);

    Map<String, Department> testMapping = new HashMap<>();
    Department department = new Department("COMS", new HashMap<>(), "Luca Carloni", 2700);
    testMapping.put("COMS", department);
    database.setMapping(testMapping);

    String expectedString = "For the COMS department: \n" + department;
    assertEquals(expectedString, database.toString());
  }
}
