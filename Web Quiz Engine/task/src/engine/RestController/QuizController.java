package engine.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import engine.dto.Quiz;
import engine.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizController {
    List<Quiz> quizzes = new ArrayList<>();
    List<User> users = new ArrayList<>();
    ObjectWriter converter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        if(users.stream()
                .filter(u -> u.getEmail() == user.getEmail())
                .findFirst()
                .get() != null) {

        }
        users.add(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/quizzes", consumes = "application/json")
    public String createNewQuiz(@Valid @RequestBody Quiz quiz,
                                User user) throws JsonProcessingException {
        quiz.setUser(user);
        this.quizzes.add(quiz);
        String json = converter.writeValueAsString(quiz);
        return json;
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public String solveQuiz(@PathVariable Integer id, @RequestParam List<Integer> answer) throws JsonProcessingException{
        Quiz target = quizzes.stream()
                .filter(quiz -> quiz.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                ));

        if(target.getAnswer().equals(answer) ) {
            return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
        } else{
            return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
        }

    }

    @GetMapping(path = "/quizzes")
    public String getQuizzes(){
        StringBuilder jsons = new StringBuilder();
        boolean isEmpty = true;
        jsons.append("[");
        for(Quiz quiz: quizzes) {
            try {
                String json = converter.writeValueAsString(quiz);
                jsons.append(json);
            } catch(JsonProcessingException e) {
                e.printStackTrace();
            }
            jsons.append(",");
            isEmpty = false;
        }

        if(!isEmpty) {
            jsons.setLength(jsons.length() - 1);
        }
        jsons.append("]");
        //System.out.println("dddd  " + jsons.toString());

        //System.out.println(jsons.toString());
        return jsons.toString();
    }

    @GetMapping(path = "/quizzes/{id}")
    public String getQuiz(@PathVariable Integer id) throws JsonProcessingException {
        Quiz target = quizzes.stream()
                .filter(quiz -> quiz.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                ));
        //System.out.println(converter.writeValueAsString(target));
        return converter.writeValueAsString(target);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable long id,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal){

    }

}
