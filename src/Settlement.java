import java.util.ArrayList;


/**
 * Represents a settlement with a name, population, type and
 * with connected roads.
 * @author Izabela Slawek
 * @version 1.0 (14th March 2016)
 */

public class Settlement {
	private String name;
	private int population;
	private SettlementType kind;
	private ArrayList<Road> roads;
	
	/**
	 * Constructor to build a settlement
	 * @param initName The name of the settlement
	 * @param initKind The kind of the settlement
	 */
	public Settlement(String initName, SettlementType initKind){
		this();
		kind = initKind;
		name = initName;
	}
	
	/**
	 * Default constructor for a settlement
	 */
	public Settlement() {
		name = null;
		kind = null;
		population = 0;
		roads = new ArrayList<>();
	}

	/**
	 * The name of the settlement.
	 * @return The name of the settlement. 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * The current population
	 * @return The population
	 */
	public int getPopulation() {
		return population;
	}
	
	/**
	 * Change the population size
	 * @param size The new population size
	 */
	public void setPopulation(int size) {
		this.population = size;
	}
	
	/**
	 * The kind of settlement, e.g. village, town etc
	 * @return The kind of settlement
	 */
	public SettlementType getKind() {
		return kind;
	}
	
	/**
	 * The population has grown or the settlement has been granted a new status (e.g. city status)
	 * @param kind The new settlement kind
	 */
	public void setKind(SettlementType kind) {
		this.kind = kind;
	}
	
	/**
	 * Add a new road to the settlement. 
	 * Report an error if the road is already connected to the settlement
	 * @param road The new road to add. 
	 */
	public void add(Road road){
		if(!roads.contains(road)){
			roads.add(road);
		}else{
			System.out.println("Road already exists");
		}
	}
	
	/**
	 * Deletes all the roads attached to this settlement. It will
	 * also detach these roads from the settlements at the other end
	 * of each road 
	 */
	public void deleteRoads() {
		for(Road road : roads){   //detach roads from settlements on the other end
			System.out.println(road.getName());
			if(road.getSourceSettlement().equals(this)){
				if(!road.getDestinationSettlement().equals(this)){
					road.getDestinationSettlement().delete(road);
				}
			}else{
				road.getSourceSettlement().delete(road);
			}
		}
		roads.clear();
	}
	
	/**
	 * Deletes the given road attached to this settlement. 
	 * @param road The road to delete.
	 */
	public void delete(Road road){
		if(roads.contains(road)){
			roads.remove(road);
		}else{
			System.out.println("Road doesn't exist");
		}
	}

	/**
	 * New equals method
	 * Compare name and classification of the settlement
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Settlement other = (Settlement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Human readable representation of the settlement
	 * Show the name, classification, population and connected roads
	 * @return String representation of the settlement
	 */
	@Override
	public String toString() {
		String result = " Settlement name: "+ name + "\n Population: " + population + "\n Kind: " + kind.toString() + "\n Roads: \n";
		for(Road road : roads){
			result += "  " + road.getName() + " connected to ";
			if(road.getDestinationSettlement().getName().equals(name)){
				result += road.getSourceSettlement().getName() + "\n";
			}else{
				result += road.getDestinationSettlement().getName() + "\n";
			}
		}
		return result;
	}
	
}
