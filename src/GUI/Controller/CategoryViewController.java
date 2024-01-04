package GUI.Controller;

import GUI.Model.CatModel;
import GUI.Model.MovieModel;

public class CategoryViewController {
    MovieModel movieModel;

    public CategoryViewController(){
        try {
            movieModel = MovieModel.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
