/** Interfata Repository pentru entitatea Tranzactie
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.repository;

import com.bugetstudent.model.Tranzactie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TranzactieRepository extends JpaRepository<Tranzactie, Integer> {

    // 1. Găsește toate tranzacțiile unui student, ordonate descrescător după dată
    List<Tranzactie> findByContStudentIdOrderByDataDesc(Integer studentId);

    // 2. Filtrare după sumă (pentru căutarea "Tranzacții mai mari de...")
    List<Tranzactie> findByContStudentIdAndSumaGreaterThanEqualOrderByDataDesc(Integer studentId, Double minSuma);
    // Interogare complexa (Agregare): Suma tranzactiilor grupate pe categorii
    // Selectam doar categoriile unde s-au cheltuit bani (suma < 0)
    @Query("SELECT t.categorie.nume, SUM(t.suma) FROM Tranzactie t WHERE t.cont.student.id = :userId AND t.suma < 0 GROUP BY t.categorie.nume")
    List<Object[]> findCheltuieliPerCategorie(Integer userId);
}