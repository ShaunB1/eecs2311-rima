package presentation;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import businessLogic.UserActivity;

import java.awt.Font;
import java.awt.Window;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import java.awt.Container;
import objects.User;

import java.awt.BorderLayout;
import java.awt.Component;

/**
 * 
 */
@SuppressWarnings("serial")
public class ViewProfile extends JFrame {

	//Container and Panel objects.
	private Container contentPane;
	private Container editProfileContentPane;
	private JPanel infoPane;
	private JPanel buttonPane;
	
	//Component objects.
	private final JButton backButton = new JButton("Back");
	private final JButton editProfileButton = new JButton("Edit Profile");
	private JLabel displName = new JLabel();
	private JLabel allergyInfo =  new JLabel();
	private final JButton btnNewButton = new JButton("Survey");
	private final JButton surveyButton = new JButton("Survey");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Create a new frame.
					ViewProfile frame = new ViewProfile();
					//Make the frame visible.
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Adds the current user's info (user name and allergies) to the user info label.
	 */
	public void displayUserInfo() {
		User currentUser = UserActivity.getCurrentUser();		
		if(currentUser != null) {
			displName.setText(currentUser.getName());
			Hashtable<String,Integer> userAllergies = currentUser.getUserAllergies().getAllergies();
			ArrayList<String> allergyNames = currentUser.getUserAllergies().getAllergyNames();
			String allergies="";
			for(String allergy : allergyNames) {
				if(userAllergies.get(allergy).intValue() == 1) {
					allergies+=allergy+",";
				}
			}
			allergyInfo.setText("<html>Allergies<br>"+"\n"+allergies);
		}else {
			displName.setText("Error: User not logged in.");
		}
		
	}
	
	
	/**
	 * Create the frame.
	 */
	public ViewProfile() {
		setTitle("RIMA - User Profile");
		//Set the application to exit when closed.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		
		//Set the size and pop up location of the window.
		setSize(450, 450);	
		setLocationRelativeTo(null);
		//Get content pane.
		contentPane = getContentPane();		
		
		//Create a new info pane.
		infoPane = new JPanel();
		//Set the info pane's layout manager to the vertical box layout.
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.PAGE_AXIS));
		//Make an invisible border for the info pane.
		infoPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		//Add user info to labels.
		displayUserInfo();	
		
		//Align labels.
		displName.setAlignmentX(CENTER_ALIGNMENT);
		allergyInfo.setAlignmentX(CENTER_ALIGNMENT);		
		
		//Add labels to the info pane.
		infoPane.add(displName);
		infoPane.add(Box.createRigidArea(new Dimension(0, 10)));
		infoPane.add(allergyInfo);
				
		//Create a new pane for buttons.
		buttonPane = new JPanel();
		//Set an invisible border for the button pane.
		buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//Set the button pane's layout manager to the horizontal box layout.
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		
		//Add the button and info panes to the content pane.
		contentPane.add(infoPane);
		contentPane.add(buttonPane);
		
		//Set up the button fonts.
		editProfileButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		buttonPane.add(surveyButton);
		surveyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDietaryRestrictionsSurvey survey = new UserDietaryRestrictionsSurvey();
				
			}
		});		
		
		//Add buttons to button pane.
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(backButton);
		buttonPane.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPane.add(editProfileButton);
		
		
		//Add info and button panes to content pane.
		contentPane.add(infoPane);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);
		
		//Set up what to do when the back button is pressed.
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		//Create a HomePage window
		HomePage homePage = new HomePage();
				
		//Make the HomePage window visible and the UserRecipeCollection window invisible.
		homePage.setVisible(true);
		contentPane.setVisible(false);
				
		//Close the UserRecipeCollection Window.
		Window win = SwingUtilities.getWindowAncestor(contentPane);
		win.dispose();				
			}
		});
		
		//Set up what to do when the back button is pressed.
		editProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showEditProfileView();
				
				//Make the HomePage window visible and the UserRecipeCollection window invisible.
				//editProfileView.setVisible(true);
				//contentPane.setVisible(false);
				
				//Close the UserRecipeCollection Window.
				//Window win = SwingUtilities.getWindowAncestor(contentPane);
				//win.dispose();				
			}
		});				

	}

	private void showEditProfileView() {
		
		EditProfileView editProfileView = new EditProfileView();
		
		editProfileContentPane = editProfileView.getContentPane();
		
		setContentPane(editProfileContentPane);
		validate();
	}
}
