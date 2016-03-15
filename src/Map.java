import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileReader; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * Class representation of map
 * Map contains of settlements and roads
 * @author Izabela Slawek
 * @version 1.0 (14th March 2016)
 *
 */

public class Map {
	
	private ArrayList<Settlement> settlements;
	private ArrayList<Road> roads;
	private Scanner scan;

	/**
	 * Constructor for a map
	 */
	public Map() {
		settlements = new ArrayList<>();
		roads = new ArrayList<>();
		scan = new Scanner(System.in);
		scan.useDelimiter("\r?\n|\r|:");
	}

	/**
	 * Display map
	 */
	public void display() {
		System.out.println(toString());
	}

	/**
	 * Add a settlement to list of settlements
	 * Check if the settlement is already there
	 * If it is, report an error
	 * @param newSettlement The new settlement to add
	 */
	private void addSettlement(Settlement newSettlement){
        if (!settlements.contains(newSettlement)){
                settlements.add(newSettlement);
                System.out.println("Settlement created");
        }else{
            System.out.println("Settlement already exists");
        }
    }
	
	/**
	 * Create new settlement
	 */
	public void createSettlement(){
		String settlementName;
		SettlementType settlementKind;
		System.out.println("Enter name of the settlement:\n");
		settlementName = scan.next();
		settlementKind = askForSettlementKind();
		Settlement newSettlement = new Settlement(settlementName, settlementKind);
		System.out.println("Enter population:\n");
		newSettlement.setPopulation(scan.nextInt());
		addSettlement(newSettlement);
	}
	
