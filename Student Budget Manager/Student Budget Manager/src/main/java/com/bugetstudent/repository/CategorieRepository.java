/** Interfata Repository pentru entitatea Categorie
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.repository;

import com.bugetstudent.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
    // Nu avem nevoie de metode speciale momentan
}