import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;


/**
 * This class displays GUI and handles controlling of the calculator.
 */
public class CalculatorPanel extends JPanel {
	
	/**
	 * The serializable class CalculatorPanel does not declare a static final 
	 * serialVersionUID field of type long
	 */
	private static final long serialVersionUID = 1L;

	private final CalculatorState calc = new CalculatorState();

	private final JTextArea display = new JTextArea(1, 9);

	private final JSlider baseSlider = new JSlider(2, 16, 16);
	private int base = baseSlider.getValue();
	private int newBase;

	private static int totalButtonNumber = 22; // There are total 22 buttons in this calculator
	private static String[] buttonNames = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
			"F", "+", "-", "*", "/", "=", "Clear" };
	private final JButton[] buttons = new JButton[totalButtonNumber];

	private static Font displayFont = new Font("Calibri Light", Font.PLAIN, 60);
	private static Font sliderFont = new Font("Arial", Font.PLAIN, 20);
	private static Font buttonFont = new Font("Arial", Font.PLAIN, 30);

	/**
	 * Constructs a calculator panel to hold all GUI components.
	 */
	public CalculatorPanel() {
		this.setLayout(new FlowLayout());

		add(display);
		display.setFont(displayFont);
		display.setEditable(false);

		// Adds and sets slider
		add(baseSlider);
		baseSlider.setPreferredSize(new Dimension(400, 80));
		baseSlider.setPaintTicks(true);
		baseSlider.setPaintLabels(true);
		baseSlider.setLabelTable(baseSlider.createStandardLabels(2));
		baseSlider.setMajorTickSpacing(2);
		baseSlider.setMinorTickSpacing(1);
		baseSlider.setFont(sliderFont);

		// Adds slidre action listener
		baseSlider.addChangeListener(new sliderListener());

		// Adds buttons and button action listener
		for (int i = 0; i < totalButtonNumber; i++) {
			buttons[i] = new JButton();
			add(buttons[i]);
			buttons[i].setText(buttonNames[i]);
			buttons[i].setFont(buttonFont);

			// Sets special sizes for "=" and "Clear" buttons
			if (i >= 20) {
				buttons[i].setPreferredSize(new Dimension(205, 60));
			} else {
				buttons[i].setPreferredSize(new Dimension(100, 60));
			}

			buttons[i].addActionListener(new buttonListenner());
		}
	}

	/**
	 * Listens to slider change events and responses accordingly.
	 */
	private class sliderListener implements ChangeListener {
		/**
		 * Overrides stateChanged method to handle slider change events.
		 * 
		 * @param ce Change event
		 */
		@Override
		public void stateChanged(final ChangeEvent ce) {
			// Casts ChangeEvent source to JSlider source
			final JSlider source = (JSlider) ce.getSource();

			// Waits until user selection finished, then updates the value of slider
			if (!source.getValueIsAdjusting()) {
				newBase = source.getValue();

				for (int i = 0; i < newBase; i++) {
					buttons[i].setEnabled(true);
				}

				// Disables buttons beyond the current base range
				for (int j = newBase; j < 16; j++) {
					buttons[j].setEnabled(false);
				}

				calc.clear();
			
				final String expression = display.getText();

				display.setText(calc.updateBase(expression, base, newBase));

				// Sets "base" to current base.
				base = newBase;
			}
		}
	}

	/**
	 * Listens to button action events and responses accordingly.
	 */
	private class buttonListenner implements ActionListener {

		/**
		 * Overrides actionPerformed method to handle button click actions.
		 * 
		 * @param ae Action event
		 */
		@Override
		public void actionPerformed(final ActionEvent ae) {
			for (int i = 0; i < 20; i++) {
				if (ae.getSource() == buttons[i]) {
					display.append(buttonNames[i]);
				}
			}

			// If "=" is clicked, puts computed result on display
			if (ae.getSource() == buttons[20]) {
				final String expression = display.getText();
				base = baseSlider.getValue();
				calc.clear();

				// Handles exceptions by printing messages on display
				try {
					display.setText(calc.getResult(expression, base));
				} catch (final ArithmeticException e) {
					display.setText("Devide by Zero");
				} catch (final IOException e) {
					display.setText("I/O Error");
				}
			} 
			
			// If "Clear" is clicked, clears all contend on display
			if(ae.getSource() == buttons[21]){
				display.setText("");
				calc.clear();			
			}			
		} 
	} 
}