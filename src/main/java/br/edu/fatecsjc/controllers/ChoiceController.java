package br.edu.fatecsjc.controllers;

import br.edu.fatecsjc.models.Choice;
import br.edu.fatecsjc.services.impl.ChoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("ChoiceController")
@RequestMapping(value = "/choices")
public class ChoiceController {

    @Autowired
    private ChoiceServiceImpl choiceService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Choice> findChoiceById(@PathVariable Integer id) {

        Choice choice = choiceService.findById(id);

        return ResponseEntity.ok().body(choice);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Choice>> findAllChoices() {

        Iterable<Choice> choices = choiceService.findChoices();

        return ResponseEntity.ok().body(choices);
    }
}