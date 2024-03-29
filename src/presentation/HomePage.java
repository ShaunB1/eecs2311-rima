package presentation;

import javax.swing.DefaultListModel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingConstants;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businessLogic.UserActivity;
import objects.User;
import persistence.DatabaseAccess;
import persistence.UsersDAO;
import objects.Ingredient;
import objects.Planner;
import objects.Recipes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.time.LocalDate;

import javax.swing.JTextArea;
import javax.swing.JList;

import javax.swing.JScrollPane;

import javax.swing.ScrollPaneConstants;

/**
 * 
 */
@SuppressWarnings("serial")
public class HomePage extends JFrame {

	// Current HomePage instance for passing to instance of next frame in
	// ActionListener to make next frame's back button work.
	private final HomePage homePage = this;

	// Panel objects.
	private JPanel breakfast;
	private JPanel lunch;
	private JPanel dinner;
	private JPanel favourites;
	private JPanel planner;
	private JPanel expirations;

	private JFrame frame;
	private JLabel label;

	// Button objects.
	JButton logoutButton = new JButton("Log Out");
	JButton userRecipesButton = new JButton("My Collection");
	JButton ingredientsButton = new JButton("My Ingredients");
	JButton newRecipesButton = new JButton("Find New Recipes");
	JButton viewProfileButton = new JButton("View Profile");
	JButton shoppingListButton = new JButton("Shopping List");

	// Label objects.
	JLabel welcomeLabel = new JLabel("");
	private JButton mealPlannerButton = new JButton("Meal Planner");
	private JComboBox<String> comboBox;
	private static JList<String> list;
	static DatabaseAccess access = new DatabaseAccess();
	static UsersDAO db = access.usersDB();
	static DefaultListModel<String> model = new DefaultListModel<String>();
	DefaultListModel<String> modelBreakfast = new DefaultListModel<String>();
	DefaultListModel<String> modelLunch = new DefaultListModel<String>();
	DefaultListModel<String> modelDinner = new DefaultListModel<String>();

	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTextArea lunchText;
	private JScrollPane scrollPane_2;
	private JTextArea dinnerText;
	private JScrollPane scrollPane_3;
	private JList<String> listBreakfast;
	private JList<String> listLunch;
	private JList<String> listDinner;
	DefaultListModel<String> modelLst = new DefaultListModel<String>();
	DefaultListModel<String> expModel = new DefaultListModel<String>();
	static DefaultListModel<String> shoppingModel = new DefaultListModel<String>();

	// JList list = new JList();
	HashMap<String, Planner> p = UserActivity.currentUser.getWeekPlanner();
	private JScrollPane scrollPane_4;
	private JList<String> list_1 = new JList<String>();
	private JScrollPane scrollPane_5;
	private JScrollPane scrollPane_6;
	private JScrollPane scrollPane_7;
	private JScrollPane scrollPane_8;
	private static JList<String> listShopping;

	public static void favouriteRecipes() {
		model.clear();
		ArrayList<Recipes> recipes = new ArrayList<Recipes>();
		recipes = db.getFavoriteList(UserActivity.getCurrentUser());
		// Add all the user's favourite recipes to the list model.
		for (Recipes r : recipes) {
			model.addElement(r.getName());
		}

		// Set the model for the list section to be the one that was
		list.setModel(model);
	}

	public static void removeFavourite(Recipes r) {
		model.removeElement(r.getName());
	}

	public void addBreakfastRecipes(String day) {
		modelBreakfast.clear();
		ArrayList<String> recipes = new ArrayList<String>();
		recipes = UserActivity.currentUser.getWeekPlanner().get(day).breakfast;

		for (String r : recipes) {
			modelBreakfast.addElement(r);
		}

		// Set the model for the list section to be the one that was
		listBreakfast.setModel(modelBreakfast);
	}

	public void addLunchRecipes(String day) {
		modelLunch.clear();
		ArrayList<String> recipes = new ArrayList<String>();
		recipes = UserActivity.currentUser.getWeekPlanner().get(day).lunch;

		for (String r : recipes) {
			modelLunch.addElement(r);
		}

		// Set the model for the list section to be the one that was
		listLunch.setModel(modelLunch);
	}

	public void addDinnerRecipes(String day) {
		modelDinner.clear();
		ArrayList<String> recipes = new ArrayList<String>();
		recipes = UserActivity.currentUser.getWeekPlanner().get(day).dinner;
		for (String r : recipes) {
			modelDinner.addElement(r);
		}
		// Set the model for the list section to be the one that was
		listDinner.setModel(modelDinner);
	}

