package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetQuiz {

  public List<String> imagePaths = new ArrayList<>();
  public List<String> questions = new ArrayList<>();
  public List<String> answers = new ArrayList<>();
  
  public void getQuizData(String genre){
    try {
      // PHPファイルのURL
      URL url = new URL("http://localhost/minhaya/quiz_get.php?genre=" + genre);
      // HTTPリクエストの作成
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      // レスポンスを取得
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
      }
      in.close();

      // JSON解析
      JsonAnalyzer(response);

    } catch (IOException e){
      e.printStackTrace();
    } 
  }

  private void JsonAnalyzer(StringBuilder response){
    System.out.println(response.toString());
    JSONArray jsonArray = new JSONArray(response.toString());
    for (int i = 0; i < jsonArray.length(); i++){
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      String imagePath = jsonObject.getString("image_path");
      String question = jsonObject.getString("question");
      String answer = jsonObject.getString("answer");

      imagePaths.add(imagePath);
      questions.add(question);
      answers.add(answer);

      // ここで取得したデータを利用して何かを行う
    }
  }
}
