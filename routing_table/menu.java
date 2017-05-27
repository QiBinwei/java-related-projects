package BinweiQi;

import java.util.Scanner;

public class menu {
	private String[] options;

    public menu(String[] options){
        this.options = new String[options.length];
        for(int i=0; i<options.length; i++){
            this.options[i] = options[i];
        }//initiate every element of the Options array.
    }

    public void displayMenu(){
        for(int i=0; i<options.length; i++){
            System.out.println((i+1) + " - " + options[i]);
        }//using for loop to show every line of the option array
    }

    public int getChoice(Scanner input){
        System.out.println("Please enter your choice: ");
        int choice = input.nextInt();
        return choice;//get the user's choice from scanner and return the choice for future use
    }

}
