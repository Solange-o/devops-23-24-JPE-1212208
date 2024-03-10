import com.greglturnquist.payroll.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;


public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
    }

    @Test
    public void testPrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Employee> constructor = Employee.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Employee employee = constructor.newInstance();
        assertNotNull(employee);
    }

    @Test
    void testValidateArguments_ValidArguments_ReturnsTrue() {
        assertTrue(employee.validateArguments("John", "Doe", "Description", "Manager", 5, "john@example.com"));
    }

    @Test
    void testValidateArguments_NullFirstName_ReturnsFalse() {
        assertFalse(employee.validateArguments(null, "Doe", "Description", "Manager", 5, "john@example.com"));
    }

    @Test
    void testValidateArguments_EmptyLastName_ReturnsFalse() {
        assertFalse(employee.validateArguments("John", "", "Description", "Manager", 5, "john@example.com"));
    }

    @Test
    void testValidateArguments_EmptyJobTitle_ReturnsFalse() {
        assertFalse(employee.validateArguments("John", "Doe", "Description", "", 5, "john@example.com"));
    }

    @Test
    void testValidateArguments_NegativeJobYears_ReturnsFalse() {
        assertFalse(employee.validateArguments("John", "Doe", "Description", "Manager", -1, "john@example.com"));
    }

    @Test
    void testValidateArguments_NullEmail_ReturnsFalse() {
        assertFalse(employee.validateArguments("John", "Doe", "Description", "Manager", 5, null));
    }

    @Test
    void testValidateArguments_EmptyEmail_ReturnsFalse() {
        assertFalse(employee.validateArguments("John", "Doe", "Description", "Manager", 5, ""));
    }

    @Test
    void testEquals_SameEmployee_ReturnsTrue() {
        Employee employee2 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
        assertTrue(employee.equals(employee2));
    }

    @Test
    void testEquals_DifferentEmployees_ReturnsFalse() {
        Employee employee2 = new Employee("Jane", "Smith", "Description", "Manager", 5, "jane@example.com");
        assertFalse(employee.equals(employee2));
    }

    @Test
    void testEquals_NullObject_ReturnsFalse() {
        assertFalse(employee == null);
    }

    @Test
    void testEquals_SameObject_ReturnsTrue() {
        assertTrue(employee.equals(employee));
    }

    @Test
    void testEquals_DifferentClass_ReturnsFalse() {
        assertFalse(employee.equals(new Object()));
    }

    @Test
    void testEquals_DifferentId_ReturnsFalse() {
        Employee employee2 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
        employee.setId(1L);
        employee2.setId(2L);
        assertFalse(employee.equals(employee2));
    }

    @Test
    void testHashCode_DifferentEmployees_ReturnsDifferentHashCodes() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
        Employee employee2 = new Employee("Jane", "Smith", "Description", "Manager", 5, "jane@example.com");
        assertNotEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testHashCode_SameEmployee_ReturnsSameHashCode() {
        Employee employee2 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
        assertEquals(employee.hashCode(), employee2.hashCode());
    }

    @Test
    void testHashCode_DifferentEmployee_ReturnsDifferentHashCodes() {
        Employee employee2 = new Employee("Jane", "Smith", "Description", "Manager", 5, "jane@example.com");
        assertNotEquals(employee.hashCode(), employee2.hashCode());
    }

    @Test
    void testHashCode_ChangeEmail_ReturnsDifferentHashCode() {
        int originalHashCode = employee.hashCode();
        employee.setEmail("mail");
        assertNotEquals(originalHashCode, employee.hashCode());
    }

    @Test
    void testGetFirstName() {
        assertEquals("John", employee.getFirstName());
    }

    @Test
    void testSetFirstName() {
        employee.setFirstName("Jane");
        assertEquals("Jane", employee.getFirstName());
    }

    @Test
    void testGetLastName() {
        assertEquals("Doe", employee.getLastName());
    }

    @Test
    void testSetLastName() {
        employee.setLastName("Smith");
        assertEquals("Smith", employee.getLastName());
    }

    @Test
    void testGetDescription() {
        assertEquals("Description", employee.getDescription());
    }

    @Test
    void testSetDescription() {
        employee.setDescription("New Description");
        assertEquals("New Description", employee.getDescription());
    }


    @Test
    void testSetAndGetId() {
        employee.setId(123L);
        assertEquals(123L, employee.getId());
    }

    @Test
    void testSetAndGetId_Null() {
        employee.setId(null);
        assertNull(employee.getId());
    }

    @Test
    void testSetAndGetDescription() {
        employee.setDescription("New Description");
        assertEquals("New Description", employee.getDescription());
    }

    @Test
    void testGetJobTitle() {
        assertEquals("Manager", employee.getJobTitle());
    }


    @Test
    void testSetJobTitle() {
        employee.setJobTitle("Senior Manager");
        assertEquals("Senior Manager", employee.getJobTitle());
    }

    @Test
    void testGetJobYears() {
        assertEquals(5, employee.getJobYears());
    }

    @Test
    void testSetJobYears() {
        employee.setJobYears(7);
        assertEquals(7, employee.getJobYears());
    }

    @Test
    void testGetEmail() {
        assertEquals("john@example.com", employee.getEmail());
    }

    @Test
    void testSetEmail() {
        employee.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", employee.getEmail());
    }

    @Test
    void testSetAndGetDescription_Null() {
        employee.setDescription(null);
        assertNull(employee.getDescription());
    }

}
