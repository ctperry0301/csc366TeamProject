package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.sql.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
  Employee findByFirstName(String firstName);

  List<Employee> findByStartDate(Date startDate);

  // JPQL query with join
  @Query("select e from Employee e join e.paychecks paychecks where e.firstName = :name or e.lastName = :name")
  Employee findByNameWithPaychecksJpql(@Param("name") String name);







}
