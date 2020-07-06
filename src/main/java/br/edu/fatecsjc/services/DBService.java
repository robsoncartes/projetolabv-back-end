package br.edu.fatecsjc.services;

import br.edu.fatecsjc.models.*;
import br.edu.fatecsjc.models.enums.AuthorityName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DBService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ChoiceService choiceService;

    public void instantiateTestDatabase() throws Exception {

        Administrator account1 = new Administrator(null, "email1@gmail.com", "admin1", "pass");
        Administrator account2 = new Administrator(null, "email2@gmail.com", "admin2", "pass");
        User account3 = new User(null, "email3@gmail.com", "user1", "pass");
        User account4 = new User(null, "email4@gmail.com", "user2", "pass");

        account1.addAuthorityName(AuthorityName.ADMINISTRATOR);
        account2.addAuthorityName(AuthorityName.ADMINISTRATOR);

        Exam exam1 = new Exam(null, "Exame A", "Tipo A", "Descrição A", account1.getUsername());
        Exam exam2 = new Exam(null, "Exame B", "Tipo B", "Descrição B", account1.getUsername());

        Question question1 = new Question(null, "Pergunta 1", exam1);
        Question question2 = new Question(null, "Pergunta 2", exam1);
        Question question3 = new Question(null, "Pergunta 3", exam2);

        Activity activity1 = new Activity(null, account3.getUsername(), exam1.getExamTitle());
        Activity activity2 = new Activity(null, account4.getUsername(), exam2.getExamTitle());

        Answer answer11 = new Answer(null, "True", true, question1);
        Answer answer12 = new Answer(null, "False", false, question1);
        Answer answer21 = new Answer(null, "Apple", true, question2);
        Answer answer22 = new Answer(null, "Wipro", false, question2);
        Answer answer31 = new Answer(null, "ADD", false, question3);
        Answer answer32 = new Answer(null, "INSERT", true, question3);

        Choice choice1 = new Choice(null, question1.getQuestionTitle(), answer11.getAnswer(), true, activity1);
        Choice choice2 = new Choice(null, question2.getQuestionTitle(), answer21.getAnswer(), true, activity1);
        Choice choice3 = new Choice(null, question3.getQuestionTitle(), answer32.getAnswer(), true, activity2);

        account3.addAtivity(activity1);
        account4.addAtivity(activity2);

        activity1.addAccount(account1);
        activity2.addAccount(account2);

        question1.addAnswer(answer11);
        question1.addAnswer(answer12);
        question2.addAnswer(answer21);
        question2.addAnswer(answer22);
        question3.addAnswer(answer31);
        question3.addAnswer(answer32);

        question1.setAssertion("True");
        question2.setAssertion("Apple");
        question3.setAssertion("INSERT");

        exam1.addQuestion(question1);
        exam1.addQuestion(question2);
        exam2.addQuestion(question3);

        exam1.setActivity(activity1);
        exam2.setActivity(activity2);

        List<Exam> exams = new ArrayList<>(Arrays.asList(exam1, exam2));
        // List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));
        // List<Answer> answers = new ArrayList<>(Arrays.asList(answer11, answer12, answer21, answer22, answer31, answer32));
        List<Choice> choices = new ArrayList<>(Arrays.asList(choice1, choice2, choice3));
        List<Activity> activities = new ArrayList<>(Arrays.asList(activity1, activity2));
        List<Account> accounts = new ArrayList<>(Arrays.asList(account1, account2, account3, account4));


        for (Account account : accounts) accountService.saveAccount(account);
        for (Activity activity : activities) activityService.saveActivity(activity);
        for (Exam exam : exams) examService.saveExam(exam);
        // for (Question question : questions) questionService.saveQuestion(question);

        questionService.saveQuestion(question1);
        questionService.saveQuestion(question2);
        questionService.saveQuestion(question3);

        // for (Answer answer : answers) answerService.saveAnswer(answer, question1);
        answerService.saveAnswer(answer11, question1);
        answerService.saveAnswer(answer12, question1);
        answerService.saveAnswer(answer21, question2);
        answerService.saveAnswer(answer22, question2);
        answerService.saveAnswer(answer31, question3);
        answerService.saveAnswer(answer32, question3);

        for (Choice choice : choices) choiceService.saveChoice(choice);
    }
}
