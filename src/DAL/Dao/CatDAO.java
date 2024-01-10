package DAL.Dao;

import BE.Category;
import DAL.DatabaseConnector;
import DAL.ICatDataAccess;
import utility.PMCException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatDAO implements ICatDataAccess
{
    private DatabaseConnector dbConnector;

    public CatDAO() throws PMCException{
        dbConnector = new DatabaseConnector();
    }

    @Override
    public List<Category> getAll() throws PMCException {
        ArrayList<Category> allCategories = new ArrayList<>();

        try(Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM dbo.Category;";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next())
            {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");

                Category cat = new Category(id, name);
                allCategories.add(cat);
            }
            return allCategories;
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            //sæt logging ind her
            throw new PMCException("Could not get categories from database", sqlEx);
        }
    }

    @Override
    public Category create(Category category) throws PMCException {
        String sql = "INSERT INTO dbo.Category (Name) VALUES (?)";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, category.getName());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }

            Category cat = new Category(id, category.getName());

            return cat;
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new PMCException("Could not create category", sqlEx);
        }
    }

    @Override
    public void update(Category category) throws PMCException {
        String sql = "UPDATE dbo.Category SET Name = ? WHERE id = ?;";

        try(Connection conn = dbConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());

            stmt.executeUpdate();
        }

        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            throw new PMCException("Could not create category", sqlEx);
        }
    }

    @Override
    public void delete(Category category) throws PMCException {
        String sql = "DELETE FROM dbo.Category WHERE id = ?;";
        try(Connection conn = dbConnector.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,category.getId());
            stmt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new PMCException("Could not delete category", ex);
        }
    }

    @Override
    public Category getById(int catId) throws PMCException {
        String sql = "SELECT * FROM dbo.Category WHERE id = ?;";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, catId);
            ResultSet rs = stmt.executeQuery();

            Category cat = null;

            while (rs.next()) {
                int id = rs.getInt("Id");
                String title = rs.getString("Name");

                cat = new Category(id, title);
            }
            return cat;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            throw new PMCException("Could not get specific category based on ID", ex);
        }
    }
}