	/**
	 * Ask user for settlement kind when creating new settlement
	 * @return Settlement kind
	 */
	private SettlementType askForSettlementKind(){
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
	
	/**
	 * Delete settlement
	 * Check if settlement exists, if not report an error
	 */
	public void deleteSettlement(){
		String settlementName;
		boolean found = false;
		System.out.println("Enter the name of the settlement to delete:\n");
		settlementName = scan.next();
		for(Settlement settlement : settlements){
			if(settlementName.equals(settlement.getName())){
				for(Iterator<Road> it = roads.iterator(); it.hasNext();){  //delete roads connected to settlement to delete
					Road road = it.next();
					if(road.getSourceSettlement().getName().equals(settlementName) || road.getDestinationSettlement().getName().equals(settlementName)){
						it.remove();
					}
				}
				settlement.deleteRoads(); //detach roads from other settlements
				settlements.remove(settlement);
				System.out.println("Settlement deleted.");
				found = true;
				break;
			}
		}
		if(!found){
			System.out.println("Settlement doesn't exist.");
		}
	}
	
	/**
	 * Check if settlement is in the list of settlements
	 * @param settlementName Name of settlement
	 * @return true if settlement exists
	 */
	public boolean settlementExists(String settlementName){
		for(Settlement settlement : settlements){
			if(settlementName.equals(settlement.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Given name of the settlement it returns the settlement
	 * @param settlementName Name of the settlement
	 * @return Settlement or null
	 */
	public Settlement getSettlement(String settlementName){
		for(Settlement settlement : settlements){
			if(settlementName.equals(settlement.getName())){
				return settlement;
			}
		}
		return null;
	}

	/**
	 * Add road to list of roads
	 * Report an error if road already exists
	 * @param newRoad New road to add
	 */
	private void addRoad(Road newRoad){
		if(!roads.contains(newRoad)){
				roads.add(newRoad);
				System.out.println("New road added");
		}else{
			System.out.println("Road already exists");
		}
	}
	
	/**
	 * Create new road
	 * Check if source and destination settlements exist
	 * Does not check if there is a road between settlements, as there can be many different roads
	 */
	public void createRoad(){
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
		if(settlementExists(sourceName)){
			System.out.println("Enter name of destination settlement:\n");
			destinationName = scan.next();
			if(settlementExists(destinationName)){
				Road newRoad = new Road(roadName, roadKind, getSettlement(sourceName), getSettlement(destinationName), length);
				addRoad(newRoad);
			}else{
				System.out.println("Destination doesn't exist.");
			}
		}else{
			System.out.println("Source doesn't exist");
		}
				
	}
	
	/**
	 * Ask user for road classification
	 * @return Road classification
	 */
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

	/**
	 * Delete road
	 * Detach it from source and destination settlements
	 * Report an error if road doesn't exists
	 */
	public void deleteRoad(){
		String roadName, sourceName, destinationName;
		System.out.println("Enter name of the road to delete:\n");
		roadName = scan.next();
		System.out.println("Enter source settlement name:\n");
		sourceName = scan.next();
		System.out.println("Enter destination settlement name:\n");
		destinationName = scan.next();
		if(roadExists(roadName, sourceName, destinationName)){
			for(Road road : roads){
				if(road.getName().equals(roadName) && road.getSourceSettlement().getName().equals(sourceName)&&
						road.getDestinationSettlement().getName().equals(destinationName)){
					road.getSourceSettlement().delete(road);
					road.getDestinationSettlement().delete(road);
					roads.remove(road);
					System.out.println("Road deleted.");
					break;
				}
			}
		}else{
			System.out.println("Road doesn't exist");
		}
	}
	
	/**
	 * Check if a road is in the list of road
	 * @param roadName Name of the road
	 * @param sourceName Name of the source settlement
	 * @param destinationName Name of the destination settlement
	 * @return True if road exists
	 */
	public boolean roadExists(String roadName, String sourceName, String destinationName){
		for(Road road : roads){
			if(road.getName().equals(roadName) && road.getSourceSettlement().getName().equals(sourceName)&&
					road.getDestinationSettlement().getName().equals(destinationName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Load settlements from file
	 * Report an error if file doesn't exists
	 */
	public void loadSettlements(){
		String settlementName, settlementFileName = "settlements.txt";
		SettlementType settlementKind;
		int population, numberOfSettlements;
		try{
			Scanner readFile = new Scanner(new FileReader(settlementFileName));
			readFile.useDelimiter(":|\n");
			numberOfSettlements = readFile.nextInt();
			for(int i = 0; i < numberOfSettlements; i++){
				settlementName = readFile.next();
				population = readFile.nextInt();
				settlementKind = SettlementType.valueOf(readFile.next());
				Settlement newSettlement = new Settlement(settlementName, settlementKind);
				newSettlement.setPopulation(population);
				settlements.add(newSettlement);
			}
			readFile.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Settlements file not found");
		}
	}
	
	/**
	 * Load roads from file
	 * Report an error if file doesn't exists
	 */
	public void loadRoads(){
		String roadName, sourceName, destinationName, roadFileName = "roads.txt";
		Classification classifier;
		double length;
		int numberOfRoads;
		try{
			Scanner readFile = new Scanner(new FileReader(roadFileName));
			readFile.useDelimiter(":|\n");
			numberOfRoads = readFile.nextInt();
			for(int i = 0; i < numberOfRoads; i++){
				roadName = readFile.next();
				classifier = Classification.valueOf(readFile.next());
				length = readFile.nextDouble();
				sourceName = readFile.next();
				destinationName = readFile.next();
				if(settlementExists(sourceName) && settlementExists(destinationName)){
					Road newRoad = new Road(roadName, classifier, getSettlement(sourceName), getSettlement(destinationName), length);
					roads.add(newRoad);
				}else{
					System.out.println("Settlements don't exist");
				}
			}
			readFile.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Roads file not found.");
		}
	}

	/**
	 * Save settlements to file
	 * Report an error if file not found
	 */
	public void saveSettlements(){
		try{
			PrintWriter saveToFile = new PrintWriter("settlements2.txt");
			saveToFile.println(settlements.size());
			for(Settlement settlement : settlements){
				saveToFile.println(settlement.getName() + ":" + settlement.getPopulation() + ":" + settlement.getKind());
			}
			saveToFile.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Settlements file not found");
		}
	}
	
	/**
	 * Save roads to file
	 * Report an error if file not found
	 */
	public void saveRoads(){
		try{
			PrintWriter saveToFile = new PrintWriter("roads2.txt");
			saveToFile.println(roads.size());
			for(Road road : roads){
				saveToFile.println(road.getName() + ":" + road.getClassification() + ":" + road.getLength() + ":" 
				+ road.getSourceSettlement().getName() + ":" + road.getDestinationSettlement().getName());
			}
			saveToFile.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("Roads file not found");
		}
	}
	
	/**
	 * Human readable string representation of map
	 * @return String representation of map
	 */
	@Override
	public String toString() {
		String result = "Map settlements:\n";
		for(Settlement settlement : settlements){
			result += settlement.toString() + "\n";
		}
		result += "Map roads:\n";
		for(Road road : roads){
			result += road.toString() + "\n";
		}
		return result;
	}
}
