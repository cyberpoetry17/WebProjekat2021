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
	
	public Address(String street,int num,String placeName,int postalCode){
		address=street+" "+num+","+placeName+postalCode;
	}
}