<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %> 

<%
  List<String> genreList = (List<String>) request.getAttribute("genreList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/host.css">
  <title>Client select host</title> 
</head>
<body>
  <h1>
    ルーム作成
  </h1>
  <button id="select" onclick="startGame()">Start</button>
  <select id="dropdown">
    <option value="">-</option>
    
    <% for (String genre : genreList) { %>
      <option value = "<%= genre %>"><%= genre %></option>
    <% } %>
  </select>
  <br>
  <div id="log"></div>
  <input type="text" id="message" placeholder="Type a message...">
  <button onclick="sendMessage()">Send</button>

  <script>
    // WebSocket接続
    var webSocket = new WebSocket("ws://localhost:8888/quiz/websocket");
    var log = document.getElementById("log");
    
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
      log.innerHTML += "<p>" + event.data + "</p>";
    }

    function sendMessage() {
      // 入力されたメッセージを送信
      var messageInput = document.getElementById("message");
      var message = messageInput.value;
      webSocket.send(message);
      messageInput.value = "";
    }

    function startGame() {
      // 選択されたジャンルを取得
      var dropdown = document.getElementById("dropdown");
      var selectedGenre = dropdown.value;

      // リンク先のURLを構築
      var url = "gameStart?genre=" + encodeURIComponent(selectedGenre);
      
      // リンク先に遷移
      window.location.href = url;
    }

  </script>

</body>
</html>
