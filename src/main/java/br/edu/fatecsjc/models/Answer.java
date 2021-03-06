package br.edu.fatecsjc.models;

import br.edu.fatecsjc.models.views.AnswerView;
import br.edu.fatecsjc.models.views.ExamView;
import br.edu.fatecsjc.models.views.QuestionView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answers")
public class Answer {

    @JsonView({AnswerView.AnswerSimple.class, ExamView.ExamComplete.class, QuestionView.QuestionComplete.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView({AnswerView.AnswerSimple.class, ExamView.ExamComplete.class, QuestionView.QuestionComplete.class})
    @NotNull
    private String answer;

    @JsonView({AnswerView.AnswerComplete.class, QuestionView.QuestionComplete.class})
    @NotNull
    private Boolean correct;

    @JsonView({AnswerView.AnswerComplete.class, ExamView.ExamComplete.class})
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    @NotNull
    private Question question;

    public boolean isValid(Answer answer) {
        return this.getAnswer().equals(answer.getAnswer());
    }

}
