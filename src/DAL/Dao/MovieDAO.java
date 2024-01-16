package DAL.Dao;

import BE.Movie;
import DAL.DatabaseConnector;
import DAL.IMovieDataAccess;
import utility.PMCException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDataAccess {
    private DatabaseConnector dbConnector;

    public MovieDAO() throws PMCException{
        dbConnector = new DatabaseConnector();
    }

    @Override
    public List<Movie> getAll() throws PMCException {
        ArrayList<Movie> allMovies = new ArrayList<>();

        try(Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Movie;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                Double rating = rs.getDouble("Rating");
                String filelink = rs.getString("Filelink");
                String lastview = rs.getString("Lastview");
                int tmdbId = rs.getInt("TMDBid");


                Movie movie = new Movie(id, tmdbId, name, rating, filelink, lastview);
                allMovies.add(movie);
            }
            return allMovies;
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new PMCException("Could not get movies from database", sqlEx);
        }
    }

    @Override
    public Movie create(Movie movie) throws PMCException {
        String sql = "INSERT INTO dbo.Movie (Name, Rating, Filelink, Lastview, TMDBid) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getRating());
            stmt.setString(3, movie.getFilelink());
            stmt.setString(4, movie.getLastview());
            stmt.setInt(5,movie.getTMDBId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }

            Movie newMovie = new Movie(id, movie.getTMDBId(), movie.getName(), movie.getRating(),
                                        movie.getFilelink(), movie.getLastview());

            return newMovie;
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new PMCException("Could not create movie", sqlEx);
        }
    }

    @Override
    public void update(Movie movie) throws PMCException {
        String sql = "UPDATE dbo.Movie SET Name = ?, Rating = ?, Filelink = ?, Lastview = ? WHERE id = ?;";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getRating());
            stmt.setString(3, movie.getFilelink());
            stmt.setString(4, movie.getLastview());
            stmt.setInt(5, movie.getId());

            stmt.executeUpdate();
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new PMCException("Could not update movie", sqlEx);
        }
    }

    @Override
    public void delete(Movie movie) throws PMCException {
        String sql = "DELETE FROM dbo.Movie WHERE id = ?;";
        try(Connection conn = dbConnector.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,movie.getId());
            stmt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new PMCException("Could not delete movie", ex);
        }
    }

    @Override
    public List<Movie> getByCatId(int catId) throws PMCException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT Movie.* FROM dbo.Movie " + "JOIN CatMovie ON Movie.Id = CatMovie.MovieId " + "WHERE CatMovie.CategoryId = ?;";
        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, catId);

            try (ResultSet rs = stmt.executeQuery())
            {
                while(rs.next())
                {
                    int id = rs.getInt("Id");
                    String name = rs.getString("Name");
                    double rating = rs.getDouble("Rating");
                    String filelink = rs.getString("Filelink");
                    String lastview = rs.getString("Lastview");
                    int tmdbId = rs.getInt("TMDBid");

                    Movie movie = new Movie(id, tmdbId, name, rating, filelink, lastview);
                    movies.add(movie);
                }
            }
        }
        catch(SQLException ex)
        {
            throw new PMCException("Could not get Movie, by specific Category ID", ex);
        }
        return movies;
    }

    @Override
    public Movie getById(int movieId) throws PMCException {
        String sql = "SELECT * FROM dbo.Movie WHERE id = ?;";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            Movie mov = null;

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                double rating = rs.getDouble("Rating");
                String filelink = rs.getString("Filelink");
                String lastview = rs.getString("Lastview");
                int tmdbId = rs.getInt("TMDBid");

                mov = new Movie(id, tmdbId, name, rating, filelink, lastview);
            }
            return mov;
        }
        catch (SQLException ex)
        {
            throw new PMCException("Could not get specific movie based on ID", ex);
        }
    }
}
