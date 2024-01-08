package DAL.Dao;

import BE.CatMovie;
import BE.Movie;
import DAL.DatabaseConnector;
import DAL.ICatMovieDataAccess;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatMovieDAO implements ICatMovieDataAccess {

    private DatabaseConnector dbConnector;

    public CatMovieDAO() throws Exception{
        dbConnector = new DatabaseConnector();
    }

    @Override
    public List<CatMovie> getAll() throws Exception {
        ArrayList<CatMovie> allCatMovies = new ArrayList<>();

        try(Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.CatMovie;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                int catId = rs.getInt("CategoryId");
                int movieId = rs.getInt("MovieId");

                CatMovie catMovie = new CatMovie(catId, movieId);
                allCatMovies.add(catMovie);
            }
            return allCatMovies;
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new Exception("Could not get CatMovies from database", sqlEx);
        }
    }

    public int getMoviesCountForCategory(int categoryId) throws Exception
    {
        String sql = "SELECT COUNT(*) FROM dbo.CatMovie WHERE CategoryId = ?";
        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                return rs.getInt(1);
            }

            return 0;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get moviecount");
        }
    }

    @Override
    public CatMovie create(CatMovie catMovie) throws SQLServerException {
        String sql = "INSERT INTO dbo.CatMovie (CategoryId, MovieId) VALUES (?,?);";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
        {
            stmt.setInt(1, catMovie.getCategoryId());
            stmt.setInt(2, catMovie.getMovieId());

            stmt.executeUpdate();

            CatMovie newCatMovie = new CatMovie(catMovie.getCategoryId(), catMovie.getMovieId());

            return newCatMovie;

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CatMovie catMovie) throws Exception {
        String sql = "UPDATE dbo.CatMovie SET CategoryId = ?, MovieId = ? WHERE id = ?;";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, catMovie.getCategoryId());
            stmt.setInt(2, catMovie.getMovieId());

            stmt.executeUpdate();
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new Exception("Could not update catMovie", sqlEx);
        }
    }

    public void delete(CatMovie catMovie) throws Exception
    {
        String sql = "DELETE FROM dbo.PlaylistSongs WHERE CategoryId = ? AND MovieId = ?;";

        try(Connection conn = dbConnector.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, catMovie.getCategoryId());
            stmt.setInt(2, catMovie.getMovieId());

            stmt.executeUpdate();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete CatMovie", ex);
        }
    }

    //UNSURE IF WE NEED THIS OR NOT - FOR NOW ITS NULL
    @Override
    public CatMovie getById(int catMovieId) throws Exception {
        return null;
    }

    @Override
    public List<Movie> getMovieByCatId(int catId) throws Exception {
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

                    Movie movie = new Movie(id, name, rating, filelink, lastview);
                    movies.add(movie);
                }
            }
        }
        return movies;
    }
}
