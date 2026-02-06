/** Clasa pentru definirea entitatii Tranzactie (elementul principal al colectiei)
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tranzactii")
public class Tranzactie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Tranzactie")
    private Integer id;

    @Column(name = "Suma")
    private Double suma;

    @Column(name = "Data")
    private LocalDateTime data;

    @Column(name = "Descriere")
    private String descriere;

    @ManyToOne
    @JoinColumn(name = "ID_Cont")
    private Cont cont;

    @ManyToOne
    @JoinColumn(name = "ID_Categorie")
    private Categorie categorie;

    // Getteri și Setteri
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Double getSuma() { return suma; }
    public void setSuma(Double suma) { this.suma = suma; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public Cont getCont() { return cont; }
    public void setCont(Cont cont) { this.cont = cont; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}