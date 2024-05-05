<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>WebSocket Client</title>
  <!-- <link rel="stylesheet" href="stylesheet.css"> -->
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>画像表示</title>
</head>
<body>
  <h2>問題のジャンル</h2>
  <form action="someAction.jsp" method="post">
    <textarea name="sakumon" rows="2" cols="50"></textarea>
  </form>
  <h2 class="mondai">問題</h2>
  <form action="someAction.jsp" method="post">
    <textarea name="sakumon" rows="5" cols="150"></textarea>
  </form>
  <div class="center">
    <h2>解答</h2>
    <form action="someAction.jsp" method="post">
      <textarea name="sakumon" rows="2" cols="50"></textarea>
    </form>
    <h3 class="chat"> 
      <input type="file" id="example" multiple>
      <div id="preview"></div>
      <script>
        function previewFile(file) {
          // プレビュー画像を追加する要素
          const preview = document.getElementById('preview');

          // FileReaderオブジェクトを作成
          const reader = new FileReader();

          // ファイルが読み込まれたときに実行する
          reader.onload = function (e) {
            const imageUrl = e.target.result; // 画像のURLはevent.target.resultで呼び出せる
            const img = document.createElement("img"); // img要素を作成
            img.src = imageUrl; // 画像のURLをimg要素にセット
            preview.appendChild(img); // #previewの中に追加
          }

          // いざファイルを読み込む
          reader.readAsDataURL(file);
        }

        // <input>でファイルが選択されたときの処理
        const fileInput = document.getElementById('example');
        const handleFileSelect = () => {
          const files = fileInput.files;
          for (let i = 0; i < files.length; i++) {
            previewFile(files[i]);
          }
        }
        fileInput.addEventListener('change', handleFileSelect);
      </script>
    </h3>
  </div>
  <h3 class="chat">
    <button type="submit">完了</button>
  </h3>
  <h2></h2>
</body>
</html>
