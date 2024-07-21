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

public class GetGenre {
  public List<String> getDataFromAPI() throws IOException {
    List<String> genreList = new ArrayList<>();
    // APIからデータを取得する処理
    String host = "localhost";
    @SuppressWarnings("deprecation")
    URL url = new URL("http://" + host + "/minhaya/genre_get.php");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    // レスポンスを取得
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuilder responseBuilder = new StringBuilder();
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      responseBuilder.append(inputLine);
    }
    in.close();
    // JSONデータを解析
    JSONArray jsonArray = new JSONArray(responseBuilder.toString());
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      String genre = jsonObject.getString("genre");
      genreList.add(genre);
    }
    return genreList;
  }
}
