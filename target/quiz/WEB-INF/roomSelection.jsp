<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/roomSelection.css">
    <title>ルーム選択</title>
</head>
<body>
    <h1>
        解答モード
    </h1>
    <button onclick="Home()">ホームに戻る</button>
    <div class="room_buttons">
      <button id ="create_room" class="solve_selectStart">
        <a href="host">Start Room</a>
      </button>
      <button id ="join_room" class="solve_selectJoin">
        <a href="guest">Join Room</a>
      </button>
    </div>

    <script>
        function Home() {
            window.location.href = 'home';
        }
    </script>
</body>
</html>
