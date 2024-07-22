<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>クイズゲーム画面</title>
</head>
<body>
  <p id="user">
    <%= request.getAttribute("userType") %>
  </p>
  <% if(request.getAttribute("userType").equals("HOST")){ %>
    <h1>ジャンル</h1>
    <p id="genre">
      <%= request.getAttribute("selectedGenre") %>
    </p>
  <% } %>
  <h1>問題</h1>
  <p id="quiz">第0問</p>
  <h1>写真パス</h1>
  <img id="image" src="#">
  <div id="log"></div>
  <!-- //解答システム -->
  <script>
    var log = document.getElementById("log");
    var quiz = document.getElementById("quiz");
    var image = document.getElementById("image");
    const userType = document.getElementById("user");
    const genre = document.getElementById("genre");

    // WebSocket接続
    var webSocket = new WebSocket("ws://localhost:8888/quiz/gameWebSocket");

    webSocket.onopen = function(event) {
      console.log("WebSocket connection opened.");
      log.innerHTML += "<p>WebSocket connection opened.</p>";
      if(userType.textContent.trim() == "HOST"){
        Initialize();
        console.log("request next Quiz.");
        setTimeout(function() {
          requestNextQuiz();
        }, 2000);       
      }
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
      console.log("Received message from server: " + event.data);
      if(event.data.startsWith("問題:") || event.data.startsWith("不正解")){
        quiz.textContent = event.data.slice(3);
      } else if(event.data.startsWith("写真:")){
        image.src = event.data.slice(3);
      } else {
        log.innerHTML += "<p>" + event.data + "</p>";
      }
    }

    function requestNextQuiz(){
      var message = {
        type: "nextQuiz",
        request: "request",
      }
      webSocket.send(JSON.stringify(message));
    }

    function Initialize(){
      var key = genre.textContent.trim();
      console.log(key)
      var message = {
        type: "startGame",
        genre: key,
      }
      webSocket.send(JSON.stringify(message));
    }

    //一文字ずつ表示
    function displayCharbychar(problemStatement){
      for(let i=0;i<problemStatement.length;i++){
        setTimeout(function() {
        quizElement.textContent += message[i];
        }, 200);//200ms間隔
      }
    }

  </script>

</body>
</html>