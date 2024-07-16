package main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

public class QuizManager {
  private QuizList quizList;
  private int currentQuestionIndex;
  private Map<String, User> users;
  private Map<User, Boolean> answered;
  private GetQuiz quizGetter;

  public QuizManager() {
    this.quizGetter = new GetQuiz();
    this.users = new ConcurrentHashMap<>();
    this.answered = new ConcurrentHashMap<>();
  }

  public void prepareQuiz(String genre) {
    quizGetter.getQuizData(genre);
    this.quizList = quizGetter.quizList;
    this.currentQuestionIndex = 0;
    this.answered.clear();
    for (User user : users.values()){
      user.setScore(0);
    }
  }

  public void addUser(User user){
    users.put(user.getUsername(), user);
  }

  public void removeUser(String username){
    users.remove(username);
  }

  public String getNextQuestion() {
    if (currentQuestionIndex < quizList.getQuestions().size()) {
      String question = quizList.getQuestions().get(currentQuestionIndex);
      String imagePath = quizList.getImagePaths().get(currentQuestionIndex);
      String answer = quizList.getAnswers().get(currentQuestionIndex);
      currentQuestionIndex++;
      answered.clear(); // 新しい問題のため、回答状況をリセット
      JSONObject quizData = new JSONObject();
      quizData.put("question", question);
      quizData.put("imagePath", imagePath);
      quizData.put("answer", answer);
      return quizData.toString();
    }
    return null;
  }

  public boolean checkAnswer(User user, String answer) {
    if (user != null && currentQuestionIndex > 0 && currentQuestionIndex <= quizList.getQuestions().size()) {
      String currentQuizAnswer = quizList.getAnswers().get(currentQuestionIndex -1);
      boolean isCorrect = currentQuizAnswer.equals(answer);
      if (isCorrect) {
        user.setScore(user.getScore() + 1);
      }
      answered.put(user, true);
      return isCorrect;
    }
    return false;
  }

  public boolean allAnswered() {
    return answered.size() == users.size();
  }

  public boolean hasMoreQuestions() {
    return currentQuestionIndex < getTotalQuestions();
  }

  public Map<User, Integer> getFinalScores() {
    Map<User, Integer> scores = new HashMap<>();
    for (User user : users.values()) {
      scores.put(user, user.getScore());
    }
    return scores;
  }

  public int getCurrentQuestionNumber() {
    return currentQuestionIndex;
  }

  public int getTotalQuestions() {
    return quizList.getQuestions().size();
  }

  public String getCurrentQuestionImagePath() {
    if (currentQuestionIndex > 0 && currentQuestionIndex <= quizList.getQuestions().size()) {
      return quizList.getImagePaths().get(currentQuestionIndex - 1);
    }
    return null;
  }
}