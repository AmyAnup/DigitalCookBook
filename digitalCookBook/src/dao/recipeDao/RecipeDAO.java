package dao.recipeDao;

import dao.dbConnection.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Recipe;
/*
 * Author: @AmyAnup
 * Date: 13/10/25
 *
 * RecipeDAO.java
 * Simple DAO class to manage recipes in the database.
 * Supports: Add, Read (All), Update, Delete recipes.
 */


public class RecipeDAO {

    // ✅ Add a new recipe
    public void addRecipe(Recipe recipe) throws ClassNotFoundException {
        String sql = "INSERT INTO recipe (title, ingredients, instructions, category, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, recipe.getTitle());
            stmt.setString(2, recipe.getIngredients());
            stmt.setString(3, recipe.getInstructions());
            stmt.setString(4, recipe.getCategory());
            stmt.setInt(5, recipe.getUser_id());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                recipe.setRecipe_Id(rs.getInt(1)); // ✅ fixed setter name
            }

            System.out.println("✅ Recipe added successfully! ID: " + recipe.getRecipe_Id());

        } catch (SQLException e) {
            System.err.println("❌ Error adding recipe: " + e.getMessage());
        }
    }

    // ✅ Retrieve all recipes for a specific user
    public List<Recipe> getAllRecipes(int userId) throws ClassNotFoundException {
        List<Recipe> list = new ArrayList<>();
        String sql = "SELECT * FROM recipe WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Recipe r = new Recipe(
                        rs.getInt("recipe_id"), // or rs.getInt("recipe_id") depending on your table
                        rs.getString("title"),
                        rs.getString("ingredients"),
                        rs.getString("instructions"),
                        rs.getString("category"),
                        rs.getInt("user_id"), // ✅ corrected from String to int
                        rs.getTimestamp("created_at")
                );
                list.add(r);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching recipes: " + e.getMessage());
        }

        return list;
    }
    
    public Recipe getRecipeById(int recipeId) throws ClassNotFoundException {
        String sql = "SELECT * FROM recipe WHERE recipe_id = ?";
        Recipe recipe = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                recipe = new Recipe(
                        rs.getInt("recipe_id"),
                        rs.getString("title"),
                        rs.getString("ingredients"),
                        rs.getString("instructions"),
                        rs.getString("category"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("created_at")
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching recipe by ID: " + e.getMessage());
        }

        return recipe;
    }


    // ✅ Update a recipe
    public void updateRecipe(Recipe recipe) throws ClassNotFoundException {
        String sql = "UPDATE recipe SET title=?, ingredients=?, instructions=?, category=? WHERE recipe_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipe.getTitle());
            stmt.setString(2, recipe.getIngredients());
            stmt.setString(3, recipe.getInstructions());
            stmt.setString(4, recipe.getCategory());
            stmt.setInt(5, recipe.getRecipe_Id());

            int rows = stmt.executeUpdate();
            System.out.println("✅ " + rows + " recipe(s) updated.");

        } catch (SQLException e) {
            System.err.println("❌ Error updating recipe: " + e.getMessage());
        }
    }

    // ✅ Delete a recipe by ID
    public void deleteRecipe(int id) throws ClassNotFoundException {
        String sql = "DELETE FROM recipe WHERE recipe_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println("🗑️ " + rows + " recipe(s) deleted.");

        } catch (SQLException e) {
            System.err.println("❌ Error deleting recipe: " + e.getMessage());
        }
    }
}
