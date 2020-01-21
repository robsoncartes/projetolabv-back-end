package br.edu.fatecsjc.controllers;

import br.edu.fatecsjc.models.Exam;
import br.edu.fatecsjc.services.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("ExamController")
@RequestMapping(value = "/exams")
public class ExamController {

    @Autowired
    private ExamServiceImpl examService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Exam> findExamById(@PathVariable Integer id) {

        Exam exam = examService.findById(id);

        return ResponseEntity.ok().body(exam);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Exam>> findAllExams() {

        Iterable<Exam> exams = examService.findExams();

        return ResponseEntity.ok().body(exams);
    }
}