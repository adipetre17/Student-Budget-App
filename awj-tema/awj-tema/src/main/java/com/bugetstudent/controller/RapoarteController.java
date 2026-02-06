/** Clasa Controller pentru afisarea paginii de Rapoarte si Statistici SQL
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */

package com.bugetstudent.controller;

import com.bugetstudent.repository.RaportRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RapoarteController {

    @Autowired
    private RaportRepository raportRepository;

    @GetMapping("/rapoarte")
    public String showRapoarte(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) return "redirect:/";

        model.addAttribute("listaDetaliata", raportRepository.getRaportDetaliat(userId));
        model.addAttribute("sumaCategorii", raportRepository.getSumaPerCategorie(userId));
        model.addAttribute("topCheltuieli", raportRepository.getTop3Cheltuieli(userId));
        model.addAttribute("balantaGen", raportRepository.getBalantaGenerala(userId));
        model.addAttribute("nrTranz", raportRepository.getNrTranzactiiPerCont(userId));

        model.addAttribute("tranzLuna", raportRepository.getTranzactiiLunaCurenta(userId)); // Era deja, dar nefolosit
        model.addAttribute("mediaCheltuieli", raportRepository.getMediaCheltuielilor(userId)); // Nou

        //interogarile complexe
        model.addAttribute("pesteMedie", raportRepository.getCheltuieliPesteMedie(userId));
        model.addAttribute("conturiInactive", raportRepository.getConturiInactive(userId));
        model.addAttribute("categoriiBuget", raportRepository.getCategoriiBugetate());
        model.addAttribute("maxTranz", raportRepository.getMaxTranzactieDinTopCont(userId));

        return "rapoarte";
    }
}