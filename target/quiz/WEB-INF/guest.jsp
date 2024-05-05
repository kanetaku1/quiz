<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/guest.css">
  <title>Client select guest</title> 
</head>
<body>
  <h1>
      ルーム参加
  </h1>
  <div id="log"></div>
  <input type="text" id="message" placeholder="Type a message...">
  <button onclick="sendMessage()">Send</button>

  <script>
    // WebSocket接続
    var webSocket = new WebSocket("ws://localhost:8025/websocket");
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
  </script>
  
</body>
</html>
