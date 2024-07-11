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
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/quiz.css">
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
    <input type="text" id="inputText" name="inputText">
    <input type="submit" id="submitButton" value="submit">
    <div id="gameLog">ゲームログ</div>
  </div>

  <div id="scoreBoard" style="display: none;">
    <div class="button-container">
      <button id="transition-button">ホームに戻る</button>
    </div>
    <h1>🌷結果発表🌷</h1>
    <table>
      <thead>
        <tr>
          <th>プレイヤー名</th>
          <th>獲得ポイント</th>
        </tr>
      </thead>
      <tbody id="result-table">
        <!-- 結果をここに動的に追加する -->
      </tbody>
    </table>
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
      if (data.type == "chat") {
        log.innerHTML += "<p>" + data.content + "</p>";
      } else if (data.type == "quiz") {
        //quiz.textContent = data.question;
        displayCharbychar(data.question);
        image.src = data.imagePath;
      } else if (data.type == "gameStarted"){
        genre.textContent = data.content;
        document.getElementById("waitingRoom").style.display = "none";
        document.getElementById("gameScreen").style.display = "block";
      } else if (data.type == "ServerMessage"){
        gameLog.innerHTML += "<p>" + data.content + "</p>";
      } else if (data.type == "gameEnd"){
        // // リンク先のURLを構築
        // var url = "ForwardToResult" + encodeURIComponent(selectedGenre);
        // window.location.href = url;
        makeScores(data.scores);
        document.getElementById("gameScreen").style.display = "none";
        document.getElementById("scoreBoard").style.display = "block";
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

    //一文字ずつ表示
    function displayCharbychar(problemStatement){
      for(let i=0;i<problemStatement.length;i++){
        setTimeout(function() {
          quiz.textContent += problemStatement[i];
        }, i*200);//200ms間隔
      }
    }

    //結果を表示
    function makeScores(scores){
      console.log(scores);
      // スコアデータを配列に変換し、得点でソート
      const scoresArray = Object.entries(scores).map(([username, score]) => ({ username, score }));
      scoresArray.sort((a, b) => b.score - a.score);
      // スコアボードにデータを表示
      const resultTable = document.getElementById('result-table');
      scoresArray.forEach(({ username, score }) => {
        const row = document.createElement('tr');
        const usernameCell = document.createElement('td');
        const scoreCell = document.createElement('td');
        usernameCell.textContent = username;
        scoreCell.textContent = score;
        row.appendChild(usernameCell);
        row.appendChild(scoreCell);
        resultTable.appendChild(row);
      });
    }

    // ホームに戻るボタンのイベントリスナーを追加
    document.getElementById('transition-button').addEventListener('click', () => {
        window.location.href = 'form'; // ホームページのURLに置き換えてください
    });

    document.getElementById("submitButton").addEventListener("click", function() {
      var inputText = document.getElementById("inputText").value;
      document.getElementById("inputText").value = ""; 

      var message = {
        action: "submitAnswer",
        answer: inputText
      };
      webSocket.send(JSON.stringify(message));
    });
  </script>
</body>
</html>
