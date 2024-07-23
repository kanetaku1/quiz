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
  <link rel="stylesheet" href="css/result.css">
  <link rel="stylesheet" href="css/quiz.css">
  <title>クイズモード</title> 
</head>
<body>
  <audio id="bgmAudio" loop>
    <source src="resources/Specification.mp3" type="audio/mpeg">
  </audio>
  <audio id="effectsAudio">
    <audio id="correctSound">
      <source src="resources/Quiz-Correct_Answer02-2.mp3" type="audio/mpeg">
    </audio>
    <audio id="incorrectSound">
      <source src="resources/Quiz-Wrong_Buzzer02-2.mp3" type="audio/mpeg">
    </audio>
    <audio id="quizSound">
      <source src="resources/Quiz-Question03-1.mp3" type="audio/mpeg">
    </audio>
  </audio>

  <div class="userLog" id="userLog">
    <div id="genre" class="genre_title"></div>
    <div id="userList" class="userList"></div>
  </div>
  <div id="roomLog" style="display:none;">
      ルームログ
  </div>
  <div id="Timer" class="Timer" style="visibility:hidden">
    <div class="timer_text">残り..</div>
    <div id="timer"></div>
  </div>

  <div id="waitingRoom">
    <div class="content_waitingRoom">
      <% if(user.getUserType() == User.UserType.HOST) { %>
        <div class="content_left_host"><!-- ジャンル選択画面(ホストのみ)のときのcontent_leftクラス -->
          <h1 class="title_text_host">ジャンル選択</h1>
          <div class="content_genre">
            <button class="select" id="select">Start</button>
            <div class="genre_select">
              <select class="dropdown" id="dropdown" required>
                <option value=""></option>
                <% for (String genre : genreList) { %>
                  <option value="<%= genre %>"><%= genre %></option>
                <% } %>
              </select>
              <span class="select_highlight"></span>
              <span class="select_selectbar"></span>
              <label class="select_label">Choose</label>
            </div>
          </div>
        </div>
        <script>
            /// 選択されたジャンルを送信＆ゲームスタート
          document.getElementById("select").addEventListener("click", function() {
            var dropdown = document.getElementById("dropdown");
            var selectedGenre = dropdown.value;
            webSocket.send(JSON.stringify({
              action: "startGame",
              genre: selectedGenre
            })); 
          });
        </script>
      <% } else { %>
        <div class="content_left_guest"><!-- ゲスト待機画面のときのcontent_leftクラス -->
          <h1 class="title_text_guest">ホストがゲームを開始するのを待っています...</h1>
        </div>
      <% } %>
      <br>
      <div class="content_chats">
        <div id="chatLog"></div>
        <div class="input-wrapper">
          <div class="message_box">
            <input type="text" id="message" placeholder="Type a message...">
          </div>
          <button onclick="sendMessage()">Send</button>
        </div>
      </div>
    </div>
  </div>

  <div id="gameScreen" style="display:none;">
    <!-- <p id="userType"><%= user.getUserType() %></p> -->
    <div class="quiz-container">
        <div class="quiz_wrapper">
            <div class="quiz_state">
                <h1>Q.</h1>
                <p id="quiz"></p>
            </div>
            <div class="imageSection" id="imageSection">
              <img class="image" id="image" src="#">
            </div>
        </div>
        <div class="answerSection" id="answerSection">
          <div class="inputText">
              <div id="inputText"></div>
          </div>
          <div class="buttons" id="buttons" style="display:none">
            <div class="buttons_B">
                <button id="upButton" class="answer-button" onclick="clickButtonAnswer(this.textContent)">上</button>
                <div class="buttons_A">
                    <button id="leftButton" class="answer-button" onclick="clickButtonAnswer(this.textContent)">左</button>
                    <div class="centerButton"></div>
                    <button id="rightButton" class="answer-button" onclick="clickButtonAnswer(this.textContent)">右</button>
                </div>
                <button id="downButton" class="answer-button" onclick="clickButtonAnswer(this.textContent)">下</button>
            </div>
          </div>
          <div id="gameLog" style="display:none">ゲームログ</div>    
          <div class="displayAnswer" id="displayAnswer">
            <h1>A.</h1>
            <p class="display_answer" id="display_answer">answer</p>
          </div>
        </div>
    </div>
  </div>

  <div id="scoreBoard" style="display: none;">
    <div class="button-container">
      <button onclick="Home()">ホームに戻る</button>
    </div>
    <div class="result_title">
      <h1>🌷結果発表🌷</h1>
    </div>
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
    var timer;
    var chatLog = document.getElementById("chatLog");
    var roomLog = document.getElementById("roomLog");
    var quiz = document.getElementById("quiz");
    var image = document.getElementById("image");
    var gameLog = document.getElementById("gameLog");
    // const userType = document.getElementById("userType");
    const genre = document.getElementById("genre");
    const bgmAudio = document.getElementById("bgmAudio");
    const effectsAudio = document.getElementById('effectsAudio');
    const correctSound = document.getElementById("correctSound");
    const incorrectSound = document.getElementById("incorrectSound");
    const quizSound = document.getElementById("quizSound");
    var answerSection = document.getElementById("answerSection");

    // WebSocket接続
    const host = window.location.hostname;
    const port = window.location.port;
    var webSocket = new WebSocket(`ws://${host}:${port}/quiz/websocket/<%= sessionId %>`);
    
    // ひらがなボタンのリスト
    const answerButtons = document.querySelectorAll(".answer-button");
    // ひらがな文字リスト
    const hiragana = ["ー","あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ", "さ", "し", "す", "せ", "そ", "た", "ち", "つ", "て", "と", "な", "に", "ぬ", "ね", "の", "は", "ひ", "ふ", "へ", "ほ", "ま", "み", "む", "め", "も", "や", "ゆ", "よ", "ら", "り", "る", "れ", "ろ", "わ", "を", "ん"];
    const katakana = ["ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ", "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ツ", "テ", "ト", "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヲ", "ン"];
    const numbers = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
    const english = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];

    let usingList = [];
    let displayword_4 = [];
    var currentIndex = 0;
    var currentAnswer;
    let isAnswerMode = false;

    webSocket.onopen = function(event) {
      console.log("WebSocket connection opened.");
      applyAudioSettings();
      roomLog.innerHTML += "<p>WebSocket connection opened.</p>";
    };

    webSocket.onerror = function(error) {
      console.error("WebSocket error: " + error);
      roomLog.innerHTML += "<p>Error: " + error + "</p>";
    };

    webSocket.onclose = function(event) {
      console.log("WebSocket connection closed.");
      roomLog.innerHTML += "<p>WebSocket connection closed.</p>";
    };

    webSocket.onmessage = function(event) {
      var data = JSON.parse(event.data);
      if (data.type === "chat") {
        chatLog.innerHTML += "<p>" + data.content + "</p>";
            // 親要素の高さを取得
        var parentHeight = chatLog.parentElement.clientHeight;
        // チャットログの高さが親要素の高さを超えた場合
        while (chatLog.scrollHeight > chatLog.clientHeight) {
          // チャットログの一番上のメッセージを削除
          var firstChild = chatLog.firstElementChild;
          if (firstChild) {
            chatLog.removeChild(firstChild);
          }
        }
      } else if (data.type === "room") {
        roomLog.innerHTML += "<p>" + data.content + "</p>";
        if(data.content === "GAME_START"){
          document.getElementById("select").disabled = true;
        }
      } else if (data.type === "userList") {
        updateUserList(JSON.parse(data.data));
      } else if (data.type == "quiz") {
        document.getElementById("inputText").textContent ="";
        document.getElementById("waitingRoom").style.display = "none";
        displayAnswer.style.visibility = "hidden";
        gameLog.style.display = "none";
        document.getElementById("gameScreen").style.display = "block";
        imageSection.style.display = "flex";
        document.getElementById("Timer").style.visibility="visible"
        quiz.textContent = "";
        currentAnswer = data.answer;//現在の問題の答えを取得
        playEffectSound(quizSound);
        displayCharbychar(data.question, function() {
          answerSection.style.display = "flex";
          buttons.style.display = "flex";
          document.getElementById("inputText").style.display = "flex";
          currentIndex = 0;
          selectDisplayWordList(currentAnswer[currentIndex]);
          updateAnswerButtons();
        });
        if (data.imagePath) {
          image.src = data.imagePath;
        } else {
          imageSection.style.display = "none"; //写真パスがない場合、非表示にする
        }
        startTimer(data.timeout); // Timerスタート
        gameLog.innerHTML = "";
      } else if (data.type == "gameStarted"){
        genre.textContent = data.content;
        document.getElementById("waitingRoom").style.display = "none";
        document.getElementById("gameScreen").style.display = "block";
      } else if (data.type === "ServerMessage"){
        switch(data.content){
          case "Correct!":
            playEffectSound(correctSound);
            break;
          case "Incorrect!":
            playEffectSound(incorrectSound);
        }
        buttons.style.display = "none";
        gameLog.style.display = "flex";
        gameLog.innerHTML = "<p>" + data.content + "</p>";
      } else if(data.type === "displayAnswer"){
        gameLog.style.display = "flex";
        displayAnswer.style.visibility = "visible";
        display_answer.textContent = currentAnswer;
      } else if (data.type === "gameEnd"){
        makeScores(data.scores);
        document.getElementById("gameScreen").style.display = "none";
        document.getElementById("userLog").style.display = "none";
        document.getElementById("timer").style.display = "none";
        document.getElementById("scoreBoard").style.display = "block";
        document.getElementById("Timer").style.visibility="hidden"
      } 
    };

    /// チャットのメッセージをサーバーへ送信
    function sendMessage() {
      var messageInput = document.getElementById("message");
      var message = messageInput.value;
      webSocket.send(JSON.stringify({
        action: "chat", 
        message: message
      }));
      messageInput.value = "";
    }
    
    /// ユーザ情報を共有
    function updateUserList(userList) {
      const userListElement = document.getElementById('userList');
      userListElement.innerHTML = '';
      userList.forEach(user => {
        const userElement = document.createElement('div');
        userElement.className = `user ${user.userType}`;
        userElement.innerHTML = `
        <div class="username">${user.username}</div>
        <div class="score">${user.score}</div>
        `;
        userListElement.appendChild(userElement);
      });
    }

    /// 入力された回答をサーバーへ送信
    function sendAnswer() {
      clearInterval(timer);
      var answerInput = document.getElementById("inputText");
      var Answer = answerInput.textContent;
      webSocket.send(JSON.stringify({
        action: "submitAnswer", 
        answer: Answer
      }));
      //buttons.style.display = "none"; // 次の問題のために解答セクションを非表示にする
    }
    
    //一文字ずつ表示
    function displayCharbychar(problemStatement, callback){
      for(let i=0;i<problemStatement.length;i++){
        setTimeout(function() {
          quiz.textContent += problemStatement[i];
          if (i === problemStatement.length - 1) {
            callback();
          }
        }, i*200);
      }
    }
    
    
    //現在の答えの文字列を判別し、適切なリストを選択する関数
    function selectDisplayWordList(nowWord) {
      isKatakana = false;
      isEnglish = false;
      isDigit =false;
      // カタカナチェック
      const isKatakana = /^[\u30A0-\u30FF]+$/.test(nowWord);
      // 英語チェック
      const isEnglish = /^[A-Za-z]+$/.test(nowWord);
    // 数字チェック
      const isDigit = /^[0-9]+$/.test(nowWord);

      if (isKatakana) {
        usingList = katakana;
      } else if (isEnglish) {
        usingList = english;
      } else if (isDigit) {
        usingList = numbers;
      } else {
        usingList = hiragana;
      }
    }

    // Fisher-Yatesアルゴリズムを使用して配列をシャッフルする関数
    function shuffleArray(array) {
      for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
      }
      return array;
    }
    
    document.addEventListener('keydown', function(event) {
      if (!isAnswerMode) return;
      switch (event.key) {
        case 'ArrowUp':
          console.log("upButton");
          document.getElementById("upButton").click();
          break;
        case 'ArrowLeft':
          console.log("leftButton");
          document.getElementById("leftButton").click();
          break;
        case 'ArrowRight':
          console.log("rightButton");
          document.getElementById("rightButton").click();
          break;
        case 'ArrowDown':
          console.log("downButton");
          document.getElementById("downButton").click();
          break;
      }
    });
    // ランダムなひらがなをボタンに設定する関数
    function updateAnswerButtons() {
      displayword_4 = [];
      displayword_4.push(currentAnswer[currentIndex]);
      for (let j = 0; j < 3; j++) {
        const randomIndex = Math.floor(Math.random() * usingList.length);
        const randomChar = usingList[randomIndex];
        displayword_4.push(randomChar);
      }
      displayword_4 = shuffleArray(displayword_4);//要素をシャッフルシャッフル♪♪
      var displayIndex = 0;
      answerButtons.forEach(button => {
        button.textContent = displayword_4[displayIndex];
        displayIndex += 1;
      });
      currentIndex += 1;//次の文字のインデックスへ
      isAnswerMode = true;
    }
    
    function clickButtonAnswer(text) {
      const answerInput = document.getElementById("inputText");
      answerInput.textContent += text;
      if (currentAnswer.length <= currentIndex) {
        sendAnswer(); // 解答の文字数分入力したら、強制的に解答を送信
        isAnswerMode = false;
      } else{
        updateAnswerButtons();
      }
    }

    function startTimer(timeout) {
      var timeLeft = timeout;
      var timerElement = document.getElementById("timer");
      
      clearInterval(timer); // 既存のタイマーをクリア

      timer = setInterval(function() {
        timerElement.textContent = timeLeft;
        timeLeft--;
        
        if (timeLeft < 0) {
          clearInterval(timer);
          webSocket.send(JSON.stringify({
            action: "giveUp",
          })); 
        }
      }, 1000);
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

    // 設定を適用する関数
    function applyAudioSettings() {
      const bgmEnabled = localStorage.getItem('bgmEnabled') === 'true';
      const effectsEnabled = localStorage.getItem('effectsEnabled') === 'true';
      const bgmVolume = localStorage.getItem('bgmVolume') || '50';
      const effectsVolume = localStorage.getItem('effectsVolume') || '50';
      
      // BGMの設定を適用
      if (bgmEnabled) {
        bgmAudio.volume = parseInt(bgmVolume) / 100;
        bgmAudio.play();
      } else {
         bgmAudio.pause();
      }
      
      // 効果音の設定を適用
      effectsAudio.volume = parseInt(effectsVolume) / 100;
      effectsAudio.muted = !effectsEnabled;
    }

    // 効果音を再生する関数（例）
    function playEffectSound(effectAudio) {
      if (localStorage.getItem('effectsEnabled') === 'true') {
        effectAudio.play();
      }
    }

    function Home() {
      window.location.href = 'home';
    }
  </script>
</body>
</html>
