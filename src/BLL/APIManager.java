package BLL;

import BE.Movie;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class APIManager {

    public List<Movie> getMovies(String string) {
        JsonObject jsonResoponse = JsonParser.parseString(string).getAsJsonObject();
        JsonArray resultsArray = jsonResoponse.getAsJsonArray("results");

        List<Movie> movies = new ArrayList<>();

        for(int i = 0; i < resultsArray.size(); i++){
            JsonObject movieJson = resultsArray.get(i).getAsJsonObject();

            String name = movieJson.get("title").getAsString();
            String year = movieJson.get("release_date").getAsString();
            double rating = movieJson.get("vote_average").getAsDouble();

            Movie movie = new Movie(-1, name, rating, "test.mp4", year);
            movies.add(movie);
        }
        return movies;
    }

    public String search(String query) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/search/movie?query=" + query.replace(" ", "%20") + "&include_adult=false&language=en-US&page=1"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYTgwZjA2MmFhNzViNWUzOGU4M2U2M2QwMDBkOGZkZSIsInN1YiI6IjY1YTUxMjQ2MWEzMjQ4MDEyZjA0ODUxYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vt7OhyBXRi7txZOUqnh8d7sBurgQsoa9HN69goTkfG0")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
