/** Controller REST pentru accesarea datelor prin JSON (pentru Postman/Mobile)
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.controller;

import com.bugetstudent.model.Tranzactie;
import com.bugetstudent.service.TranzactieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Spunem Spring ca aici returnam DATE (JSON), nu HTML
@RequestMapping("/api") // Toate linkurile vor incepe cu /api
public class TranzactieRestController {

    @Autowired
    private TranzactieService tranzactieService;

    // 1. GET: Returneaza lista de tranzactii a unui student (format JSON)
    // Link: http://localhost:8080/api/tranzactii?studentId=1
    @GetMapping("/tranzactii")
    public List<Tranzactie> getTranzactiiJson(@RequestParam Integer studentId) {
        // Folosim null la suma minima ca sa le luam pe toate
        return tranzactieService.getTranzactii(studentId, null);
    }

    // 2. POST: Adauga o tranzactie prin API (pentru Postman)
    // Link: http://localhost:8080/api/tranzactii
    @PostMapping("/tranzactii")
    public String addTranzactieJson(@RequestParam String descriere,
                                    @RequestParam Double suma,
                                    @RequestParam Integer idCont,
                                    @RequestParam Integer idCategorie) {

        tranzactieService.addTranzactie(descriere, suma, idCont, idCategorie);
        return "Tranzactie adaugata cu succes prin API!";
    }
}