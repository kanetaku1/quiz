<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %> 
<%@ page import="main.User" %>
<%@ page import="main.UserManager" %>

<%
  List<String> genreList = (List<String>) request.getAttribute("genreList");
  String sessionId = session.getId();
  User user = UserManager.getUser(sessionId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/host.css">
  <title>クイズゲーム</title> 
</head>
<body>
  <div id="waitingRoom">
    <h1>ルーム作成</h1>
    <% if(user.getUserType() == User.UserType.HOST) { %>
      <button id="select">Start</button>
      <select id="dropdown">
        <option value="">-</option>
        <% for (String genre : genreList) { %>
          <option value="<%= genre %>"><%= genre %></option>
        <% } %>
      </select>
    <% } else { %>
      <p>ホストがゲームを開始するのを待っています...</p>
    <% } %>
    <br>
    <div id="log"></div>
    <input type="text" id="message" placeholder="Type a message...">
    <button onclick="sendMessage()">Send</button>
  </div>

  <div id="gameScreen" style="display:none;">
    <p id="userType"><%= user.getUserType() %></p>
    <h1>ジャンル</h1>
    <p id="genre"></p>
    <h1>問題</h1>
    <p id="quiz">第0問</p>
    <h1>写真パス</h1>
    <img id="image" src="#">
    <div id="gameLog"></div>
  </div>

  <script>
    var log = document.getElementById("log");
    var quiz = document.getElementById("quiz");
    var image = document.getElementById("image");
    var gameLog = document.getElementById("gameLog");
    const userType = document.getElementById("userType");
    const genre = document.getElementById("genre");

    // WebSocket接続
    var webSocket = new WebSocket("ws://localhost:8888/quiz/websocket/<%= sessionId %>");

    webSocket.onopen = function(event) {
      console.log("WebSocket connection opened.");
      log.innerHTML += "<p>WebSocket connection opened.</p>";
    };

    webSocket.onerror = function(error) {
      console.error("WebSocket error: " + error);
      log.innerHTML += "<p>Error: " + error + "</p>";
    };

    webSocket.onclose = function(event) {
      console.log("WebSocket connection closed.");
      log.innerHTML += "<p>WebSocket connection closed.</p>";
    };

    webSocket.onmessage = function(event) {
      var data = JSON.parse(event.data);
      if (data.type === "chat") {
        log.innerHTML += "<p>" + data.content + "</p>";
      } else if (data.type === "game") {
        gameLog.innerHTML += "<p>" + data.content + "</p>";
      } else if (data.type === "quiz") {
        quiz.textContent = data.question;
        image.src = data.imagePath;
      } else if (data.type == "gameStarted"){
        genre.textContent = data.content;
        document.getElementById("waitingRoom").style.display = "none";
        document.getElementById("gameScreen").style.display = "block";
      } else if (data.type == "gameEnd"){

      }
    };

    function sendMessage() {
      var messageInput = document.getElementById("message");
      var message = messageInput.value;
      webSocket.send(JSON.stringify({action: "chat", message: message}));
      messageInput.value = "";
    }

    document.getElementById("select").addEventListener("click", function() {
      var dropdown = document.getElementById("dropdown");
      var selectedGenre = dropdown.value;

      var message = {
        action: "startGame",
        genre: selectedGenre
      };
      webSocket.send(JSON.stringify(message)); 
    });
  </script>
</body>
</html>
