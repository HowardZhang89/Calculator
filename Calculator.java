package Calculator;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

// Created by Howard Zhang June 9th, 2020

/* * * * * * * * * * * * * * * * * *
 * 
 *  I created this Calculator to practice working with GUI and action listeners, and to show prospective employers
 *  my thought process and coding style. 
 *  
 *  My design decisions focus on logical encapsulation, DRY principles, and most importantly, readability. 
 *  My hope is that a programmer should be able to follow along my code and be able to understand everything 
 *  I do and how everything fits together to create this simple Calculator. 
 *  
 *  I am aware there a possibilities to reduce code length more elegant by using loops to create the buttons. 
 *  However, they add complexity by making the code take longer to read and debug without improving processing speed. 
 *  They also remove the ability to reference the buttons directly, which could be useful if changes need to be made 
 *  in the future. 
 *  
 */

public class Calculator {

	private static JFrame frame;
	private static JTextField display;
	private static String operator = null;
	private static Double value1, value2, result;
	private static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bDecimal;
	private static JButton bAC, bPosNeg, bPercent, bAdd, bMinus, bMult, bDiv, bEquals;
	
	private static boolean calculating;
	private static boolean newNumberFlag;
	
	private static final int buttonWidth = 60;
	private static final int buttonHeight = 55;
	static Dimension buttonDimension = new Dimension( Calculator.buttonWidth, Calculator.buttonHeight);
	
	/**
	 * Create the application.
	 */
	public Calculator() {
		
		Calculator.value1 = null;
		Calculator.value2 = null;
		Calculator.result = null;
		Calculator.calculating = false;
		Calculator.newNumberFlag = true;
		
		initialize();
	}
	
	
	static void calculate() {
		
		if(Calculator.value1 == null || Calculator.value2 == null) {
			System.out.println("Error: One of the operands is null.");
			return;
		}
		
		switch (Calculator.operator) {
		case "+":
			Calculator.result = Calculator.value1 + Calculator.value2;
			break;
		case "-":
			Calculator.result = Calculator.value1 - Calculator.value2;
			break;
		case "*":
			Calculator.result = Calculator.value1 * Calculator.value2;
			break;
		case "/":
			Calculator.result = Calculator.value1 / Calculator.value2; 
			break;
		}
		return;
	}
	
	static void handleDecimal() {
		String displayText = Calculator.display.getText();
		
		if(displayText.contains(".")) {
			return;
		}
		if(Calculator.newNumberFlag) {
			Calculator.display.setText("0.");
			Calculator.newNumberFlag = false;
		}
		else {
			Calculator.display.setText(displayText + ".");
		}
	}
	
	static void handleNumber(JButton b) {
		String buttonText = b.getText();
		String displayText = Calculator.display.getText();
		
		if(Calculator.newNumberFlag) {
			Calculator.display.setText(buttonText);
			Calculator.newNumberFlag = false;
		}
		else {
			Calculator.display.setText(displayText + buttonText);
		}
		
	}
	
	static void handleOperator(JButton b) {	
		
		// Handle chain operators where user does not press equals in between
		if(Calculator.calculating) {
			Calculator.value2 = Double.parseDouble(Calculator.display.getText());
			calculate();
			Calculator.display.setText(Calculator.result.toString());
			Calculator.value1 = Calculator.result;
			System.out.println("Result: " + Calculator.result + " Chained Operator.");
			System.out.println("Operand 1 updated.");
			
		}
		
		// Continue with normal operator function
		Calculator.operator = b.getText();
		Calculator.calculating = true;
		Calculator.newNumberFlag = true;
		Calculator.value1 = Double.valueOf(Calculator.display.getText());
		System.out.println("Operand 1 = " + Calculator.value1.toString());
		System.out.println("Operator: " + Calculator.operator);
	}
	
