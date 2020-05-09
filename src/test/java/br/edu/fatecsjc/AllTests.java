package br.edu.fatecsjc;

import br.edu.fatecsjc.integration.repositories.AccountRepositoryTest;
import br.edu.fatecsjc.unit.models.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AccountTest.class, AdministratorTest.class, ActivityTest.class, ExamTest.class, QuestionTest.class, AnswerTest.class, AccountRepositoryTest.class})
public class AllTests {


}
