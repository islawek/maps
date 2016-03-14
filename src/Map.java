import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * 
 * @author Chris Loftus 
 * @version 1.0 (25th February 2016)
 *
 */

public class Map {
	
	private ArrayList<Settlement> settlements;
	private ArrayList<Road> roads;

	public Map() {
		settlements = new ArrayList<>();
		roads = new ArrayList<>();
	}

	/**
	 * In this version we display the result of calling toString on the command
	 * line. Future versions may display graphically
	 */
	public void display() {
		System.out.println(toString());
	}

	public void addSettlement(Settlement newSettlement) throws IllegalArgumentException {
		if (!settlements.contains(newSettlement)){
				settlements.add(newSettlement);
		}else{
			System.out.println("Settlement already exists");
		}
	}
	
	public void deleteSettlement(String settlementName){
		boolean found = false;
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
	
	public boolean findSettlement(String settlementName){
		for(Settlement settlement : settlements){
			if(settlementName.equals(settlement.getName())){
				return true;
			}
		}
		return false;
	}
	
	public Settlement getSettlement(String settlementName){
		for(Settlement settlement : settlements){
			if(settlementName.equals(settlement.getName())){
				return settlement;
			}
		}
		return null;
	}

	
	public void addRoad(Road newRoad){
		if(!roads.contains(newRoad)){
				roads.add(newRoad);
				System.out.println("New road added");
		}else{
			System.out.println("Road already exists");
		}
	}
	
	public void deleteRoad(String roadName, String sourceName, String destinationName){
		boolean found = false;
		for(Road road : roads){
			if(road.getName().equals(roadName) && road.getSourceSettlement().getName().equals(sourceName)&&
					road.getDestinationSettlement().getName().equals(destinationName)){
				road.getSourceSettlement().delete(road);
				road.getDestinationSettlement().delete(road);
				roads.remove(road);
				System.out.println("Road deleted.");
				found = true;
				break;
			}
		}
		if(!found){
			System.out.println("Road not found");
		}
	}
	
	public boolean findRoad(String roadName, String sourceName, String destinationName){
		for(Road road : roads){
			if(road.getName().equals(roadName) && road.getSourceSettlement().getName().equals(sourceName)&&
					road.getDestinationSettlement().getName().equals(destinationName)){
				return true;
			}
		}
		return false;
	}
	
	public void load() throws FileNotFoundException{
		//load settlements
		String settlementName;
		SettlementType settlementKind;
		int population;
		Scanner readFile = new Scanner(new FileReader("settlements.txt"));
		readFile.useDelimiter(":|\n");
		int numberOfSettlements = readFile.nextInt();
		for(int i = 0; i < numberOfSettlements; i++){
			settlementName = readFile.next();
			population = readFile.nextInt();
			settlementKind = SettlementType.valueOf(readFile.next());
			Settlement newSettlement = new Settlement(settlementName, settlementKind);
			newSettlement.setPopulation(population);
			settlements.add(newSettlement);
		}
		readFile.close();
		//load roads
		String roadName, sourceName, destinationName;
		Classification classifier;
		double length;
		readFile = new Scanner(new FileReader("roads.txt"));
		readFile.useDelimiter(":|\n");
		int numberOfRoads = readFile.nextInt();
		for(int i = 0; i < numberOfRoads; i++){
			roadName = readFile.next();
			classifier = Classification.valueOf(readFile.next());
			length = readFile.nextDouble();
			sourceName = readFile.next();
			destinationName = readFile.next();
			if(findSettlement(sourceName) && findSettlement(destinationName)){
				Road newRoad = new Road(roadName, classifier, getSettlement(sourceName), getSettlement(destinationName), length);
				roads.add(newRoad);
			}else{
				System.out.println("Settlements don't exist");
			}
		}
		readFile.close();
	}

	public void save() throws IOException{
		//save settlements
		PrintWriter saveToFile = new PrintWriter("settlements2.txt");
		saveToFile.println(settlements.size());
		for(Settlement settlement : settlements){
			saveToFile.println(settlement.getName() + ":" + settlement.getPopulation() + ":" + settlement.getKind());
		}
		saveToFile.close();
		//save roads
		saveToFile = new PrintWriter("roads2.txt");
		saveToFile.println(roads.size());
		for(Road road : roads){
			saveToFile.println(road.getName() + ":" + road.getClassification() + ":" + road.getLength() + ":" 
			+ road.getSourceSettlement().getName() + ":" + road.getDestinationSettlement().getName());
		}
		saveToFile.close();
	}

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