	/**
	 * Create the frame.
	 */
	public HomePage() {

		frame = this;
		setTitle(("RIMA - Welcome!"));
		setResizable(false);

		// Background
		label = new JLabel();
		label.setLocation(0, 0);
		label.setSize(1264, 681);

		// Shopping List Button
		shoppingListButton = new JButton("Shopping List");
		shoppingListButton.setForeground(new Color(255, 255, 255));
		shoppingListButton.setBackground(new Color(59, 89, 182));

		shoppingListButton.setBounds(50, 10, 160, 23);
		label.add(shoppingListButton);

		shoppingListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserShoppingList shoppingList = new UserShoppingList();
				shoppingList.setVisible(true);
			}
		});

		// Logout Button
		logoutButton = new JButton("Logout");
		logoutButton.setForeground(new Color(255, 255, 255));
		logoutButton.setBackground(new Color(59, 89, 182));
		logoutButton.setBounds(1005, 653, 160, 23);
		label.add(logoutButton);

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.frame.setVisible(true);
				UserActivity.getCurrentUser().loggedIn = false;
				UserActivity.setCurrentUser(null);
				frame.setVisible(false);
				frame.dispose();
			}
		});

		// User Recipes Button
		userRecipesButton = new JButton("My Collection");
		userRecipesButton.setForeground(new Color(255, 255, 255));
		userRecipesButton.setBackground(new Color(59, 89, 182));

		userRecipesButton.setBounds(250, 10, 160, 23);
		label.add(userRecipesButton);

		userRecipesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecipeCollection.page = 0;
				RecipeCollection collection = new RecipeCollection();
				collection.setVisible(true);
			}
		});

		// Ingredients Button
		ingredientsButton = new JButton("My Ingredients");
		ingredientsButton.setForeground(new Color(255, 255, 255));
		ingredientsButton.setBackground(new Color(59, 89, 182));
		ingredientsButton.setBounds(450, 10, 160, 23);
		label.add(ingredientsButton);

		ingredientsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IngredientsListView ingredients = new IngredientsListView(homePage);
				ingredients.setVisible(true);
			}
		});

		// New Recipes Button
		newRecipesButton = new JButton("Find New Recipes");
		newRecipesButton.setForeground(new Color(255, 255, 255));
		newRecipesButton.setBackground(new Color(59, 89, 182));
		newRecipesButton.setBounds(650, 10, 160, 23);
		label.add(newRecipesButton);

		newRecipesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecipeCollection.page = 1;
				RecipeCollection list = new RecipeCollection();
				list.setVisible(true);

			}
		});

		// View Profile Button
		viewProfileButton = new JButton("View Profile");
		viewProfileButton.setForeground(new Color(255, 255, 255));
		viewProfileButton.setBackground(new Color(59, 89, 182));
		viewProfileButton.setBounds(850, 10, 160, 23);
		label.add(viewProfileButton);

		viewProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewProfile profile = new ViewProfile();
				profile.setVisible(true);
			}
		});

		// Meal Planner Button
		mealPlannerButton = new JButton("Meal Planner");
		mealPlannerButton.setForeground(new Color(255, 255, 255));
		mealPlannerButton.setBackground(new Color(59, 89, 182));
		mealPlannerButton.setBounds(1050, 10, 160, 23);
		label.add(mealPlannerButton);

		mealPlannerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMealPlanner planner = new UserMealPlanner();
				planner.frame.setVisible(true);
				frame.dispose();
			}
		});

		// Breakfast Panel
		breakfast = new JPanel();
		breakfast.setLayout(null);
		JLabel breakfastLabel1 = new JLabel("Breakfast");
		breakfastLabel1.setBounds(116, 5, 99, 14);
		breakfast.add(breakfastLabel1);
		breakfast.setBounds(40, 45, 280, 400);
		breakfast.setBackground(Color.WHITE);

		label.add(breakfast);

		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(23, 154, 231, 235);
		breakfast.add(scrollPane);

		JTextArea breakfastText = new JTextArea();
		scrollPane.setViewportView(breakfastText);
		breakfastText.setLineWrap(true);

		scrollPane_5 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_5.setBounds(23, 30, 231, 106);
		breakfast.add(scrollPane_5);

		listBreakfast = new JList<String>();
		scrollPane_5.setViewportView(listBreakfast);
		listBreakfast.getSelectionModel().addListSelectionListener(e -> {
			// Get the selected list item
			String name = (String) listBreakfast.getSelectedValue();
			Recipes r = db.getRecipe(UserActivity.currentUser, name);
			if (r != null) {
				breakfastText.setText(r.toString());
			} else if (name != null) {
				breakfastText.setText(name + " is not avialable in your collection!");
			}
		});
		// Lunch Panel
		lunch = new JPanel();
		lunch.setLayout(null);
		JLabel lunchLabel1 = new JLabel("Lunch");
		lunchLabel1.setBounds(125, 5, 79, 14);
		lunch.add(lunchLabel1);
		lunch.setBounds(340, 45, 280, 400);
		lunch.setBackground(Color.WHITE);

		label.add(lunch);

		scrollPane_1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 154, 260, 235);
		lunch.add(scrollPane_1);
		lunchText = new JTextArea();
		scrollPane_1.setViewportView(lunchText);
		lunchText.setLineWrap(true);

		scrollPane_6 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_6.setBounds(10, 30, 260, 106);
		lunch.add(scrollPane_6);

		listLunch = new JList<String>();
		scrollPane_6.setViewportView(listLunch);
		listLunch.getSelectionModel().addListSelectionListener(e -> {
			// Get the selected list item
			String name = (String) listLunch.getSelectedValue();
			Recipes r = db.getRecipe(UserActivity.currentUser, name);
			if (r != null) {
				lunchText.setText(r.toString());
			} else if (name != null) {
				lunchText.setText(name + " is not avialable in your collection!");
			}
		});

		// Dinner Panel
		dinner = new JPanel();
		dinner.setLayout(null);
		JLabel dinnerLabel1 = new JLabel("Dinner");
		dinnerLabel1.setBounds(123, 5, 63, 14);
		dinner.add(dinnerLabel1);
		dinner.setBounds(640, 45, 280, 400);
		dinner.setBackground(Color.WHITE);

		label.add(dinner);

		scrollPane_2 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(10, 154, 260, 235);
		dinner.add(scrollPane_2);

		dinnerText = new JTextArea();
		scrollPane_2.setViewportView(dinnerText);
		dinnerText.setLineWrap(true);

		scrollPane_7 = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_7.setBounds(10, 30, 260, 103);
		dinner.add(scrollPane_7);

		listDinner = new JList<String>();
		scrollPane_7.setViewportView(listDinner);
		listDinner.getSelectionModel().addListSelectionListener(e -> {
			// Get the selected list item
			String name = (String) listDinner.getSelectedValue();
			Recipes r = db.getRecipe(UserActivity.currentUser, name);
			if (r != null) {
				dinnerText.setText(r.toString());
			} else if (name != null) {
				dinnerText.setText(name + " is not avialable in your collection!");
			}
		});
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);

		// Favourites Panel
		favourites = new JPanel();
		favourites.setLayout(null);
		JButton btnNewButton = new JButton("Back to List");
		btnNewButton.setBounds(137, 366, 133, 23);
		btnNewButton.setVisible(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setVisible(false);
				textArea.setVisible(false);
				list.setVisible(true);
				favouriteRecipes();
			}
		});
		favourites.add(btnNewButton);

		list = new JList<String>();
		list.setBounds(10, 29, 260, 360);
		favourites.add(list);
		list.getSelectionModel().addListSelectionListener(e -> {
			// Get the selected list item
			String name = (String) list.getSelectedValue();
			// Create a ViewRecipe window for the selected list item/recipe.
			Recipes r = db.getRecipe(UserActivity.currentUser, name);
			if (r != null) {
				model.clear();
				list.setVisible(false);
				scrollPane_3.setVisible(true);
				textArea.setVisible(true);
				textArea.setText(r.toString());
				btnNewButton.setVisible(true);
			}
		});
		JLabel favouritesLabel1 = new JLabel("Favourites");
		favouritesLabel1.setBounds(114, 5, 118, 14);
		favourites.add(favouritesLabel1);
		favourites.setBounds(940, 45, 280, 400);
		favourites.setBackground(Color.WHITE);

		label.add(favourites);

		scrollPane_3 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setBounds(23, 43, 230, 320);
		favourites.add(scrollPane_3);

		scrollPane_3.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setVisible(false);
		scrollPane_3.setVisible(false);

		// Expiration Dates Panel
		// Panel setup.
		expirations = new JPanel();
		expirations.setLayout(null);
		expirations.setBounds(940, 456, 280, 190);
		expirations.setBackground(Color.WHITE);

		// Label setup
		JLabel expLabel = new JLabel("Expired Ingredients");
		int expWidth = 115;
		expLabel.setBounds(expirations.getWidth() / 2 - expWidth / 2, 7, expWidth, 16);
		expirations.add(expLabel);

		// List setup
		JList<String> expList = new JList<String>(expModel);
		expirationListSetup();

		// Scroll pane setup
		JScrollPane expScrollPane = new JScrollPane(expList);
		int scrollBorder = 9;
		expScrollPane.setBounds(scrollBorder, expLabel.getY() + expLabel.getHeight() + scrollBorder,
				expirations.getWidth() - scrollBorder * 2,
				expirations.getHeight() - expLabel.getHeight() - scrollBorder * 3);
		expirations.add(expScrollPane);

		frame.getContentPane().add(expirations);
		frame.validate();

		// Set up what to do when an item in the list is selected.
		expList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {

					if (expList.isSelectionEmpty())
						return;

					User user = UserActivity.getCurrentUser();
					if (user == null)
						return;

					ArrayList<Ingredient> ingredients = user.getIngredients();
					if (ingredients == null)
						return;

					String selectedValue = (String) expList.getSelectedValue();
					for (Ingredient ingredient : ingredients) {
						if (ingredient.getName().equals(selectedValue)) {
							JComponent[] components = new JComponent[] { expList };
							IngredientView ingredientView = new IngredientView(components, ingredient);
							expList.clearSelection();
							expList.setEnabled(false);
							ingredientView.setVisible(true);
							return;
						}
					}
				}
			}
		});

		// Setup
		frame.getContentPane().setBackground(new Color(143, 188, 143));
		getContentPane().setLayout(null);
		// Meal Planner Panel
		planner = new JPanel();
		getContentPane().add(planner);
		planner.setLayout(null);
		JLabel plannerLabel1 = new JLabel("Select  Day To View Recipe Details");
		plannerLabel1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		plannerLabel1.setBounds(10, 11, 174, 25);
		planner.add(plannerLabel1);
		planner.setBounds(40, 456, 468, 190);
		planner.setBackground(Color.WHITE);

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				breakfastText.setText(null);
				lunchText.setText(null);
				dinnerText.setText(null);

				String day = comboBox.getSelectedItem().toString();
				addBreakfastRecipes(day);
				addLunchRecipes(day);
				addDinnerRecipes(day);
			}
		});
		comboBox.setBounds(23, 47, 136, 22);

		planner.add(comboBox);

		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(194, 11, 250, 168);
		planner.add(scrollPane_4);
		scrollPane_4.setViewportView(list_1);
		frame.getContentPane().add(label);

		String WhichDayofWeek[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		for (String d : WhichDayofWeek) {
			comboBox.addItem(d);
		}

		JPanel shoppingListPanel = new JPanel();
		shoppingListPanel.setBackground(new Color(255, 255, 255));
		shoppingListPanel.setBounds(518, 456, 400, 190);
		getContentPane().add(shoppingListPanel);
		shoppingListPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Shopping List");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(86, 11, 215, 14);
		shoppingListPanel.add(lblNewLabel);

		scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(10, 33, 380, 146);
		shoppingListPanel.add(scrollPane_8);

		listShopping = new JList<String>();
		scrollPane_8.setViewportView(listShopping);

		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		favouriteRecipes();
		showPlan();
		shoppingLstSetUp();

	}

	public void expirationListSetup() {
		User user = UserActivity.getCurrentUser();
		if (user == null)
			return;

		ArrayList<Ingredient> ingredients = user.getIngredients();
		if (ingredients == null)
			return;

		expModel.removeAllElements();
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getExpiration().isBefore(LocalDate.now())) {
				expModel.addElement(ingredient.getName());
			}
		}
	}

	public static void shoppingLstSetUp() {
		shoppingModel.clear();
		for (String n : UserActivity.currentUser.getShoppingList()) {
			shoppingModel.addElement(n);
		}
		listShopping.setModel(shoppingModel);
	}

	public void showPlan() {
		modelLst.clear();
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		for (String d : days) {
			modelLst.addElement(d);
			modelLst.addElement("        " + "Breakfast:");
			for (String b : p.get(d).breakfast) {
				modelLst.addElement("             " + b);

			}
			modelLst.addElement("        " + "Lunch:");
			for (String b : p.get(d).lunch) {
				modelLst.addElement("             " + b);

			}
			modelLst.addElement("        " + "Dinner:");
			for (String b : p.get(d).dinner) {
				modelLst.addElement("             " + b);

			}
		}
		list_1.setModel(modelLst);
	}
}
