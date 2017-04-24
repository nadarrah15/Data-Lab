
public class Address implements Comparable<Address>{
	
	private String street, city, state;
	private int zip;
	
	public Address(String street, String city, String state, int zip){
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public String toString(){
		return "\"" + street + ", " + city + ", " + state + " " + zip + "\"";
	}
	
	public String getStreet(){
		return street;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getState(){
		return state;
	}
	
	public int getZip(){
		return zip;
	}

	@Override
	public int compareTo(Address o) {
		int result = this.getState().compareTo(o.getState());
		if(result == 0)
			result = this.getCity().compareTo(o.getCity());
		
		return result;
	}
}
