package engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import engine.dto.Quiz;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@Validated
public class WebQuizEngine {


    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }




}
