package com.flight.searchengine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flight.searchengine.entity.FlightDetails;
import com.flight.searchengine.service.SearchEngineService;

import lombok.AllArgsConstructor;

@RequestMapping("/flight/search")
@RestController
@CrossOrigin
@AllArgsConstructor
public class SearchEngineController {
	
	@Autowired
	private SearchEngineService service;

	@GetMapping("/csvinsert")
	public String csvinsert(){
			service.insertDataFromCsv();
			return	"CSV INSERTED";
	}
	
	@GetMapping("/getAllSources")
	public List<String> getAllSources(){
		return service.getAllSources();
	}
	
	@GetMapping("/getAllDestinations")
	public List<String> getAllDestinations(){
		return service.getAllDestinations();
	}
	
	@GetMapping("/getAirlines")
	public List<String> getAirlines(){
		return service.getAirlines();
	}
	
	@GetMapping("/getCabins")
	public List<String> getCabins(){
		return service.getCabins();
	}
	
	@GetMapping("/getOfferCode")
	public List<String> getOfferCode(){
		return service.getOfferCode();
	}
	
	@PostMapping("/fetchflight")
	public List<FlightDetails> fetchFlight(@RequestParam String source, @RequestParam String destination,  @RequestParam Long departureTime,
			@RequestParam(required=false) Integer stops, @RequestParam(required=false) String price, @RequestParam(required=false) String duration,
			@RequestParam(required=false) String flightName, @RequestParam(required=false) String offercode,@RequestParam(required=false) String cabin){ 
		List<FlightDetails> flightDetails= service.getAvailableFlights(source, destination, departureTime, stops, price, duration, flightName, offercode, cabin) ;
		return flightDetails;
	}
	
}
