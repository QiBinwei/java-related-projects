package BinweiQi;
//this is the main class and it is the input source
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.NumberUpSupported;
import javax.sound.sampled.Line;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

public class Main {
	//create a graph to calculate shortest path
	public static void job(Graph g){

		String[] options = {"Display the topology matrix",
	            "Display the connection table",
	            "Display the shortest path to destination router",
	            "Modify the topology",
	            "Best router for broadcast",                       
	            "Exit"};

	    menu mainMenu = new menu(options);
		int fromNode = -1;
		int toNode = -1;
		boolean done = false;
		Scanner scan = new Scanner(System.in);
		do{
			mainMenu.displayMenu();
			int choice = mainMenu.getChoice(scan);
			switch(choice){
				case 1:
					System.out.println();
					g.displayTopology();
					System.out.println();
					break;
				case 2:
					System.out.println();
					System.out.println("Please input your source node");
					fromNode = scan.nextInt();
					g.calculateShortestDistances(fromNode);
					g.buildConnectionTable(fromNode);
					System.out.println();
					break;
				case 3:
					
					System.out.println();

					if (fromNode == -1){//check if the fromNode equal to the default value -1, 
						System.out.println("Please input your source node");
						fromNode = scan.nextInt();
					}
					System.out.println("Please input your destination node");
					toNode = scan.nextInt();
					String p;
					p = g.shortestPahtBetween(fromNode, toNode);
					System.out.println(p);
					System.out.println();
					break;
				case 4:
					System.out.println();

					System.out.println("Select a router to be removed:");
					int removedNode = scan.nextInt();
					g.modifyTopology(removedNode);
					g.displayTopology();
					if (fromNode==-1||toNode==-1) {
						System.out.println("Please input your source node");
						fromNode = scan.nextInt();
						System.out.println("Please input your destination node");
						toNode = scan.nextInt();
					}
					g.calculateShortestDistances(fromNode);
					g.buildConnectionTable(fromNode);
					System.out.println(g.shortestPahtBetween(fromNode, toNode));
					System.out.println();
					break;
				case 5:
					System.out.println();
					String b =g.bestRouterForBroadcase();
					System.out.println(b);
					System.out.println();
					break;
				case 6:
					System.out.println("Exit CS542-04 2016 Fall project.\n Thanks for using, Good Bye!");
					done = true;
					break;
					
			}
		}while(!done);
		scan.close();
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		String filePath = "src/BinweiQi/topology.txt";
		if (args.length >= 1) {
			filePath = args[0];
		}
		
		File inputFile = new File(filePath);
		Scanner scfile = new Scanner(inputFile);
	 
		Graph g = new Graph(scfile);
		
		job(g);
		
		
	}
}
