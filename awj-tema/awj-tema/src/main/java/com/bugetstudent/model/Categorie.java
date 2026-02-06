/** Clasa pentru definirea entitatii Categorie (tipuri de cheltuieli/venituri)
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Categorii")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Categorie")
    private Integer id;

    @Column(name = "Nume_Categorie")
    private String nume;

    @Column(name = "Tip")
    private String tip;

    // Getteri și Setteri
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public String getTip() { return tip; }
    public void setTip(String tip) { this.tip = tip; }
}