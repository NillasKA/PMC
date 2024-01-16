package BLL;

import BE.Category;
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
    /**
     * Used to search for a movie in the TMDB database.
     * @param query - The name of the movie.
     * @return - Returns a long string of movies with a name close to the search query
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * Used to extract the names of the categories the movie belongs to, by using the movie id from TMDB
     * @return - Returns information regarding that movie, including its categories.
     * @throws IOException
     * @throws InterruptedException
     */
    public String getMovieDetails(int movieId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/" + movieId + "?language=en-US"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYTgwZjA2MmFhNzViNWUzOGU4M2U2M2QwMDBkOGZkZSIsInN1YiI6IjY1YTUxMjQ2MWEzMjQ4MDEyZjA0ODUxYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vt7OhyBXRi7txZOUqnh8d7sBurgQsoa9HN69goTkfG0")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public List<Integer> getMovieId(String string) {
        JsonObject jsonResoponse = JsonParser.parseString(string).getAsJsonObject();
        JsonArray resultsArray = jsonResoponse.getAsJsonArray("results");

        List<Integer> movieId = new ArrayList<>();

        for(int i = 0; i < resultsArray.size(); i++){
            JsonObject movieJson = resultsArray.get(i).getAsJsonObject();
            int name = movieJson.get("id").getAsInt();
            movieId.add(name);
        }
        return movieId;
    }

    public List<Movie> getMovies(String string) {
        JsonObject jsonResoponse = JsonParser.parseString(string).getAsJsonObject();
        JsonArray resultsArray = jsonResoponse.getAsJsonArray("results");

        List<Movie> movies = new ArrayList<>();

        for(int i = 0; i < resultsArray.size(); i++){
            JsonObject movieJson = resultsArray.get(i).getAsJsonObject();

            String name = movieJson.get("title").getAsString();
            int tmdbID = movieJson.get("id").getAsInt();
            double rating = movieJson.get("vote_average").getAsDouble();
            String year = movieJson.get("release_date").getAsString();

            Movie movie = new Movie(-1, tmdbID, name, rating, "temp.mp4", year);
            movies.add(movie);
        }
        return movies;
    }

    public List<Category> getCategories (int movieId) throws IOException, InterruptedException {
        String string = getMovieDetails(movieId);
        JsonObject jsonResponse = JsonParser.parseString(string).getAsJsonObject();
        JsonArray resultsArray = jsonResponse.getAsJsonArray("genres");

        List<Category> cats = new ArrayList<>();
        for(int i = 0; i < resultsArray.size(); i++){
            JsonObject catJson = resultsArray.get(i).getAsJsonObject();
            String catName = catJson.get("name").getAsString();

            Category category = new Category(-1, catName);
            cats.add(category);
        }
        return cats;
    }
}
