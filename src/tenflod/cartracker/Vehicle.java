package tenflod.cartracker;

public class Vehicle {
		
	String id;
	String nickname;
	Integer year;
	String make;
	String model;
	Integer imageId;
	
	public Vehicle(String nickname, Integer year, String make, String model, Integer imageId)
	{
		this.nickname = nickname;
		this.year = year;
		this.make = make;
		this.model = model;
		this.imageId = imageId;
	}
	
	public String getFullName()
	{
		return String.format("%s %s %s", year.toString(), make, model);
	}
}
