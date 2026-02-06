/** Clasa Service pentru logica legata de Studenti (Login)
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.service;

import com.bugetstudent.model.Student;
import com.bugetstudent.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service // Spunem lui Spring ca aceasta este o clasa de logica
public class StudentService {

    @Autowired // Injectam Repository-ul creat anterior
    private StudentRepository studentRepository;

    // Metoda de autentificare simpla
    public Student login(String email, String parola) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            // Verificam parola
            if (student.getParola().equals(parola)) {
                return student;
            }
        }
        return null; // Login esuat
    }
}