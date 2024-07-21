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
  <div id="quiz-list">
    <h1>問題ジャンル</h1>
    <form action="searchMode" method="get">
      <select name="selectedGenre">
        <option value="">ジャンルを選択</option>
        <% for (String genre : genreList) { %>
          <option value="<%= genre %>" <%= genre.equals(selectedGenre) ? "selected" : "" %>>
            <%= genre %>
          </option>
        <% } %>
      </select>
      <input type="submit" value="問題を表示">
    </form>
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

  <div id="quiz-detail" style="display:none;">
    <h2>問題詳細</h2>
    <img id="image" src="#" alt="問題画像">
    <p><strong>問題:</strong></p>
    <p id="question"></p>
    <p><strong>答え:</strong></p>
    <p id="answer"></p>
    <button onclick="closeQuestionDetail()">閉じる</button>
  </div>

  <button onclick="Home()">ホームに戻る</button>
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
      if (quizData.imagePaths[index] != "") {
        image.style.display = "block";
        image.src = quizData.imagePaths[index];
      } else {
        image.style.display = "none"; //写真パスがない場合、非表示にする
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