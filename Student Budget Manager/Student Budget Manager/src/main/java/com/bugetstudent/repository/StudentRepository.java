/** Interfata Repository pentru entitatea Student (gestioneaza accesul la DB)
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.repository;

import com.bugetstudent.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Spring genereaza automat: SELECT * FROM Studenti WHERE Email = ?
    Optional<Student> findByEmail(String email);
}