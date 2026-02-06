/** Repository pentru interogari SQL Native adaptate EXACT la baza ta de date
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.repository;

import com.bugetstudent.model.Tranzactie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaportRepository extends JpaRepository<Tranzactie, Integer> {

    // ==========================================
    // 6 INTEROGARI SIMPLE (JOIN, GROUP BY)
    // Folosim numele exacte: Nume_Categorie, Nume_Cont, Balanta_Initiala, etc.
    // ==========================================

    // 1. Raport Detaliat (JOIN 3 Tabele)
    @Query(value = "SELECT t.Descriere, t.Suma, t.Data, c.Nume_Cont, cat.Nume_Categorie " +
            "FROM Tranzactii t " +
            "JOIN Conturi c ON t.ID_Cont = c.ID_Cont " +
            "JOIN Categorii cat ON t.ID_Categorie = cat.ID_Categorie " +
            "WHERE c.ID_Student = :studentId ORDER BY t.Data DESC", nativeQuery = true)
    List<Object[]> getRaportDetaliat(@Param("studentId") Integer studentId);

    // 2. Suma totală cheltuită pe fiecare categorie
    @Query(value = "SELECT cat.Nume_Categorie, SUM(t.Suma) " +
            "FROM Tranzactii t " +
            "JOIN Categorii cat ON t.ID_Categorie = cat.ID_Categorie " +
            "JOIN Conturi c ON t.ID_Cont = c.ID_Cont " +
            "WHERE c.ID_Student = :studentId AND t.Suma < 0 " +
            "GROUP BY cat.Nume_Categorie", nativeQuery = true)
    List<Object[]> getSumaPerCategorie(@Param("studentId") Integer studentId);

    // 3. Top 3 cele mai mari cheltuieli
    @Query(value = "SELECT TOP 3 t.Descriere, t.Suma " +
            "FROM Tranzactii t " +
            "JOIN Conturi c ON t.ID_Cont = c.ID_Cont " +
            "WHERE c.ID_Student = :studentId AND t.Suma < 0 " +
            "ORDER BY t.Suma ASC", nativeQuery = true)
    List<Object[]> getTop3Cheltuieli(@Param("studentId") Integer studentId);

    // 4. Balanța Generală Venituri vs Cheltuieli
    @Query(value = "SELECT " +
            "SUM(CASE WHEN t.Suma > 0 THEN t.Suma ELSE 0 END) as Venituri, " +
            "SUM(CASE WHEN t.Suma < 0 THEN t.Suma ELSE 0 END) as Cheltuieli " +
            "FROM Tranzactii t JOIN Conturi c ON t.ID_Cont = c.ID_Cont " +
            "WHERE c.ID_Student = :studentId", nativeQuery = true)
    List<Object[]> getBalantaGenerala(@Param("studentId") Integer studentId);

    // 5. Numarul de tranzactii per Cont
    @Query(value = "SELECT c.Nume_Cont, COUNT(t.ID_Tranzactie) " +
            "FROM Conturi c LEFT JOIN Tranzactii t ON c.ID_Cont = t.ID_Cont " +
            "WHERE c.ID_Student = :studentId " +
            "GROUP BY c.Nume_Cont", nativeQuery = true)
    List<Object[]> getNrTranzactiiPerCont(@Param("studentId") Integer studentId);

    // 6. Tranzacțiile din luna curentă
    @Query(value = "SELECT t.Descriere, t.Suma " +
            "FROM Tranzactii t JOIN Conturi c ON t.ID_Cont = c.ID_Cont " +
            "WHERE c.ID_Student = :studentId AND MONTH(t.Data) = MONTH(GETDATE()) AND YEAR(t.Data) = YEAR(GETDATE())", nativeQuery = true)
    List<Object[]> getTranzactiiLunaCurenta(@Param("studentId") Integer studentId);

    //  Media Cheltuielilor (Folosind AVG si JOIN)
    @Query(value = "SELECT AVG(t.Suma) FROM Tranzactii t " +
            "JOIN Conturi c ON t.ID_Cont = c.ID_Cont " +
            "WHERE c.ID_Student = :studentId AND t.Suma < 0", nativeQuery = true)
    Double getMediaCheltuielilor(@Param("studentId") Integer studentId);


    // ==========================================
    // 4 INTEROGARI COMPLEXE (SUBCERERI)
    // ==========================================

    // 7. Tranzacții care depășesc media cheltuielilor
    @Query(value = "SELECT * FROM Tranzactii " +
            "WHERE Suma < (SELECT AVG(Suma) FROM Tranzactii WHERE Suma < 0) " +
            "AND ID_Cont IN (SELECT ID_Cont FROM Conturi WHERE ID_Student = :studentId)", nativeQuery = true)
    List<Tranzactie> getCheltuieliPesteMedie(@Param("studentId") Integer studentId);

    // 8. Conturi care nu au fost folosite deloc (NOT IN)
    @Query(value = "SELECT ID_Cont, Nume_Cont FROM Conturi " +
            "WHERE ID_Cont NOT IN (SELECT DISTINCT ID_Cont FROM Tranzactii) " +
            "AND ID_Student = :studentId", nativeQuery = true)
    List<Object[]> getConturiInactive(@Param("studentId") Integer studentId);

    // 9. Categorii care sunt incluse în Bugete
    @Query(value = "SELECT * FROM Categorii " +
            "WHERE ID_Categorie IN (SELECT ID_Categorie FROM Bugete_Categorii)", nativeQuery = true)
    List<Object[]> getCategoriiBugetate();

    // 10. Cea mai mare tranzactie din cel mai bogat cont
    @Query(value = "SELECT TOP 1 * FROM Tranzactii " +
            "WHERE ID_Cont = (SELECT TOP 1 ID_Cont FROM Conturi WHERE ID_Student = :studentId ORDER BY Balanta_Initiala DESC) " +
            "ORDER BY ABS(Suma) DESC", nativeQuery = true)
    List<Tranzactie> getMaxTranzactieDinTopCont(@Param("studentId") Integer studentId);
}