package presentation;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import businessLogic.UserActivity;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 */
@SuppressWarnings("serial")
public class HomePage extends JFrame {
	
	//Current HomePage instance for passing to instance of next frame in ActionListener to make next frame's back button work.
	private final HomePage homePage = this;
	
	//Panel objects.
	private Container contentPane;
	private Container ingredientsListPane;
	private JPanel logoutPane;
	private JPanel optionsPane;

	
	
	//Button objects.
	JButton logoutButton = new JButton("Log Out");	
	JButton userRecipesButton = new JButton("My Collection");
	JButton ingredientsButton = new JButton("My Ingredients");
	JButton newRecipesButton = new JButton("Find New Recipes");
	JButton viewProfileButton = new JButton("View Profile");
	
	//Label objects.
	JLabel welcomeLabel = new JLabel("");
	private final JButton mealPlannerButton_1 = new JButton("Meal Planner");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomePage() {
		setTitle("RIMA - Home");
		//Set application to exit when closed.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set size and pop up location of the window.
		setSize(1280,720);
		setLocationRelativeTo(null);
		
		//Get and store the content pane.
		contentPane = getContentPane();	
		//contentPane.add(label);
		//Create a new pane for the logout button.
		logoutPane = new JPanel(); 
		logoutPane.setBounds(0, 0, 1264, 33);
		//Set an invisible border for the options pane.
		logoutPane.setBorder(new EmptyBorder(5, 5, 5, 5));		
		//Set the options pane's layout manager to the horizontal box layout.
		logoutPane.setLayout(new BoxLayout(logoutPane, BoxLayout.LINE_AXIS));
	
		//Create a new options pane.
		optionsPane = new JPanel(); 
		optionsPane.setBounds(0, 201, 1264, 480);
		//Set an invisible border for the options pane.
		optionsPane.setBorder(new EmptyBorder(5, 5, 5, 5));		
		//Set the options pane's layout manager to the vertical box layout.
		optionsPane.setLayout(new BoxLayout(optionsPane, BoxLayout.PAGE_AXIS));		
		
		
		//Set up welcome label font and text.		
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		welcomeLabel.setText("Welcome "+ UserActivity.getCurrentUser().getName());
		
		//Centre align components for optionsPane.
		welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
		userRecipesButton.setAlignmentX(CENTER_ALIGNMENT);
		ingredientsButton.setAlignmentX(CENTER_ALIGNMENT);
		newRecipesButton.setAlignmentX(CENTER_ALIGNMENT);
		mealPlannerButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMealPlanner planner = new UserMealPlanner();
				planner.frame.setVisible(true);
				//planner.setVisible(true);
				//planner.setVisible(true);
				contentPane.setVisible(false);
				Window win = SwingUtilities.getWindowAncestor(contentPane);
				win.dispose();
			}
		});
		mealPlannerButton_1.setAlignmentX(CENTER_ALIGNMENT);
		
		//Add logout button to the logout pane.
		logoutPane.add(Box.createHorizontalGlue());
		logoutPane.add(logoutButton);
		optionsPane.add(welcomeLabel);
		optionsPane.add(Box.createRigidArea(new Dimension(0, 30)));		
		optionsPane.add(userRecipesButton);
		optionsPane.add(Box.createRigidArea(new Dimension(0, 20)));
		optionsPane.add(ingredientsButton);
		optionsPane.add(Box.createRigidArea(new Dimension(0, 20)));
		optionsPane.add(newRecipesButton);
		optionsPane.add(Box.createRigidArea(new Dimension(0, 30)));
		
		optionsPane.add(mealPlannerButton_1);
		optionsPane.add(Box.createRigidArea(new Dimension(0, 30)));

		
		//Set user recipes button to redirect to the user's personal recipe collection when pushed.
		userRecipesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserRecipeCollection collection = new UserRecipeCollection();
				collection.setVisible(true);
				contentPane.setVisible(false);
				Window win = SwingUtilities.getWindowAncestor(contentPane);
				win.dispose();
			}
		});
		
		//Set new recipes button to redirect to the app's recipe databse when pushed.
		newRecipesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecipeList listRecipes = new RecipeList();
				listRecipes.setVisible(true);
				contentPane.setVisible(false);
				Window win = SwingUtilities.getWindowAncestor(contentPane);
				win.dispose();
			}
		});
		
		//Set "log out" button to redirect to the main window of the application when pushed.
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserActivity.getCurrentUser().loggedIn = false;
				UserActivity.setCurrentUser(null);
				Main mainPage = new Main();
				mainPage.frame.setVisible(true);
				contentPane.setVisible(false);
				Window win = SwingUtilities.getWindowAncestor(contentPane);
				win.dispose();
			}
		});				
		
		//Set ingredients button to redirect to the user's personal ingredient collection when pushed.
		ingredientsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IngredientsListView ingredientView = new IngredientsListView(homePage, contentPane);
				
				//Save the home page window's content pane.
				ingredientsListPane = ingredientView.getContentPane();
				
				//Set the content pane to be the home page window's content pane.
				setContentPane(ingredientsListPane);
				validate();
			}
		});
		getContentPane().setLayout(null);
		
		//Add components to content pane.
		contentPane.add(logoutPane);
		contentPane.add(optionsPane);
		viewProfileButton.setAlignmentX(CENTER_ALIGNMENT);
		//optionsPane.add(Box.createRigidArea(new Dimension(0, 20)));
		optionsPane.add(viewProfileButton);
		
		//Set view profile button to redirect to the user's profile when pushed.
		viewProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		//Create a Profile window
		ViewProfile viewProfile = new ViewProfile();
				
		//Make the Profile window visible and the HomePage window invisible.
		viewProfile.setVisible(true);
		contentPane.setVisible(false);
				
		//Close the old window.
		Window win = SwingUtilities.getWindowAncestor(contentPane);
		win.dispose();				
			}
		});
		
		//optionsPane.add(mealPlannerButton_1);
	}
}
