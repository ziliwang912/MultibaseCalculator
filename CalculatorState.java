import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class stores and updates state of the calculator.
 */
public class CalculatorState {
	private int currBase; // Value of current base
	private int value; 	// Value of current calculation
	private ArrayList<String> operands = new ArrayList<String>();
	private ArrayList<String> operator = new ArrayList<String>();
	private int operand1, operand2;

	/**
	 * Creates a CalculatorState and initializes the value of calculation
	 */
	public CalculatorState() { 
		value = 0; 
	}
	
	/**
	 * Get the value of current base
	 */
	public int getBase() {
		return this.currBase;
	}

	/**
	 * Sets state of calculator to default values.
	 */
	public void clear(int base) { 
		value = 0; 
		operands.clear();
		operator.clear();
		operand1 = operand2 = 0;
		this.currBase = base;
	}
	
	/**
	 * Breaks an expression string into parts and adds them respectively 
	 * into operator ArrayList and operands ArrayList.
	 * 
	 * @param expression An expression string to be handled.
	 */
	private void preHandle(String expression) {
		String operators = "+-*/";   // All possible operators
		
		// The four operators are delimiters for separating tokens
		StringTokenizer st = new StringTokenizer(expression, operators, true); 
		
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			
			if (operators.contains(token)) {
				operator.add(token); 
			} else {
				operands.add(token);  // None operators are considered as operands
			} 
		} 			
	} 
	
	/**
	 * Returns an expression after changing operands in it to their new base.
	 * 
	 * @param expression An expression to be updated.
	 * @param base The base of operands in the expression.
	 * @param newBase The desired base.
	 */
	public String updateBase(String expression, int base, int newBase) {
		preHandle(expression);
		
		currBase = base;

		/* 
		  If the expression is empty, then no update needed;
		  If there is no operator, then there is only one operand needed to update;
		  If there are two operands, then the full expression needs to be updated;
		  The only one possible case left is that the expression has one operand plus an operator.
		 */
		if (expression.isEmpty()){
			return "";
		} else if (operator.isEmpty()) {
			operand1 = Integer.parseInt(operands.get(0), currBase); 
			expression = Integer.toString(operand1, newBase);			
		} else if (operands.size() == 1) {			
			operand1 = Integer.parseInt(operands.get(0), currBase);
			expression = Integer.toString(operand1, newBase) + operator.get(0);			
		} else {	
			operand1 = Integer.parseInt(operands.get(0), currBase);
			operand2 = Integer.parseInt(operands.get(1), currBase);
			expression = Integer.toString(operand1, newBase) + operator.get(0) 
										  + Integer.toString(operand2,newBase);	
		} 
		
		return expression.toUpperCase(); // Makes sure that result is in upper case 
	}

	/**
	 * Returns computed result and writes history into file.
	 * 
	 * @param expression An expression to be computed.
	 * @param base The base of operands in the expression.
	 * 
	 * @throws ArithmeticException If an integer "divide by zero" .
	 * @throws IOException If the named file exists but is a directory rather than a regular file, 
	 *                     does not exist but cannot be created, or cannot be opened for any other reason.
	 */
	public String getResult(String expression, int base) throws ArithmeticException, IOException {
		preHandle(expression);

		currBase = base;
		operand1 = Integer.parseInt(operands.get(0), base);
		operand2 = Integer.parseInt(operands.get(1), base);

		switch(operator.get(0)) {
			case "+": 
				value = operand1 + operand2;
				break;			
			case "-": 
				value = operand1 - operand2;
				break;				
			case "*": 			
				value = operand1 * operand2;
				break;				
			case "/": 
				value = operand1 / operand2;
				break;		
		}
		
		String result = Integer.toString(value,currBase).toUpperCase();
		writeHistory(expression + " = " + result + " <Base " + currBase + ">");
		
        return result;       
	}
	
	/**
	 * Writes a string into file.
	 * 
	 * @param history A string to be written.
	 * 
	 * @throws IOException If the named file exists but is a directory rather than a regular file, 
	 *                     does not exist but cannot be created, or cannot be opened for any other reason.
     */
	private void writeHistory(String history) throws IOException {
		FileWriter writer = new FileWriter("History.txt", true);
		
		writer.write(history);
		writer.write(System.getProperty("line.separator"));  // Add a new line
		writer.close(); 
	}  
}