package br.edu.fatecsjc.services.impl;

import br.edu.fatecsjc.models.Exam;
import br.edu.fatecsjc.repositories.ExamRepository;
import br.edu.fatecsjc.services.ExamService;
import br.edu.fatecsjc.services.exceptions.DataIntegrityException;
import br.edu.fatecsjc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Override
    public Exam findById(Integer id) {

        Exam exam = examRepository.findById(id).orElse(null);

        if (exam == null)
            throw new ObjectNotFoundException("Exame não encontrado. Id: " + id + ", Tipo: " + Exam.class.getName());

        return exam;
    }

    @Override
    public Exam saveExam(Exam exam) {

        Exam obj = examRepository.findByExamTitle(exam.getExamTitle());

        if (obj == null) {
            exam.setId(null);
            exam.setExamTitle(exam.getExamTitle());
            exam.setType(exam.getType());
            exam.setDescription(exam.getDescription());
            exam.setAuthor(exam.getAuthor());

            return examRepository.save(exam);
        } else
            throw new DataIntegrityException("Já existe um exame com este título.");
    }

    @Override
    public void deleteExamById(Integer id) {

        findById(id);

        try {
            examRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir o exam informado pois existem questões.");
        }
    }

    @Override
    public List<Exam> findExams() {

        return examRepository.findAll();
    }
}
