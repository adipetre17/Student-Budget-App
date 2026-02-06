/** Clasa Controller principala pentru Tranzactii si Conturi
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.controller;

import com.bugetstudent.model.Cont;      // <--- IMPORT NOU
import com.bugetstudent.service.ContService;
import com.bugetstudent.service.TranzactieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;                   // <--- IMPORT NOU

@Controller
public class MainController {

    @Autowired
    private TranzactieService tranzactieService;

    @Autowired
    private ContService contService;

    // ================== ZONA DASHBOARD (ACASA) ==================
    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) return "redirect:/";

        // 1. Balanta Totala
        List<Cont> conturi = contService.getConturiByStudent(userId);
        double totalBalanta = 0.0;
        for (Cont c : conturi) {
            totalBalanta += c.getBalanta();
        }
        model.addAttribute("totalBalanta", totalBalanta);

        // 2. STATISTICI NOI
        // Lista de obiecte [NumeCategorie, SumaTotala]
        List<Object[]> statistici = tranzactieService.getStatisticiCategorii(userId);
        model.addAttribute("statistici", statistici);

        return "home";
    }

    // ================== ZONA TRANZACTII ==================

    @GetMapping("/tranzactii")
    public String showTranzactii(@RequestParam(required = false) Double minSuma,
                                 HttpSession session,
                                 Model model) {
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) return "redirect:/";

        model.addAttribute("tranzactiiList", tranzactieService.getTranzactii(userId, minSuma));
        model.addAttribute("conturiList", contService.getConturiByStudent(userId));
        model.addAttribute("categoriiList", tranzactieService.getAllCategorii());
        model.addAttribute("filtruCurent", minSuma);

        return "tranzactii";
    }

    @PostMapping("/tranzactii")
    public String handleTranzactieAction(
            @RequestParam String action,
            @RequestParam(required = false) Integer id_tranzactie,
            @RequestParam(required = false) String descriere,
            @RequestParam(required = false) Double suma,
            @RequestParam(required = false) Integer id_cont,
            @RequestParam(required = false) Integer id_categorie,
            @RequestParam(required = false) String descriere_noua,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) return "redirect:/";

        if ("add".equals(action)) {
            tranzactieService.addTranzactie(descriere, suma, id_cont, id_categorie);
        } else if ("update".equals(action)) {
            tranzactieService.updateDescriere(id_tranzactie, descriere_noua);
        } else if ("delete".equals(action)) {
            tranzactieService.deleteTranzactie(id_tranzactie);
        }

        return "redirect:/tranzactii";
    }

    // ================== ZONA CONTURI ==================

    @GetMapping("/conturi")
    public String showConturi(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) return "redirect:/";

        model.addAttribute("listaConturi", contService.getConturiByStudent(userId));
        return "conturi";
    }

    @PostMapping("/conturi")
    public String handleContAction(
            @RequestParam String action,
            @RequestParam(required = false) Integer id_cont,
            @RequestParam(required = false) String nume_cont,
            @RequestParam(required = false) String nume_nou,
            @RequestParam(required = false) Double balanta,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) return "redirect:/";

        if ("add".equals(action)) {
            contService.addCont(userId, nume_cont, balanta);
        } else if ("update".equals(action)) {
            contService.updateCont(id_cont, nume_nou);
        } else if ("delete".equals(action)) {
            contService.deleteCont(id_cont);
        }

        return "redirect:/conturi";
    }
}