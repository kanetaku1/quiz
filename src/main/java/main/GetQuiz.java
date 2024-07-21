package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetQuiz{
  public QuizList quizList;
  
  public void getQuizData(String genre){
    try {
      // PHPファイルのURL
      String host = "localhost";
      @SuppressWarnings("deprecation")
      URL url = new URL("http://" + host + "/minhaya/quiz_get.php?genre=" + genre);
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
    quizList = new QuizList();
    JSONArray jsonArray = new JSONArray(response.toString());
    for (int i = 0; i < jsonArray.length(); i++){
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      String imagePath = jsonObject.getString("image_path");
      String question = jsonObject.getString("question");
      String answer = jsonObject.getString("answer");
      this.quizList.setQuizData(imagePath, question, answer);
    }
  }
}
