<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>サウンド設定</title>
  <link rel="stylesheet" href="css/setting.css">
</head>
<body>
  <div class="container">
    <h1>サウンド設定</h1>
    <button onclick="Home()">ホームに戻る</button>
    <form id="soundSettingsForm">
      <div class="form-group">
        <input type="checkbox" id="bgm" name="bgm" checked>
        <label for="bgm">BGM</label>
      </div>
      <div class="form-group">
        <input type="checkbox" id="effects" name="effects" checked>
        <label for="effects">効果音</label>
      </div>
      <div class="form-group">
        <label for="bgmVolume">BGM音量:</label>
        <input type="range" id="bgmVolume" name="bgmVolume" min="0" max="100" value="50" oninput="document.getElementById('bgmVolumeOutput').innerText = this.value">
        <span id="bgmVolumeOutput">50</span>
      </div>
      <div class="form-group">
        <label for="effectsVolume">効果音音量:</label>
        <input type="range" id="effectsVolume" name="effectsVolume" min="0" max="100" value="50" oninput="document.getElementById('effectsVolumeOutput').innerText = this.value">
        <span id="effectsVolumeOutput">50</span>
      </div>
      <button type="submit">設定を保存</button>
    </form>
  </div>
  <script>
    function Home() {
      window.location.href = 'home';
    }
  </script>
</body>
</html>