/** Clasa pentru definirea entitatii Student (mapeaza tabelul Studenti)
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Studenti")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Student")
    private Integer id;

    @Column(name = "Nume")
    private String nume;

    @Column(name = "Prenume")
    private String prenume;

    @Column(name = "Email")
    private String email;

    @Column(name = "Parola")
    private String parola;

    // Getteri și Setteri
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getParola() { return parola; }
    public void setParola(String parola) { this.parola = parola; }
}