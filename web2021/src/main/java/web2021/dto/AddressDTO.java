package web2021.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
	
	private String city;
	
	private String city_district;
	
	private String country;
	
	private String country_code;
	
	private String county;
	
	private String house_number;
	
	private String neighbourhood;
	
	private int postcode;
	
	private String road;
	
	private String state;
	
	private String village;
	
}
