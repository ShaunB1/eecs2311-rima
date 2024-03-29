package persistence;

import java.util.ArrayList;

import objects.Ingredient;
import objects.Recipes;
import objects.User;

/**
 *  The interface DAO specifies additional methods for managing users, 
 *  recipes, ingredients, and other relating data.
 */
public interface UsersDAO extends DAO<User> {
	ArrayList<Recipes> getRecipes(User u);
	void addRecipes(User u,Recipes r);
	void removeRecipes(User u, Recipes r);
	Recipes getRecipe(User u, String name);
	ArrayList<Ingredient> getIngredients(User u);
	Ingredient getIngredient(User u, String name);
	void edit(String oldName, String name, String Password); 
	void editAllergy(User u, String allergyType, int change);
	public ArrayList<Recipes> getFavoriteList(User u);
	public void editFavorites(Recipes r,int change);
	public void editPlanner(User u,String day,String time, String recipe);
	public void editShoppingList(String ingredient, String name);
	boolean updateIngredients(User user, ArrayList<Ingredient> lst);
}
