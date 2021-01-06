package beans;

public class Address {
	@Override
	public String toString() {
		return  address;
	}

	private String address="";
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Address(){}
	
	public Address(String street,int number,String place,int postalCode){
		address=street+" "+ number +","+place +postalCode;
	}
}