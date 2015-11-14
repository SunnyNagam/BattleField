package main;

import javax.swing.JFrame;

public class BoardScreen {

public static void main(String[] args){
		
		JFrame window = new JFrame("BattleField");				// jframe widow
		
		window.setContentPane(new Board());						// Content of window
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		window.setResizable(false);								// un-expandable
		window.pack();
		window.setVisible(true);				
	}

}
