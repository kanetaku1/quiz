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
    document.getElementById('soundSettingsForm').addEventListener('submit', function(e) {
      e.preventDefault();
      
      // フォームの値を取得
      const bgmEnabled = document.getElementById('bgm').checked;
      const effectsEnabled = document.getElementById('effects').checked;
      const bgmVolume = document.getElementById('bgmVolume').value;
      const effectsVolume = document.getElementById('effectsVolume').value;
      
      // LocalStorageに保存
      localStorage.setItem('bgmEnabled', bgmEnabled);
      localStorage.setItem('effectsEnabled', effectsEnabled);
      localStorage.setItem('bgmVolume', bgmVolume);
      localStorage.setItem('effectsVolume', effectsVolume);
      
      alert('設定が保存されました');
    });

    // ページロード時に保存された設定を読み込む
    window.addEventListener('load', function() {
      const bgmEnabled = localStorage.getItem('bgmEnabled');
      const effectsEnabled = localStorage.getItem('effectsEnabled');
      const bgmVolume = localStorage.getItem('bgmVolume');
      const effectsVolume = localStorage.getItem('effectsVolume');
      
      if (bgmEnabled !== null) document.getElementById('bgm').checked = bgmEnabled === 'true';
      if (effectsEnabled !== null) document.getElementById('effects').checked = effectsEnabled === 'true';
      if (bgmVolume !== null) {
        document.getElementById('bgmVolume').value = bgmVolume;
        document.getElementById('bgmVolumeOutput').innerText = bgmVolume;
      }
      if (effectsVolume !== null) {
        document.getElementById('effectsVolume').value = effectsVolume;
        document.getElementById('effectsVolumeOutput').innerText = effectsVolume;
      }
    });
    
    function Home() {
      window.location.href = 'home';
    }
  </script>
</body>
</html>