package BinweiQi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {

	public static void main(String[] args) {
		
		String inputFormat = "input/instance%02d";
		String outputFormat = "output_greedy/greedy_solution%02d";
		
		new File("output_greedy").mkdir();
		
		
		for (int i = 1; i < 100; i++) {
			String input = String.format(inputFormat, i);
			String output = String.format(outputFormat, i);
			
	    	SeparatingPoints s = new SeparatingPoints();
				
			
			try {
				s.load(input);
			} catch (FileNotFoundException e1) {
				continue;
			}
			
			s.solve();
			
			
			PrintStream out;
			try { 
				out = new PrintStream(new FileOutputStream(output));
				System.setOut(out);
			} catch (Exception e) {
				e.printStackTrace();
			}

			s.printSolution();	
		}
	}
}
