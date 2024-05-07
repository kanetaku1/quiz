<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %> 

<% 
  List<String> imagePathList = (List<String>) request.getAttribute("imagePath");
  List<String> questionList = (List<String>) request.getAttribute("question");
  List<String> answerList = (List<String>) request.getAttribute("answer");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>クイズゲーム画面</title>
</head>
<body>
  <h1>問題</h1>
  <p><%= questionList.get(0) %></p>
  <h1>答え</h>
  <p><%= answerList.get(0) %></p>
  <h1>写真パス</h1>
  <p><%= imagePathList.get(0) %></p>
</body>
</html>