
/**
 * Represents a road that is linked to two settlements: source and destination.
 * @author Izabela Slawek
 * @version 1.0 (14th March 2015)
 */
public class Road {
	private String name;
	private Classification classification;
	private Settlement sourceSettlement;
	private Settlement destinationSettlement;
	private double length;
	
	/**
	 * Constructor to build road between two settlements. 
	 * @param name The road name
	 * @param classifier The class of road, e.g. 'A'
	 * @param source The source settlement
	 * @param destination The destination settlement (can be the same as the source!)
	 * @param length The length of the road
	 */	
	public Road(String name, 
			Classification classifier, 
			Settlement source, 
			Settlement destination,
			double length){
		
		this.name = name;
		classification = classifier;
		sourceSettlement = source;
		source.add(this);
		destinationSettlement = destination;
		destination.add(this);
		this.length = length;
	}
	
	/**
	 * The name of the road
	 * @return The road's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name of the road
	 * @param newName New name of the road
	 */
	public void setName(String newName){
		name = newName;
	}
	
	/**
	 * Set length of the road
	 * @param len Length of the road
	 */
	public void setLength(double len) {
		length = len;
	}
	
	/**
	 * Get length of the road
	 * @return Length of the road
	 */
	public double getLength(){
		return length;
	}
	
	/**
	 * The road's class
	 * @return The class of the road, e.g. A
	 */
	public Classification getClassification() {
		return classification;
	}
	
	/**
	 * The source settlement
	 * @return One end of the road we call source
	 */
	public Settlement getSourceSettlement() {
		return sourceSettlement;
	}
	
	/**
	 * The destination settlement
	 * @return One end of the road we call destination
	 */
	public Settlement getDestinationSettlement() {
		return destinationSettlement;
	}

	/**
	 * New equals method
	 * Compare name and source and destination settlements names
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Road other = (Road) obj;
		if (destinationSettlement == null) {
			if (other.destinationSettlement != null)
				return false;
		} else if (!destinationSettlement.equals(other.destinationSettlement))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sourceSettlement == null) {
			if (other.sourceSettlement != null)
				return false;
		} else if (!sourceSettlement.equals(other.sourceSettlement))
			return false;
		return true;
	}


	/**
	 * Human readable representation of the road
	 * Show the name, classification, length and connected settlements
	 * @return String representation of the road
	 */
	@Override
	public String toString() {
		String result = " Road name: " + name + "\n Classification: " + classification 
				+ "\n Length: " + length + "\n Connects " + sourceSettlement.getName() 
				+ " with " + destinationSettlement.getName() +"\n";
		return result;
	}
}
