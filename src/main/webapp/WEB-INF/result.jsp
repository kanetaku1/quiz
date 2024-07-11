<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page import="main.User" %> <% String sessionId =
session.getId(); User user = UserManager.getUser(sessionId); %>

<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>クイズ結果</title>
    <link rel="stylesheet" href="css/result.css" />
  </head>

  <body>
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

    <script>
      // サンプルデータ
      const players = [
        { name: "いとひろ", score: 80 },
        { name: "かりきず", score: 85 },
        { name: "たくろー", score: 90 },
        { name: "しゃちょー", score: 65 },
        { name: "幻の5人目", score: 30 },
      ];

      // 結果を表示する関数
      function displayResults() {
        const resultTable = document.getElementById("result-table");

        // スコアが高い順に並べ替え
        players.sort((a, b) => b.score - a.score);

        // 各プレイヤーの結果を表示
        players.forEach((player) => {
          const row = document.createElement("tr");
          const nameCell = document.createElement("td");
          const scoreCell = document.createElement("td");

          nameCell.textContent = player.name;
          scoreCell.textContent = player.score;

          row.appendChild(nameCell);
          row.appendChild(scoreCell);
          resultTable.appendChild(row);
        });

        // 最大20行までの空の行を作成
        for (let i = 0; i < 20; i++) {
          const row = document.createElement("tr");
          const nameCell = document.createElement("td");
          const scoreCell = document.createElement("td");

          row.appendChild(nameCell);
          row.appendChild(scoreCell);
          resultTable.appendChild(row);
        }

        // ボタン
        const transitionButton = document.getElementById("transition-button");
        transitionButton.addEventListener("click", function () {
          // ここに画面遷移のロジックを記述する
          console.log("次へボタンがクリックされました");
        });
      }

      // 結果を表示
      displayResults();
    </script>
  </body>
</html>
