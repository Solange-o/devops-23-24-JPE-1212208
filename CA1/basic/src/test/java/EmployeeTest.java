import com.greglturnquist.payroll.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee("John", "Doe", "Description", "Manager", 5);
    }

    @Test
    public void testValidateArguments_ValidArguments_ReturnsTrue() {
        assertTrue(employee.validateArguments("John", "Doe", "Description", "Manager", 5));
    }

    @Test
    public void testValidateArguments_NullFirstName_ReturnsFalse() {
        assertFalse(employee.validateArguments(null, "Doe", "Description", "Manager", 5));
    }

    @Test
    public void testEquals_SameEmployee_ReturnsTrue() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5);
        Employee employee2 = new Employee("John", "Doe", "Description", "Manager", 5);
        assertEquals(employee1, employee2);
    }

    @Test
    public void testEquals_DifferentEmployees_ReturnsFalse() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5);
        Employee employee2 = new Employee("Jane", "Smith", "Description", "Manager", 5);
        assertNotEquals(employee1, employee2);
    }

    @Test
    public void testHashCode_SameEmployee_ReturnsSameHashCode() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5);
        Employee employee2 = new Employee("John", "Doe", "Description", "Manager", 5);
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    public void testHashCode_DifferentEmployees_ReturnsDifferentHashCodes() {
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5);
        Employee employee2 = new Employee("Jane", "Smith", "Description", "Manager", 5);
        assertNotEquals(employee1.hashCode(), employee2.hashCode());
    }
}
