package com.example.FullStackProjectUserService.service;

import com.example.FullStackProjectUserService.model.Album;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.stream.Collectors;

public class SpotyfyAPI {
    public static String getAccessToken(){
        String clientId = "08b4373dd22344cf9f95e0b50485fce1";
        String clientSecret = "abcb62df980d474fb92de99abf3ea540";
        String authString = clientId + ":" + clientSecret;
        String base64Auth = Base64.getEncoder().encodeToString(authString.getBytes());

        try {
            // Create a connection to the Spotify API
            URL tokenUrl = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) tokenUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + base64Auth);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            // Send a request to get the access token
            String requestBody = "grant_type=client_credentials";
            connection.getOutputStream().write(requestBody.getBytes());

            // Get the response
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response manually
                String responseBody = response.toString();
                int start = responseBody.indexOf("access_token\":\"") + 15;
                int end = responseBody.indexOf("\"", start);
                String accessToken = responseBody.substring(start, end);
                //System.out.println("Токен доступа: " + accessToken);
                return accessToken;
            } else {
                System.err.println("Ошибка при получении токена доступа. Код ответа: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Faileddd to add music";
    }

    public static Album getAlbumInfo(String trackId){
        String accessToken = getAccessToken();

        // Замените на ID песни, для которой вы хотите получить название альбома

        Album album = new Album();

        try {
            // Создайте URL для запроса к Spotify API
            URL url = new URL("https://api.spotify.com/v1/tracks/" + trackId);

            // Откройте соединение с API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            // Проверьте, что ответ от сервера успешный
            if (connection.getResponseCode() == 200) {
                // Прочитайте ответ от сервера
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String jsonResponse = reader.lines().collect(Collectors.joining());

                // Преобразуйте ответ в JSON объект
                JSONObject jsonObject = new JSONObject(jsonResponse);
                album.setAlbumId(null);
                album.setSpotifyId(jsonObject.getJSONObject("album").getString("id"));
                album.setTitle(jsonObject.getJSONObject("album").getString("name"));

                JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("album").getJSONArray("artists"));
                JSONObject artistObject = jsonArray.getJSONObject(0);
                album.setArtist(artistObject.getString("name"));

                album.setRelease_date(jsonObject.getJSONObject("album").getString("release_date"));

                jsonArray = new JSONArray(jsonObject.getJSONObject("album").getJSONArray("images"));
                artistObject = jsonArray.getJSONObject(0);
                album.setImage_url(artistObject.getString("url"));
                return album;
            } else {
                System.out.println("Ошибка при выполнении запроса: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
            // Закройте соединение
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return album;
    }
}
