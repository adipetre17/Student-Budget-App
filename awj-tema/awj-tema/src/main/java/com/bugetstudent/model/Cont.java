/** Clasa pentru definirea entitatii Cont (conturi bancare sau cash)
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Conturi")
public class Cont {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cont")
    private Integer id;

    @Column(name = "Nume_Cont")
    private String nume;

    @Column(name = "Balanta_Initiala")
    private Double balanta;

    @ManyToOne
    @JoinColumn(name = "ID_Student")
    private Student student;

    // Getteri și Setteri
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public Double getBalanta() { return balanta; }
    public void setBalanta(Double balanta) { this.balanta = balanta; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}