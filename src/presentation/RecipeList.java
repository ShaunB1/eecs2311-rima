package presentation;

import java.util.ArrayList;

import java.awt.Font;
import java.awt.Window;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import businessLogic.UserActivity;
import objects.Recipes;
import persistence.DAO;
import persistence.RecipesStubDB;
import persistence.UsersDAO;
import persistence.DatabaseAccess;
import javax.swing.JTextField;
import javax.swing.JComboBox;

/**
 * 
 */
@SuppressWarnings("serial")
public class RecipeList extends JFrame {

	//Content pane object
	private JPanel contentPane;
	//List section object
	private JList<String> list;
	//Button object
	private final JButton btnBack = new JButton("Back");
	private JTextField searchField;
	String searchCategory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeList frame = new RecipeList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Adds the recipes in the app's database to the list section to display them
	 */
	public void addRecipes() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		DatabaseAccess access = new DatabaseAccess();
		DAO<Recipes> db = access.recipesDB();
		//DAO<Recipes> db = new RecipesStubDB();
		ArrayList<Recipes> recipes = db.getAll();
		for(Recipes r: recipes) {
			model.addElement(r.getName());
		}
		list.setModel(model);
	}
	public void searchUserRecipe(String searchedItem) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		DatabaseAccess access = new DatabaseAccess();
		DAO<Recipes> db = access.recipesDB();
		ArrayList<Recipes> recipes = new ArrayList<Recipes>(); 
		recipes = db.getAll();
		for(Recipes r: recipes) {
			if(searchCategory.equals("Name") && r.getName().equals(searchedItem)) {
				model.addElement(r.getName());
			}else if(searchCategory.equals("Ingredient") && r.getIngredients().contains(searchedItem)) {
				model.addElement(r.getName());
			}
		}
		list.setModel(model);
	}
	/**
	 * Create the frame.
	 */
	public RecipeList() {
		setTitle("RIMA - Recipes List");
		//Sets the application to exit when closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Sest the bounds of the window
		setSize(1280, 720);
		setLocationRelativeTo(null);
		//Creates a new content pane
		contentPane = new JPanel();
		contentPane.setBounds(0, 0, 1280, 720);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//Replaces the frame's content pane with the one that was just set up.
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Creates a new section for an item list
		list= new JList<String>();
		list.setBackground(new Color(255, 255, 255));
		list.setBounds(30, 61, 651, 486);
		//Adds the recipes in the app's database to the list section to display them
		addRecipes();
		//Sets up what to do when an item in the list is selected. When selected the recipe's detailed information is displayed.
		list.getSelectionModel().addListSelectionListener(e-> {
			String name = (String) list.getSelectedValue();
			ViewRecipeDB newWindow = new ViewRecipeDB(name);
			newWindow.NewScreen(name);
			contentPane.setVisible(false);
			Window win = SwingUtilities.getWindowAncestor(contentPane);
			win.dispose();
		});
		contentPane.add(list);
		//Creates a back button. When clicked, user is redirected to the main page.
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setBounds(549, 558, 132, 29);
		contentPane.add(btnBack);
		
		searchField = new JTextField();
		searchField.setBounds(202, 11, 232, 39);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<String> model = new DefaultListModel<String>();
				list.setModel(model);
				searchUserRecipe(searchField.getText());
				
			}
		});
		btnNewButton.setBounds(444, 11, 89, 39);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("All Recipes");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(543, 11, 138, 39);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRecipes();
			}
		});
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Search By:");
		comboBox.addItem("Name");
		comboBox.addItem("Category");
		comboBox.addItem("Ingredient");
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					searchCategory = comboBox.getSelectedItem().toString();
				}
			}
		});
		
		comboBox.setBounds(30, 10, 162, 40);
		contentPane.add(comboBox);
		
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(UserActivity.getCurrentUser() == null) {
					Main.frame.setVisible(true);
				}else {
					HomePage homePage = new HomePage();
					homePage.setVisible(true);
				}
				contentPane.setVisible(false);
				Window win = SwingUtilities.getWindowAncestor(contentPane);
				win.dispose();
				
			}
		});
		
	}
}
