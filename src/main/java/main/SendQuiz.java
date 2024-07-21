package main;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SendQuiz {
  public void SendData(String imagePath, String genre, String question, String answer){
    try {
      String host = "localhost";
      @SuppressWarnings("deprecation")
      URL url = new URL("http://" + host + "/minhaya/quiz_post.php");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
      // 送信するデータを作成
      String postData = "imagePath=" + imagePath + "&genre=" + genre + "&question=" + question + "&answer=" + answer;
      byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
      // データを送信
      try (OutputStream os = connection.getOutputStream()) {
        os.write(postDataBytes);
          // レスポンスを取得
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
          System.out.println("Data sent successfully.");
        } else {
          System.out.println("Failed to send data. Response code: " + responseCode);
        }   
      }
      connection.disconnect();
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}

