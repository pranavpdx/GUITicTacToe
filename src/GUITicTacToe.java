
/*
 * This is a GUI version of our original TicTacToe program with some modifications
 * Author: Pranav Sharma
 * Date: 9/21/18
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUITicTacToe implements ActionListener {
	// Creates the actual frame that user plays the game on
	JFrame frame = new JFrame("Pranav's GUI TicTacToe Game");
	// creates empty array for buttons to later fill
	JButton button[][] = new JButton[3][3];
	// List of other variables and JButtons or JTextFields to later be used in the
	// program
	Container center = new Container();
	String player1 = "X";
	int xwins = 0;
	String player2 = "O";
	int owins = 0;
	JLabel playerx = new JLabel(player1 + " wins: " + xwins);
	JLabel playero = new JLabel(player2 + " wins: " + owins);
	Container north = new Container();
	int[][] board = new int[3][3];
	final int BLANK = 0;
	final int X_MOVE = 1;
	final int O_MOVE = 2;
	final int X_TURN = 0;
	final int O_TURN = 1;
	int turn = X_TURN;
	JButton xChangeName = new JButton("Change Player X's Name");
	JButton oChangeName = new JButton("Change Player O's Name");
	JTextField xChangeField = new JTextField();
	JTextField oChangeField = new JTextField();
	JLabel result = new JLabel(player1 + "'s Turn!");
	boolean clicked = false;
	JButton playAgain = new JButton("PLAY AGAIN?");
	Container south = new Container();
	int combo = 0;

	// This constructor sets the layouts for all the buttons in the JFrame
	public GUITicTacToe() {
		frame.setSize(400, 400);
		// sets layout of frame
		frame.setLayout(new BorderLayout());

		// sets layout of the center of the frame
		center.setLayout(new GridLayout(3, 3));

		// creates the array of buttons in the frame
		for (int row = 0; row < button.length; row++) {
			for (int colomn = 0; colomn < button[0].length; colomn++) {
				button[row][colomn] = new JButton("");
				center.add(button[row][colomn]);
				button[row][colomn].addActionListener(this);
			}
		}
		frame.add(center, BorderLayout.CENTER);

		// sets layout of the north part of frame
		north.setLayout(new GridLayout(3, 2));
		north.add(playerx);
		north.add(playero);
		north.add(xChangeName);
		xChangeName.addActionListener(this);
		north.add(oChangeName);
		oChangeName.addActionListener(this);
		north.add(xChangeField);
		north.add(oChangeField);
		frame.add(north, BorderLayout.NORTH);

		// sets layout of the south part of the frame
		south.setLayout(new GridLayout(1, 2));
		result.setFont(new Font("Serif", Font.BOLD, 30));
		south.add(result);
		playAgain.addActionListener(this);
		playAgain.setVisible(false);
		south.add(playAgain);
		frame.add(south, BorderLayout.SOUTH);

		// lets frame close and makes it visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	// start of the code that runs the constructor
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GUITicTacToe();
	}

	// implemented method that executes the operations when any button is clicked
	public void actionPerformed(ActionEvent event) {
		// only runs code if someone hasn't won
		if (clicked == false) {
			JButton current = new JButton();
			boolean grid = false;
			// if user has clicked a button on the TicTacToe board then checks if its blank
			// and puts an X or an O, also checks for any wins
			for (int row = 0; row < button.length; row++) {
				for (int colomn = 0; colomn < button[0].length; colomn++) {
					if (event.getSource().equals(button[row][colomn])) {
						current = button[row][colomn];
						// puts an X or an O on the board
						if (board[row][colomn] == BLANK) {
							if (turn == X_TURN) {
								current.setText("X");
								board[row][colomn] = X_MOVE;
								turn = O_TURN;
							} else {
								current.setText("O");
								turn = X_TURN;
								board[row][colomn] = O_MOVE;
							}
							if (turn == X_TURN) {
								result.setText(player1 + "'s turn!");
								result.setFont(new Font("Serif", Font.BOLD, 30));
							} else if (turn == O_TURN) {
								result.setText(player2 + "'s turn!");
								result.setFont(new Font("Serif", Font.BOLD, 30));
							}
						}

						grid = true;
						// after each click checks if anyone has one and if so sets the color of the
						// winning squares to green. Else if a tie then says tie
						if (checkWin(X_MOVE) == true) {
							result.setText(player1 + " wins!");
							result.setFont(new Font("Serif", Font.BOLD, 30));
							xwins++;
							playerx.setText(player1 + "'s wins: " + xwins);
							clicked = true;
							setColor(combo);
							playAgain.setVisible(true);
						} else if (checkWin(O_MOVE) == true) {
							result.setText(player2 + " wins!");
							result.setFont(new Font("Serif", Font.BOLD, 30));
							owins++;
							playero.setText(player2 + "'s wins: " + owins);
							clicked = true;
							playAgain.setVisible(true);
							setColor(combo);
						} else if (checkTie() == true) {
							result.setText("Its a tie!");
							result.setFont(new Font("Serif", Font.BOLD, 30));
							clicked = true;
							playAgain.setVisible(true);
							
						}
					}
				}
			}
			// runs code if button is not a TicTacToe board button. Changes the players
			// names when asked to
			if (grid == false) {
				if (event.getSource().equals(xChangeName)) {
					if(!xChangeField.getText().equals("")) {
						player1 = xChangeField.getText();
						playerx.setText(player1 + "'s wins: " + xwins);
					}
				} else if (event.getSource().equals(oChangeName)) {
					
					if(!oChangeField.getText().equals("")) {
						player2 = oChangeField.getText();
						playero.setText(player2 + "'s wins: " + owins);
					}
				}

			} else {

			}
		} else {
			// if a user has won then asked if they want to play again and all other buttons
			// freeze. If user clicks playAgain button then the board resets and displays
			// who's turn it is.
			if (event.getSource().equals(playAgain)) {
				if (checkWin(X_MOVE)) {
					result.setText(player2 + "'s Turn!");
				} else if (checkWin(O_MOVE)) {
					result.setText(player1 + "'s Turn!");
				}else {
					result.setText(player1 + "'s Turn!");
				}
				for (int row = 0; row < button.length; row++) {
					for (int col = 0; col < button[0].length; col++) {
						button[row][col].setText("");
						button[row][col].setBackground(new JButton().getBackground());
						board[row][col] = BLANK;
					}
				}
				clicked = false;
				playAgain.setVisible(false);
			}
		}
	}

	// method to check if a player has won the game
	public boolean checkWin(int player) {
		if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
			combo = 1;
			return true;
		}
		if (board[1][0] == player && board[1][1] == player && board[1][2] == player) {
			combo = 2;
			return true;
		}
		if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
			combo = 3;
			return true;
		}
		if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
			combo = 4;
			return true;
		}
		if (board[0][1] == player && board[1][1] == player && board[2][1] == player) {
			combo = 5;
			return true;
		}
		if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
			combo = 6;
			return true;
		}
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
			combo = 7;
			return true;
		}
		if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
			combo = 8;
			return true;
		} else {
			return false;
		}
	}

	// checks to see if the game results in a tie
	public boolean checkTie() {
		int x = 0;
		for (int row = 0; row < board.length; row++) {
			for (int colomn = 0; colomn < board[0].length; colomn++) {
				if (board[row][colomn] != BLANK) {
					x = x + 1;
				}
			}
		}
		if (x == 9) {
			return true;
		} else {
			return false;
		}

	}

	// if a player has won, then sets the winning buttons as green
	public void setColor(int combo) {
		if (combo == 1) {
			button[0][0].setBackground(Color.GREEN);
			button[0][1].setBackground(Color.GREEN);
			button[0][2].setBackground(Color.GREEN);
		} else if (combo == 2) {
			button[1][0].setBackground(Color.GREEN);
			button[1][1].setBackground(Color.GREEN);
			button[1][2].setBackground(Color.GREEN);

		} else if (combo == 3) {
			button[2][0].setBackground(Color.GREEN);
			button[2][1].setBackground(Color.GREEN);
			button[2][2].setBackground(Color.GREEN);

		} else if (combo == 4) {
			button[0][0].setBackground(Color.GREEN);
			button[1][0].setBackground(Color.GREEN);
			button[2][0].setBackground(Color.GREEN);

		} else if (combo == 5) {
			button[0][1].setBackground(Color.GREEN);
			button[1][1].setBackground(Color.GREEN);
			button[2][1].setBackground(Color.GREEN);
		} else if (combo == 6) {
			button[0][2].setBackground(Color.GREEN);
			button[1][2].setBackground(Color.GREEN);
			button[2][2].setBackground(Color.GREEN);
		} else if (combo == 7) {
			button[0][0].setBackground(Color.GREEN);
			button[1][1].setBackground(Color.GREEN);
			button[2][2].setBackground(Color.GREEN);
		} else if (combo == 8) {
			button[0][2].setBackground(Color.GREEN);
			button[1][1].setBackground(Color.GREEN);
			button[2][0].setBackground(Color.GREEN);
		}
	}

}