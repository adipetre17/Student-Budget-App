/** Clasa pentru definirea entitatii Buget
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */

package com.bugetstudent.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Bugete")
public class Buget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nume; // Exemplu: "Buget Vacanta"

    private Double sumaLimita; // Exemplu: 2000 RON

    // RELATIA N:N (Many-to-Many) - Cerinta obligatorie BD
    @ManyToMany
    @JoinTable(
            name = "Bugete_Categorii",
            joinColumns = @JoinColumn(name = "buget_id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private List<Categorie> categorii;

    // Getters si Setters standard
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public Double getSumaLimita() { return sumaLimita; }
    public void setSumaLimita(Double sumaLimita) { this.sumaLimita = sumaLimita; }
    public List<Categorie> getCategorii() { return categorii; }
    public void setCategorii(List<Categorie> categorii) { this.categorii = categorii; }
}