	private void initializeBinaryOperators(){
		bAdd = new JButton("+");
		bMinus = new JButton("-");
		bMult = new JButton("*");
		bDiv = new JButton("/");
		bEquals = new JButton("=");
		
		ArrayList<JButton> binaryOperators = new ArrayList<JButton>();
		binaryOperators.add(bAdd);
		binaryOperators.add(bMinus);
		binaryOperators.add(bMult);
		binaryOperators.add(bDiv);
		
		for(JButton b : binaryOperators) {
			b.setPreferredSize(buttonDimension);
			b.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
			b.setForeground(Color.ORANGE);
			// add actionListener for +/-/*/"/"
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					Calculator.handleOperator(b);
				}
			});
		}
		
		// "=" action listener
		bEquals.setPreferredSize(buttonDimension);
		bEquals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				if(Calculator.value1 == null) {
					return;
				}
				if(Calculator.value2 == null && Calculator.newNumberFlag) {
					return;
				}
				
				Calculator.value2 = Double.parseDouble(Calculator.display.getText());	
				System.out.println("Operand 2 = " + Calculator.value2);
				Calculator.calculate();
				System.out.println("Result = " + Calculator.result);
				Calculator.display.setText(Double.toString(Calculator.result));
				Calculator.value1 = Calculator.result;
				Calculator.calculating = false;
				Calculator.operator = null;
				Calculator.newNumberFlag = true;
				System.out.println("Value 1 updated: " + result);
			}
		});
	}
	
	private void initializeUnaryOperators() {
		// make operators
		bAC = new JButton("AC");
		bPosNeg = new JButton("+/-");
		bPercent = new JButton("%");
		
		ArrayList<JButton> unaryOperators = new ArrayList<JButton>();
		unaryOperators.add(bAC);
		unaryOperators.add(bPosNeg);
		unaryOperators.add(bPercent);
		
		for(JButton b : unaryOperators) {
			b.setPreferredSize(buttonDimension);
			b.setFont (new Font("Helvetica Neue", Font.PLAIN, 14));
			b.setForeground(Color.BLUE);
		}
		
		
		// AC action listener
		bAC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calculator.display.setText("0");
				Calculator.value1 = null;
				Calculator.value2 = null;
				Calculator.calculating = false;
			}
		});
		
		// "+/-" action listener
		bPosNeg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Double.parseDouble(Calculator.display.getText()) == 0.0) {
					return;
				}
				else if(Calculator.display.getText().contains("-")) {
					Calculator.display.setText( Calculator.display.getText().substring(1));  // "-" will always be first
				}
				else {
					Calculator.display.setText( "-" + Calculator.display.getText());
				}
				if(Calculator.value2 != null && Calculator.calculating) {
					Calculator.value2 *= -1;
					System.out.println("Operand 2 updated: " + Calculator.value2);
				}
				if(Calculator.value1 != null && !Calculator.calculating) {
					Calculator.value1 *= -1;
					System.out.println("Operand 1 updated: " + Calculator.value1);
				}
			}
			});
			
		// "%" sign action listener
		bPercent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double result = Double.parseDouble(Calculator.display.getText()) / 100;
				Calculator.display.setText(result.toString());
			}
		});
	}
	
	
	private void initializeNumbers() {
		
		List<JButton> Numbers = new ArrayList<JButton>();
		b1 = new JButton("1");  
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		b5 = new JButton("5");
		b6 = new JButton("6");
		b7 = new JButton("7");
		b8 = new JButton("8");
		b9 = new JButton("9");
		b0 = new JButton("0");
		bDecimal = new JButton(".");
		
		
		Numbers.add(b1);
		Numbers.add(b2);
		Numbers.add(b3);
		Numbers.add(b4);
		Numbers.add(b5);
		Numbers.add(b6);
		Numbers.add(b7);
		Numbers.add(b8);
		Numbers.add(b9);
		Numbers.add(b0);
		
		for(JButton b : Numbers) {
			b.setPreferredSize(buttonDimension);
			b.setFont (new Font("Helvetica Neue", Font.PLAIN, 14));
			b.setForeground(Color.BLACK);
			b.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handleNumber(b);
				}
			});
		}
		
		
		// special dimension for 0
		b0.setPreferredSize(new Dimension(Calculator.buttonWidth*2 + 5, Calculator.buttonHeight));
		// special action handler for decimal
		bDecimal.setPreferredSize(buttonDimension);
		bDecimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calculator.handleDecimal();
			}
		});
		
	}
	
	private void initialize() {
		Calculator.frame = new JFrame("Calculator");
		Calculator.frame.setBounds(100,100, Calculator.buttonWidth * 4 + 20, Calculator.buttonHeight * 7);
		Calculator.frame.setResizable(false);
		Calculator.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		// make Display Box
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(0,0, Calculator.buttonWidth * 4, Calculator.buttonHeight);
		Calculator.display = new JTextField("0");
		Calculator.display.setEditable(false);
		Calculator.display.setPreferredSize(new Dimension (Calculator.buttonWidth * 4, Calculator.buttonHeight));
		displayPanel.add(Calculator.display);
		
		initializeNumbers();
		initializeUnaryOperators();
		initializeBinaryOperators();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setBounds(0, 0, Calculator.buttonWidth * 4, Calculator.buttonHeight * 5);
		buttonPanel.add(bAC);
		buttonPanel.add(bPosNeg);
		buttonPanel.add(bPercent);
		buttonPanel.add(bDiv);
		buttonPanel.add(b7);
		buttonPanel.add(b8);
		buttonPanel.add(b9);
		buttonPanel.add(bMult);
		buttonPanel.add(b4);
		buttonPanel.add(b5);
		buttonPanel.add(b6);
		buttonPanel.add(bAdd);
		buttonPanel.add(b1);
		buttonPanel.add(b2);
		buttonPanel.add(b3);
		buttonPanel.add(bMinus);
		buttonPanel.add(b0);
		buttonPanel.add(bDecimal);	
		buttonPanel.add(bEquals);
		
		Calculator.frame.getContentPane().add(displayPanel, BorderLayout.NORTH);
		Calculator.frame.getContentPane().add(buttonPanel);
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculator window = new Calculator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
