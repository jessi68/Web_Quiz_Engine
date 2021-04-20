package engine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.stereotype.Component;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class Quiz {

    @JsonIgnore
    private static int numOfQuiz = 0;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Text should not be blank")
    private String text;

    @NotNull(message = "Options should not be null")
    @NotEmpty(message = "Options should not be mepty")
    @Size(min = 2, message = "Must have at least 2 options!")
    @ElementCollection
    private String options[];

    @JsonIgnore
    @NotEmpty
    private List<Integer> answer;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnore
    private User user;

    public Quiz(String title, String text, String[] options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.id = ++numOfQuiz;
        this.answer = answer;
    }

    @JsonIgnore
    public List<Integer> getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }


    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String toMessage() {

        return "{\n" + "\"id\": \"{id}\",\n" +
                "  \"title\": \"The Java Logo\",\n" +
                "  \"text\": \"What is depicted on the Java logo?\",\n" +
                "  \"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]\n" +
                "}";
    }

}
