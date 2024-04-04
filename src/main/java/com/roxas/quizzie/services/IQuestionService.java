package com.roxas.quizzie.services;

import com.roxas.quizzie.models.Question;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
	Question createQuestion(Question question);
	List<Question> getAllQuestions();
	Optional<Question> getQuestionById(Long Id);
	Question updateQuestion(Long Id, Question question) throws ChangeSetPersister.NotFoundException;
	void deleteQuestion(Long Id);
	List<String> getAllSubjects();
	List<Question> getQuestionsByNumber(Integer numOfQuestions, String subject);
}