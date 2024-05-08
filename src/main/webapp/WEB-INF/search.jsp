<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %> 

<%
  List<String> genreList = (List<String>) request.getAttribute("genreList");
  List<String> questionList = (List<String>) request.getAttribute("question");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/search.css">
  <title>Start View Mode</title>
</head>
<body>
  <h1>
    問題ジャンル
    <div class="select-box01">
      <select id="subject" onchange="startSearch()" >
        <option value="">ジャンルを選択してください</option>
        <%if (genreList != null) {
          for (String genre : genreList) { %>
            <option value = "<%= genre %>"><%= genre %></option>
          <% } 
        } %>
        <input type="hidden" name="genres" value="<%= genreList %>">
      </select>
    </div>
  </h1>
      
  <div id="Quiz">
    <h2><%= request.getAttribute("selectedGenre") %>の問題</h2>
    <%if (questionList != null) {
      for (String question : questionList) { %>
        <p><a href = "editQuiz?question=<%= question %>"><%= question %></a></p>
      <% } 
    } %>
  </div>

  <script>
    function startSearch(){
      // 選択されたジャンルを取得
      var subject = document.getElementById("subject");
      var selectedGenre = subject.value;

      // リンク先のURLを構築
      var url = "searchMode?genre=" + encodeURIComponent(selectedGenre);
      
      // リンク先に遷移
      window.location.href = url;
    }
  </script>
</body>
</html>