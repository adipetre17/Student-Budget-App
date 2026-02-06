/** Interfata Repository pentru entitatea Cont
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.repository;

import com.bugetstudent.model.Cont;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContRepository extends JpaRepository<Cont, Integer> {

    // Spring face JOIN automat: SELECT * FROM Conturi WHERE ID_Student = ?
    List<Cont> findByStudentId(Integer studentId);
}