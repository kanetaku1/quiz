package main;

import java.util.ArrayList;
import java.util.List;

public class QuizList {
  private List<String> imagePaths;
  private List<String> questions;
  private List<String> answers;
  private List<String> genres;

  // public QuizList(List<String> imagePaths, List<String> questions, List<String> answers, List<String> genres){
  //   this.imagePaths = imagePaths;
  //   this.questions = questions;
  //   this.answers = answers;
  //   this.genres = genres;
  // }
  public QuizList(){
    this.imagePaths = new ArrayList<>();
    this.questions = new ArrayList<>();
    this.answers = new ArrayList<>(); 
    this.genres = new ArrayList<>();
  }

  public void setQuizData(String ImagePath, String Question, String Answer){
    this.imagePaths.add(ImagePath);
    this.questions.add(Question);
    this.answers.add(Answer);
  }

  public List<String> getImagePaths(){
    return this.imagePaths;
  }

  public List<String> getQuestions(){
    return this.questions;
  }

  public List<String> getAnswers(){
    return this.answers;
  }

  public void showData(){
    if(imagePaths.size() != 0){
      System.out.println("Image Path: " + imagePaths);
      System.out.println("Question: " + questions);
      System.out.println("Answer: " + answers);
    } else{
      System.out.println("null");
    }
  }
}