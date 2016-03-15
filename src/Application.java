import java.util.Scanner;

/**
 * Main class to test the Road and Settlement classes
 * 
 * @author Izabela Slawek
 * @version 1.0 (14th March 2016)
 *
 */
public class Application {

	private Scanner scan;
	private Map map;
	
	/**
	 * Constructor for application
	 */
	public Application() {
		scan = new Scanner(System.in);
		scan.useDelimiter("\r?\n|\r"); // Newlines on Unix or DOS
		map = new Map();
	}

	/**
	 * Run command line menu
	 */
	private void runMenu(){
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

	/**
	 * Display map
	 */
	private void display(){
		map.display();
	}
	
	/**
	 * Save map to file
	 */
	private void save(){
		map.saveSettlements();
		map.saveRoads();
	}

	/**
	 * Load map from file
	 */
	private void load(){
		map.loadSettlements();
		map.loadRoads();
	}

	/**
	 * Print menu
	 */
	private void printMenu() {
		System.out.println(
				"Choose one of the options: \n 1 - create settlement\n 2 - delete settlement\n "
				+ "3 - create road\n 4 - delete road\n 5 - display map\n 6 - save map\n 7 - quit");
	}
	
	/**
	 * Create new settlement
	 */
	private void createSettlement(){
		map.createSettlement();
	}
	
	/**
	 * Delete settlement
	 */
	private void deleteSettlement(){
		map.deleteSettlement();
	}
	
	/**
	 * Create new road
	 */
	private void createRoad(){
		map.createRoad();
	}

	/**
	 * Delete road
	 */
	private void deleteRoad(){
		map.deleteRoad();
	}
	
	/**
	 * The main method of the application
	 */
	public static void main(){
		Application app = new Application();
		app.load();
		app.runMenu();
		app.save();
	}

}
