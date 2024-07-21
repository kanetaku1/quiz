<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/index.css">
    <title>クイズ</title>
</head>
<body>
    <div class="text-container">
        <p class="text">
            <span>ク</span>
            <span>イ</span>
            <span>ズ</span>
            <span>を</span>
            <span>始</span>
            <span>め</span>
            <span>よ</span>
            <span>う</span>
            <span>！</span>
        </p>
    </div>
    <div class="title">
        <h2>早押しクイズ  ボタンを押してスタート</h2>
    </div>
    <a class="start-btn" href="User">
        <img src="resources/スクリーンショット 2024-04-28 1.04.03.png" alt="スタートボタン">
    </a>
    <img class="girl" src="resources/手が届く-removebg-preview.png" alt="女の子">
    
    <script>
        function initAudioSettings() {
            if (localStorage.getItem('settingsInitialized') !== 'true') {
                // デフォルト設定
                localStorage.setItem('bgmEnabled', 'true');
                localStorage.setItem('effectsEnabled', 'true');
                localStorage.setItem('bgmVolume', '50');
                localStorage.setItem('effectsVolume', '50');
                localStorage.setItem('settingsInitialized', 'true');
            }
        }

        // ページロード時に初期化を実行
        window.addEventListener('load', initAudioSettings);
    </script>
</body>
</html>