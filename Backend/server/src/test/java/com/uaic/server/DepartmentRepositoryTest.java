package com.uaic.server;

import com.uaic.server.aspects.DepartmentAspect;
import com.uaic.server.entities.Department;
import com.uaic.server.repositories.DepartmentRepository;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author G
 */
@ExtendWith(SpringExtension.class)
@Import(DepartmentAspect.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use H2 in-memory database
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    private Department testDepartment;
    
    @SpyBean  // Spy on the aspect to capture its behavior
    private DepartmentAspect departmentAspect;
    
     @BeforeEach
    public void setUp() {
        testDepartment = new Department();
        testDepartment.setName("HR");
        testDepartment.setLocation("Strada Panselutelor");
       
        // Save the test employee to the database
        departmentRepository.save(testDepartment);
    }

    @Test
    public void testSave() {
        Department newDepartment = new Department();
        newDepartment.setName("HR");
        newDepartment.setLocation("Strada Panselutelor");

        Department savedDepartment = departmentRepository.save(newDepartment);
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getName()).isEqualTo("HR");
    }

    @Test
    public void testFindById() {
        Optional<Department> foundDepartment = departmentRepository.findById(testDepartment.getId());
        assertThat(foundDepartment).isPresent();
        assertThat(foundDepartment.get().getLocation()).isEqualTo(testDepartment.getLocation());
    }
    
     @Test
    public void testFindByLocation() {
        Optional<Department> foundDepartment = departmentRepository.findByLocation("Strada Panselutelor");
        assertThat(foundDepartment).isPresent();
        assertThat(foundDepartment.get().getName()).isEqualTo("HR");
    }
    @Test
    public void testDelete() {
        departmentRepository.delete(testDepartment);
        Optional<Department> foundDepartment = departmentRepository.findById(testDepartment.getId());
        assertThat(foundDepartment).isNotPresent();
    }

    @Test
    public void testCount() {
        long count = departmentRepository.count();
        assertThat(count).isEqualTo(1); // Assuming we only have one department in setup
    }
}
