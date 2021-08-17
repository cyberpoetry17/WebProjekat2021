package web2021.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web2021.dto.LocationDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Location implements Serializable {
	
	private static final long serialVersionUID = -9076154249536808759L;
	
//	private Long id;
	
	private double longitude;
	
	private double latitude;
	
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

	private String display_name;

	public Location(LocationDTO locationDTO) {
		this.latitude = locationDTO.getLatitude();
		this.longitude = locationDTO.getLongitude();
		this.city = locationDTO.getAddress().getCity();
		this.city_district = locationDTO.getAddress().getCity_district();
		this.country = locationDTO.getAddress().getCountry();
		this.country_code = locationDTO.getAddress().getCountry_code();
		this.county = locationDTO.getAddress().getCounty();
		this.house_number = locationDTO.getAddress().getHouse_number();
		this.neighbourhood = locationDTO.getAddress().getNeighbourhood();
		this.postcode = locationDTO.getAddress().getPostcode();
		this.road = locationDTO.getAddress().getRoad();
		this.state = locationDTO.getAddress().getState();
		this.village = locationDTO.getAddress().getVillage();
		this.display_name = locationDTO.getDisplay_name();
	}

}
