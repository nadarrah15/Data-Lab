import java.io.Serializable;

public class Record implements Serializable{
	
	private long StudentID;
	private int credits;
	private double GPA;
	private String major;
	private Address address;
	
	public Record(long StudentID, int credits, double GPA, String major, Address address){
		this.StudentID = StudentID;
		this.credits = credits;
		this.GPA = GPA;
		this.major = major;
		this.address = address;
	}
	
	public long getID(){
		return StudentID;
	}
	
	public int getCredits(){
		return credits;
	}
	
	public double getGPA(){
		return GPA;
	}
	
	public String getMajor(){
		return major;
	}
	
	public Address getAddress(){
		return address;
	}
}
