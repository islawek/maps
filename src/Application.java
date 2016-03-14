
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

/**
 * Main class to test the Road and Settlement classes
 * 
 * @author Chris Loftus (add your name and change version number/date)
 * @version 1.0 (24th February 2016)
 *
 */
public class Application {

	private Scanner scan;
	private Map map;
	private Settlement settlement;

	public Application() {
		scan = new Scanner(System.in);
		scan.useDelimiter("\r?\n|\r"); // Newlines on Unix or DOS
		map = new Map();
	}

	private void runMenu() throws FileNotFoundException, IOException{
		int choice;
		do {
			printMenu();
			choice = scan.nextInt();
			switch (choice) {
			case 1:
				createSettlement();
				break;
			case 2:
				deleteSettlement();
				break;
			case 3:
				createRoad();
				break;
			case 4:
				deleteRoad();
				break;
			case 5:
				display();
				break;
			case 6:
				save();
				break;
			case 7:
				System.out.println("bye bye");
				break;
			default:
				System.out.println("Not a valid choice.");
			}
		} while (choice != 7);
	}

	private void display(){
		map.display();
	}
	private void save() throws IOException{
		map.save();
	}

	private void load() throws FileNotFoundException{
		map.load();
	}

	private void printMenu() {
		System.out.println(
				"Choose one of the options: \n 1 - create settlement\n 2 - delete settlement\n 3 - create road\n 4 - delete road\n 5 - display map\n 6 - save map\n 7 - quit");
	}

	private void createSettlement() {
		String settlementName;
		SettlementType settlementKind;
		System.out.println("Enter name of the settlement:\n");
		settlementName = scan.next();
		settlementKind = askForSettlementKind();
		Settlement newSettlement = new Settlement(settlementName, settlementKind);
		System.out.println("Enter population:\n");
		newSettlement.setPopulation(scan.nextInt());
		map.addSettlement(newSettlement);
	}

	private SettlementType askForSettlementKind() {
		SettlementType result = null;
		boolean valid;
		do {
			valid = false;
			System.out.print("Enter kind of settlement:\n ");
			for (SettlementType type : SettlementType.values()) {
				System.out.print(type + " ");
			}
			String choice = scan.next().toUpperCase();
			try {
				result = SettlementType.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae) {
				System.out.println(choice + " is not one of the options. Try again.");
			}
		} while (!valid);
		return result;
	}
	
	private void deleteSettlement(){
		System.out.println("Enter the name of the settlement to delete:\n");
		String settlementName = scan.next();
		map.deleteSettlement(settlementName);
	}
	
	private void createRoad(){
		String roadName, sourceName, destinationName;
		Classification roadKind;
		double length;
		System.out.println("Enter name of the road:\n");
		roadName = scan.next();
		roadKind = askForRoadClassifier();
		System.out.println("Enter the length of the road:\n");
		length = scan.nextDouble();
		System.out.println("Enter name of source settlement:\n");
		sourceName = scan.next();
		if(map.findSettlement(sourceName)){
			System.out.println("Enter name of destination settlement:\n");
			destinationName = scan.next();
			if(map.findSettlement(destinationName)){
				Road newRoad = new Road(roadName, roadKind, map.getSettlement(sourceName), map.getSettlement(destinationName), length);
				map.addRoad(newRoad);
			}else{
				System.out.println("Destination doesn't exist.");
			}
		}else{
			System.out.println("Source doesn't exist");
		}
				
	}
	
	private void deleteRoad(){
		String roadName, sourceName, destinationName;
		System.out.println("Enter name of the road to delete:\n");
		roadName = scan.next();
		System.out.println("Enter source settlement name:\n");
		sourceName = scan.next();
		System.out.println("Enter destination settlement name:\n");
		destinationName = scan.next();
		if(map.findRoad(roadName, sourceName, destinationName)){
				map.deleteRoad(roadName, sourceName, destinationName);
		}else{
			System.out.println("Road doesn't exist");
		}
	}
	
	private Classification askForRoadClassifier() {
		Classification result = null;
		boolean valid;
		do {
			valid = false;
			System.out.print("Enter a road classification: ");
			for (Classification cls : Classification.values()) {
				System.out.print(cls + " ");
			}
			String choice = scan.next().toUpperCase();
			try {
				result = Classification.valueOf(choice);
				valid = true;
			} catch (IllegalArgumentException iae) {
				System.out.println(choice + " is not one of the options. Try again.");
			}
		} while (!valid);
		return result;
	}

	public static void main(String args[]) throws FileNotFoundException, IOException{
		Application app = new Application();
		app.load();
		app.runMenu();
		app.save();
	}

}
