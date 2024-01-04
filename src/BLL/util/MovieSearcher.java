package BLL.util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {

    public List<Movie> search(List<Movie> searchBase, String query)
    {
        List<Movie> searchResult = new ArrayList<>();

        for(Movie movie : searchBase)
        {
            if(compareToMovieName(query, movie) || compareToRating(query, movie))
            {
                searchResult.add(movie);
            }
        }
        return searchResult;
    }

    private boolean compareToMovieName(String query, Movie movie)
    {
        return movie.getName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToRating(String query, Movie movie)
    {
        double queryAsNumber = Double.parseDouble(query);

        if(queryAsNumber == movie.getRating() || queryAsNumber < movie.getRating())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
