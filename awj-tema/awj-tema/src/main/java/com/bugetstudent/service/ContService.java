/** Clasa Service pentru logica legata de Conturi
 * @author Petre Adrian
 * @version 12 Ianuarie 2026
 */
package com.bugetstudent.service;

import com.bugetstudent.model.Cont;
import com.bugetstudent.model.Student;
import com.bugetstudent.repository.ContRepository;
import com.bugetstudent.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContService {

    @Autowired
    private ContRepository contRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Listare conturi pentru un student
    public List<Cont> getConturiByStudent(Integer studentId) {
        return contRepository.findByStudentId(studentId);
    }

    // Salvare cont nou
    public void addCont(Integer studentId, String nume, Double balanta) {
        Student s = studentRepository.findById(studentId).orElse(null);
        if (s != null) {
            Cont c = new Cont();
            c.setStudent(s);
            c.setNume(nume);
            c.setBalanta(balanta);
            contRepository.save(c);
        }
    }

    // Actualizare nume cont
    public void updateCont(Integer contId, String numeNou) {
        Cont c = contRepository.findById(contId).orElse(null);
        if (c != null) {
            c.setNume(numeNou);
            contRepository.save(c);
        }
    }

    // Stergere cont
    public void deleteCont(Integer contId) {
        contRepository.deleteById(contId);
    }
}