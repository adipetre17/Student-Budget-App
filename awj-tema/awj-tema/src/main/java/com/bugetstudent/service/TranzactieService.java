/** Clasa Service pentru logica principala de Tranzactii
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.service;

import com.bugetstudent.model.Categorie;
import com.bugetstudent.model.Cont;
import com.bugetstudent.model.Tranzactie;
import com.bugetstudent.repository.CategorieRepository;
import com.bugetstudent.repository.ContRepository;
import com.bugetstudent.repository.TranzactieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TranzactieService {

    @Autowired
    private TranzactieRepository tranzactieRepository;

    @Autowired
    private ContRepository contRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    // 1. Obtine toate tranzactiile unui student
    public List<Tranzactie> getTranzactii(Integer studentId, Double minSuma) {
        if (minSuma != null) {
            return tranzactieRepository.findByContStudentIdAndSumaGreaterThanEqualOrderByDataDesc(studentId, minSuma);
        } else {
            return tranzactieRepository.findByContStudentIdOrderByDataDesc(studentId);
        }
    }

    // 2. Adauga o tranzactie noua SI ACTUALIZEAZA BALANTA
    public void addTranzactie(String descriere, Double suma, Integer idCont, Integer idCategorie) {
        Cont cont = contRepository.findById(idCont).orElse(null);
        Categorie cat = categorieRepository.findById(idCategorie).orElse(null);

        if (cont != null && cat != null) {
            // A. Cream tranzactia
            Tranzactie t = new Tranzactie();
            t.setDescriere(descriere);
            t.setSuma(suma);
            t.setData(LocalDateTime.now());
            t.setCont(cont);
            t.setCategorie(cat);
            tranzactieRepository.save(t);

            // B. Actualizam Balanta Contului
            // Daca suma e -50, se va scadea. Daca e +100, se va aduna.
            Double balantaVeche = cont.getBalanta();
            Double balantaNoua = balantaVeche + suma;

            cont.setBalanta(balantaNoua);
            contRepository.save(cont); // Salvam contul actualizat in DB
        }
    }

    // 3. Modifica descrierea
    public void updateDescriere(Integer idTranzactie, String descriereNoua) {
        Tranzactie t = tranzactieRepository.findById(idTranzactie).orElse(null);
        if (t != null) {
            t.setDescriere(descriereNoua);
            tranzactieRepository.save(t);
        }
    }

    // 4. Sterge tranzactie SI REFACE BALANTA
    public void deleteTranzactie(Integer id) {
        Tranzactie t = tranzactieRepository.findById(id).orElse(null);

        if (t != null) {
            Cont c = t.getCont();

            // Refacem balanta (Scadem suma tranzactiei sterse)
            // Ex: Aveam 100 lei. Am sters o cheltuiala de -50 lei.
            // 100 - (-50) = 150 lei (Banii s-au intors).
            Double balantaNoua = c.getBalanta() - t.getSuma();
            c.setBalanta(balantaNoua);
            contRepository.save(c);

            // Stergem efectiv tranzactia
            tranzactieRepository.deleteById(id);
        }
    }

    // 5. Helper: Obtine categoriile
    public List<Categorie> getAllCategorii() {
        return categorieRepository.findAll();
    }
    // Metoda pentru statistici
    public List<Object[]> getStatisticiCategorii(Integer userId) {
        return tranzactieRepository.findCheltuieliPerCategorie(userId);
    }
}