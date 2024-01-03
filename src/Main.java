import BE.CatMovie;
import BE.Category;
import BE.Movie;
import GUI.Model.CatModel;
import GUI.Model.CatMovieModel;
import GUI.Model.MovieModel;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        //TEST ITEMS TO EASILY TEST IT ALL.
        MovieModel movieModel = MovieModel.getInstance();
        CatModel catModel = CatModel.getInstance();
        CatMovieModel catMovieModel = CatMovieModel.getInstance();
        List<Movie> movies = movieModel.getAll();
        List<Category> cats = catModel.getAll();
        List<CatMovie> catMovie = catMovieModel.getAll();
    }
}
