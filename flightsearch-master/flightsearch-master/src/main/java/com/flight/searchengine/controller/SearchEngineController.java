package com.flight.searchengine.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flight.searchengine.entity.FlightDetails;
import com.flight.searchengine.repository.SearchEngineRepository;
import com.flight.searchengine.service.SearchEngineService;
//import com.flight.searchengine.service.SearchEngineServiceImpl;

import lombok.AllArgsConstructor;

@RequestMapping("/flight/search")
@RestController
@CrossOrigin
@AllArgsConstructor
public class SearchEngineController {
	
	@Autowired
	private SearchEngineRepository repo;
	
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
	
	
	
//	@RequestMapping("/fetchFlight")
////	public List<FlightDetails> fetchFlight(@RequestBody FlightDetails request){
////		List<FlightDetails> a= repo.getAvailableFlights( request.getSource(), request.getDestination()) ;
//	public List<FlightDetails> fetchFlight(@RequestParam String source, @RequestParam String destination, @RequestParam Timestamp departure, @RequestParam(required=false, defaultValue="2500") int price, @RequestParam(required=false, defaultValue="4") long duration, @RequestParam(required=false) String flightName, @RequestParam(required=false) String offercode){ 
//		//List<FlightDetails> flightDetails= repo.getAvailableFlights(source, destination, departure, price, duration, flightName, offercode) ;
//		List<FlightDetails> flightDetails= service.getAvailableFlights(source, offercode) ;
//
//		return flightDetails;
//	}
	
	@PostMapping("/fetchflight")
	public List<FlightDetails> fetchFlight(@RequestParam String source, @RequestParam String destination,  @RequestParam Long departureTime, @RequestParam(required=false) Integer stops, @RequestParam(required=false) String price, @RequestParam(required=false) String duration, @RequestParam(required=false) String flightName, @RequestParam(required=false) String offercode){ 
		List<FlightDetails> flightDetails= service.getAvailableFlights(source, destination, departureTime, stops, price, duration, flightName, offercode) ;
		return flightDetails;
	}
	
	
//	@RequestMapping("/fetchByStops")
//	public List<FlightDetails> fetchByStops(@RequestParam int stops){
//		List<FlightDetails> a= repo.findByStops( stops) ;
//		return a;
//	}
//	
//
//	@RequestMapping("/fetchByDuration")
//	public List<FlightDetails> fetchByDuration(@RequestParam long duration){
//		List<FlightDetails> a= repo.findByDuration( duration) ;
//		return a;
//		
//	}
}
