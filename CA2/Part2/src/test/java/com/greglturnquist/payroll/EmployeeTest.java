package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void validCreateEmployee() throws InstantiationException {

        // Arrange
        String firstName = "name";
        String lastName = "lastName";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "name@mail.com";

        // Act
        Employee employee = null;
        try {
            employee = new Employee(firstName, lastName, description, jobTitle, jobYears, email);
        } catch (InstantiationException e) {
            fail("InstantiationException should not be thrown for valid input");
        }

        // Assert
        assertNotNull(employee);
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
        assertEquals(description, employee.getDescription());
        assertEquals(jobTitle, employee.getJobTitle());
        assertEquals(jobYears, employee.getJobYears());
        assertEquals(email, employee.getEmail());
    }

    @Test
    void emptyEmployeeFirstName(){

        // Arrange
        String firstName = "";
        String lastName = "lastName";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "name@mail.com";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, email));
    }

    @Test
    void emptyEmployeeLastName(){

        // Arrange
        String firstName = "firstName";
        String lastName = "";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "name@mail.com";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, email));
    }

    @Test
    void emptyEmployeeDescription(){

        // Arrange
        String firstName = "firstName";
        String lastName = "lastName";
        String description = "";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "name@mail.com";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, email));
    }

    @Test
    void emptyEmployeeEmail(){

        // Arrange
        String firstName = "firstName";
        String lastName = "lastName";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, email));
    }

    @Test
    void invalidEmployeeEmail(){

        // Arrange
        String firstName = "firstName";
        String lastName = "lastName";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "email";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, email));
    }

    @Test
    void negativeEmployeeJobYears(){

        // Arrange
        String firstName = "firstName";
        String lastName = "lastName";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = -5;
        String email = "name@mail.com";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, email));
    }


    @Test
    void nullEmployeeFirstName(){

        // Arrange
        String firstName = null;
        String lastName = "lastName";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "name@mail.com";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, email));
    }

    @Test
    void nullEmployeeLastName(){

        // Arrange
        String firstName = "firstName";
        String lastName = null;
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "name@mail.com";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, null, description, jobTitle, jobYears, email));
    }

    @Test
    void nullEmployeeDescription(){

        // Arrange
        String firstName = "firstName";
        String lastName = "lastName";
        String description = null;
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = "name@mail.com";

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, null, jobTitle, jobYears, email));
    }

    @Test
    void nullEmployeeEmail(){

        // Arrange
        String firstName = "firstName";
        String lastName = "lastName";
        String description = "description";
        String jobTitle = "jobTitle";
        int jobYears = 5;
        String email = null;

        // Act and Assert
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, null));
    }

    @Test
    void equalObjectsShouldReturnTrue() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Description";
        String jobTitle = "Manager";
        int jobYears = 5;
        String email = "john@example.com";
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
        Employee employee2 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");

        // Act & Assert
        assertTrue(employee1.equals(employee2));
        assertTrue(employee2.equals(employee1));
    }

    @Test
    void differentObjectsShouldReturnFalse() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Description";
        String jobTitle = "Manager";
        int jobYears = 5;
        String email = "john@example.com";
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
        Employee employee2 = new Employee("Jane", "Doe", "Description", "Manager", 5, "jane@example.com");

        // Act & Assert
        assertFalse(employee1.equals(employee2));
        assertFalse(employee2.equals(employee1));
    }

    @Test
    void nullObjectShouldReturnFalse() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Description";
        String jobTitle = "Manager";
        int jobYears = 5;
        String email = "john@example.com";
        Employee employee = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");

        // Act & Assert
        assertFalse(employee.equals(null));
    }

    @Test
    void differentClassObjectShouldReturnFalse() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Description";
        String jobTitle = "Manager";
        int jobYears = 5;
        String email = "john@example.com";
        Employee employee = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");

        // Act & Assert
        assertFalse(employee.equals("Not an Employee"));
    }

    @Test
    void hashCodeConsistency() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Description";
        String jobTitle = "Manager";
        int jobYears = 5;
        String email = "john@example.com";
        Employee employee1 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");
        Employee employee2 = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");

        // Act & Assert
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void getId() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Description";
        String jobTitle = "Manager";
        int jobYears = 5;
        String email = "john@example.com";
        Long id = 123L;
        Employee employee = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");

        // Act
        employee.setId(id);

        // Assert
        assertEquals(id, employee.getId());
    }

    @Test
    void setId() throws InstantiationException {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String description = "Description";
        String jobTitle = "Manager";
        int jobYears = 5;
        String email = "john@example.com";
        Long id = 123L;
        Employee employee = new Employee("John", "Doe", "Description", "Manager", 5, "john@example.com");

        // Act
        employee.setId(id);

        // Assert
        assertEquals(id, employee.getId());

    }

}
