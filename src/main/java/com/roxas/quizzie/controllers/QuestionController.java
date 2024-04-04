package com.roxas.quizzie.controllers;

import com.roxas.quizzie.models.Question;
import com.roxas.quizzie.services.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;


@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("api/quizzes")
@RequiredArgsConstructor
public class QuestionController {
	
	private final IQuestionService questionService;
	
	@PostMapping("/create-question")
	public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
		Question createdQuestion = questionService.createQuestion(question);
		return ResponseEntity.status(CREATED).body(createdQuestion);
	}
	
	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		List<Question> allQuestions = questionService.getAllQuestions();
		return ResponseEntity.ok(allQuestions);
	}
	
	@GetMapping("/question/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long Id) throws ChangeSetPersister.NotFoundException {
		Optional<Question> question = questionService.getQuestionById(Id);
		if (question.isPresent()) {
			return ResponseEntity.ok(question.get());
		} else {
			throw new ChangeSetPersister.NotFoundException();
		}
	}
	
	@PutMapping("/question/{id}/update")
	public ResponseEntity<Question> updateQuestion(@PathVariable("id") Long Id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
		Question updatedQuestion = questionService.updateQuestion(Id, question);
		return ResponseEntity.ok(updatedQuestion);
	}
	
	@DeleteMapping("/question/{id}/delete")
	public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long Id) {
		questionService.deleteQuestion(Id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/subjects")
	public ResponseEntity<List<String>> getAllSubjects() {
		List<String> subjects = questionService.getAllSubjects();
		return ResponseEntity.ok(subjects);
	}
	
	@GetMapping("/random")
	public ResponseEntity<List<Question>> getRandomQuestions(@RequestParam Integer numOfQuestions, @RequestParam String subjects) {
		List<Question> questions = questionService.getQuestionsByNumber(numOfQuestions, subjects);
		List<Question> mutableQuestions = new ArrayList<>(questions);
		Collections.shuffle(mutableQuestions);
		int availableQuestions = Math.min(numOfQuestions, mutableQuestions.size());
		List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);
		return ResponseEntity.ok(randomQuestions);
	}
	
}