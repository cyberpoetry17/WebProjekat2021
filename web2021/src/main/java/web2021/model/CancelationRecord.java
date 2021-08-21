package web2021.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelationRecord {
	
	private LocalDateTime dateOfFirstCancelation;
	
	private LocalDateTime dateOfLastCancelation;
	
	private int counter;
	
}
