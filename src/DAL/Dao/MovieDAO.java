package DAL.Dao;

import BE.Movie;
import DAL.DatabaseConnector;
import DAL.IMovieDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDataAccess {
    private DatabaseConnector dbConnector;

    public MovieDAO() throws Exception{
        dbConnector = new DatabaseConnector();
    }

    @Override
    public List<Movie> getAll() throws Exception {
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


                Movie movie = new Movie(id, name, rating, filelink, lastview);
                allMovies.add(movie);
            }
            return allMovies;
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new Exception("Could not get movies from database", sqlEx);
        }
    }

    @Override
    public Movie create(Movie movie) throws Exception {
        String sql = "INSERT INTO dbo.Movie (Name, Rating, Filelink, Lastview) VALUES (?, ?, ?, ?)";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, movie.getName());
            stmt.setDouble(2, movie.getRating());
            stmt.setString(3, movie.getFilelink());
            stmt.setString(4, movie.getLastview());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }

            Movie newMovie = new Movie(id, movie.getName(), movie.getRating(),
                                        movie.getFilelink(), movie.getLastview());

            return newMovie;
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new Exception("Could not create movie", sqlEx);
        }
    }

    @Override
    public void update(Movie movie) throws Exception {
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
            throw new Exception("Could not update movie", sqlEx);
        }
    }

    @Override
    public void delete(Movie movie) throws Exception {
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
            throw new Exception("Could not delete movie", ex);
        }
    }

    //TODO INSERT METHOD
    @Override
    public List<Movie> getByCatId(int catId) throws Exception {
        return null;
    }

    @Override
    public Movie getById(int movieId) throws Exception {
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

                mov = new Movie(id, name, rating, filelink, lastview);
            }
            return mov;
        }
    }
}
