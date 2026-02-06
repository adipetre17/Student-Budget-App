/** Clasa Controller pentru autentificare si gestionarea sesiunii
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.controller;

import com.bugetstudent.model.Student;
import com.bugetstudent.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    // 1. Afisare pagina de Login (index)
    @GetMapping("/")
    public String showLoginPage() {
        return "index"; // Va cauta index.html in resources/templates
    }

    // 2. Procesare Login (POST)
    @PostMapping("/login")
    public String handleLogin(@RequestParam(required = false) String email,
                              @RequestParam(required = false) String password,
                              HttpSession session,
                              Model model) {

        // 1. Verificăm dacă utilizatorul a trimis datele
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "Te rog completează ambele câmpuri!");
            return "index"; // Îl trimitem înapoi la login
        }

        // 2. Încercăm autentificarea
        Student student = studentService.login(email, password);

        if (student != null) {
            // Login reușit -> Salvăm în sesiune
            session.setAttribute("user_id", student.getId());
            session.setAttribute("user_name", student.getNume());
            return "redirect:/home";
        } else {
            // Login eșuat -> Eroare vizuală
            model.addAttribute("error", "Email incorect sau parolă incorectă!");
            return "index";
        }
    }

    // 3. Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Stergem sesiunea
        return "redirect:/";
    }
}