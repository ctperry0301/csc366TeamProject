package csc366.jpademo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long>{

    @Query("from Shift s where s.worked = FALSE")
    List<Shift> findUnworkedShifts();

    @Modifying
    @Query("update Shift s set s.worked = TRUE where s.shiftId = :shiftId")
    void markShiftAsWorked(@Param("shiftId") Long shiftId);

    Shift findByWorker(Employee worker);

    Shift findByManager(LocationManager manager);

    Shift findByShiftId(Long shiftId);
}
