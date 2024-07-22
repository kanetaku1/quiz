<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %> 
<%@ page import="main.QuizList" %>

<%
  List<String> genreList = (List<String>) request.getSession().getAttribute("genreList");
  QuizList quizList = (QuizList) request.getAttribute("quizList");
  String selectedGenre = (String) request.getAttribute("selectedGenre");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/search.css">
  <title>閲覧モード</title>
</head>
<body>
  <h1>閲覧モード</h1>
  <button class="back_button" onclick="Home()">ホームに戻る</button>
  <div id="quiz-list">
      <div class="select_genre">
        <form action="searchMode" method="get">
          <div class="genre_select">
            <select class="dropdown" id="dropdown" name="selectedGenre" required>
              <option value=""></option>
              <% for (String genre : genreList) { %>
                <option value="<%= genre %>" <%= genre.equals(selectedGenre) ? "selected" : "" %>>
                  <%= genre %>
                </option>
              <% } %>
            </select>
            <span class="select_highlight"></span>
            <span class="select_selectbar"></span>
            <label class="select_label">Choose</label>
          </div>
          <input class="submit_button" type="submit" value="問題を表示">
        </form>
      </div>
      <div class="quizs">
        <% if (selectedGenre != null) { %>
          <h2><%=  selectedGenre %>の問題</h2>
          <% if (quizList != null) {
            for (int i = 0; i < quizList.getQuestions().size(); i++) { %>
              <div class="question">
                <a href = "#" onclick="showQuestionDetail(<%=i%>)"><%= quizList.getQuestions().get(i) %></a>
              </div>
            <% } 
          } 
        }%>
      </div>
    </div>
  
    <div class="quiz-detail" id="quiz-detail" style="display:none">
      <h2>問題詳細</h2>
      <div class="detail-content">
        <div class="detail-quiz">
          <div class="detail-state">
            <p>Q.</p>
            <p id="question">牡蠣は柿でも、夏期の火器はなーんなんだ？？</p>
          </div>
          <div class="detail-answer">
            <p>A.</p>
            <p id="answer">白亜紀の秋だっつってんだろ</p>
          </div>
        </div>
        <div class="imageSection">
          <img id="image" src="#" alt="問題画像">
        </div>
        
      </div>
      <button class="close-button" onclick="closeQuestionDetail()">閉じる</button>
    </div> 

  <script>
    function Home() {
      window.location.href = 'home';
    }
  </script>

  <% if (selectedGenre != null) {
    org.json.JSONObject quizData = new org.json.JSONObject();
    quizData.put("questions", quizList.getQuestions());
    quizData.put("answers", quizList.getAnswers());
    quizData.put("imagePaths", quizList.getImagePaths());
  %>
  <script>
    var quizData = <%= quizData.toString() %>;
    var image = document.getElementById("image");
    var question = document.getElementById("question");
    var answer = document.getElementById("answer");
    var quiz_List = document.getElementById("quiz-list");
    var quiz_Detail = document.getElementById("quiz-detail");

    function showQuestionDetail(index) {
      quiz_List.style.display = "none";
      quiz_Detail.style.display = "block";
      question.textContent = quizData.questions[index];
      answer.textContent = quizData.answers[index];
      if(quizData.imagePaths[index]){
        image.style.visibility = "visible";
        image.src = quizData.imagePaths[index];
      }else{
        image.style.visibility = "hidden";
      }
      
    }

    function closeQuestionDetail() {
      quiz_Detail.style.display = 'none';
      quiz_List.style.display = 'block';
    }

    function Home() {
      window.location.href = 'home';
    }
  </script>
  <%
  } 
%>
</body>
</html>