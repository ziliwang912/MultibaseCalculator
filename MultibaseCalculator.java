import javax.swing.JFrame;

/**
 * <h1>CICS 3120 Project One</h1>
 * This GUI multi-base calculator can do addition, subtraction, multiplication, and division 
 * with any base between 2 and 16. The slider lets user select the base the calculator is 
 * currently using. When the user uses the slider to select a different base, the calculator 
 * responds immediately: the numbers in the calculator's display area will be represented in
 * the new base, and the digit buttons will reflect the new base.
 * 
 * @author Zili Wang
 * @since 04-08-2017
 * 
 * <h2>Continued Project</h2>
 * There are potential bugs in this app. This is the continued develepment for this project.
 * @author Zili Wang
 * @since 12-27-2019
 * 
 */

public class MultibaseCalculator {

	/**
	 * Starts program.
	 * 
	 * @param args Contains the supplied command-line arguments as an array of String objects.
	 */
	public static void main(String[] args) {	
		createAndShowGUI();
	} 

	/**
	 * Creates and shows GUI for the calculator.
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Multibase Calculator");
		frame.add(new CalculatorPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 605);
		frame.setResizable(false);	// Makes the size of frame fixed.
		frame.setVisible(true);
	}
	
}