<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>WebSocket Client</title>
  <!-- <link rel="stylesheet" href="stylesheet.css"> -->
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>作問モード</title>
</head>
<body>
  <form action="makeMode" method="post" enctype="multipart/form-data">
    <input type="file" name="imageFile" id="image"><br>
    <div id="upload"></div>
    <input type="text" name="genre" placeholder="Enter genre"><br>
    <input type="text" name="question" placeholder="Enter question"><br>
    <input type="text" name="answer" placeholder="Enter answer"><br>
    <input type="submit" value="Submit">
  </form>
  <script>
    function previewFile(file) {
      // プレビュー画像を追加する要素
      const preview = document.getElementById('upload');
      // 以前のプレビューを削除
      preview.innerHTML = '';

      // FileReaderオブジェクトを作成
      const reader = new FileReader();

      // ファイルが読み込まれたときに実行する
      reader.onload = function (e) {
        const img = document.createElement("img"); // img要素を作成
        img.src = e.target.result; // 画像のURLをimg要素にセット
        img.style.maxWidth = "100%"
        preview.appendChild(img); // #previewの中に追加
      }

      // いざファイルを読み込む
      reader.readAsDataURL(file);
    }

    // <input>でファイルが選択されたときの処理
    const fileInput = document.getElementById('image');
    const handleFileSelect = () => {
      const files = fileInput.files;
      if (files.length > 0) {
        previewFile(files[0]); // 最初のファイルのみをプレビュー
      }
    }
    fileInput.addEventListener('change', handleFileSelect);
  </script>
</body>
</html>
