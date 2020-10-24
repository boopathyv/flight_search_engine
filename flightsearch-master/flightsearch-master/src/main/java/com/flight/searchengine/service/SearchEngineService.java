package com.flight.searchengine.service;




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.flight.searchengine.entity.FlightDetails;
import com.flight.searchengine.repository.SearchEngineRepository;
@Service
public class SearchEngineService{

	@Autowired  
	SearchEngineRepository repo;

	String line="";
	
	public List<String> getAllSources() {
		return repo.findAllSources();
	}

	public void insertDataFromCsv() {
		FlightDetails e= new FlightDetails();
		try{
			BufferedReader br=new BufferedReader(new FileReader("src/main/resources/flightdetails6.csv"));
			while((line=br.readLine())!=null){
				System.out.println("Line"+line);
				String [] data=line.split(",");
				e.setFlightName(data[0]);
				e.setSource(data[1]);
				e.setDestination(data[2]);
				long timestampdep = Timestamp.valueOf(data[3]).getTime();
				e.setDeparture(timestampdep);
				long timestamparr = Timestamp.valueOf(data[4]).getTime();	
				e.setArrival(timestamparr);
				e.setOffercode(data[5]);
				e.setId(data[6]);
				e.setStops(Integer.parseInt(data[7]));
				e.setDuration(Long.parseLong(data[8]));
				e.setPrice(Integer.parseInt(data[9]));
				e.setCabin(data[10]);
				repo.save(e);
				
//				System.out.println("Line"+line);
//				data=line.split(",");
//				e.setId(data[0]);
//				e.setFlightName(data[1]);
//				e.setSource(data[2]);
//				e.setDestination(data[3]);
//				long timestampdep = Timestamp.valueOf(data[4]).getTime();
//				e.setDeparture(timestampdep);
//				long timestamparr = Timestamp.valueOf(data[5]).getTime();	
//				e.setArrival(timestamparr);
//				e.setDuration(Long.parseLong(data[6]));
//				e.setStops(Integer.parseInt(data[7]));
//				e.setCabin(data[8]);
//				e.setOffercode(data[9]);
//				e.setPrice(Integer.parseInt(data[10]));
//				repo.save(e);
			}
		}catch(IOException exp){

			exp.printStackTrace();
		}
	}
	
	
		
		public List<FlightDetails> getAvailableFlights(String source,String destination,Long departureTime, Integer stops, String price,String duration,String flightName,String offercode, String cabin){
	        return repo.findAll(new Specification<FlightDetails>() {
	            @Override
	            public Predicate toPredicate(Root<FlightDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
	                List<Predicate> predicates = new ArrayList<>();
	                List<Order> orderList = new ArrayList<>();
	                if(source!=null) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("source"), source)));
	                }
	                if(destination!=null) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("destination"), destination)));
	                }
	                if(departureTime!=null) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("departure"), departureTime)));
	                }
	                if(cabin!=null && !cabin.isEmpty()) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cabin"), cabin)));
	                }
	                if (stops != null) {
	                	if (stops > 2) {
	                		predicates.add(
								criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("stops"), 0)));
	                	}else if (stops > 1) {
	                		predicates.add(
								criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("stops"), 2)));
	                	} else {
	                		predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("stops"), stops)));
	                	}
	                }
	                if(price == null || price.equals("ASC")){
	                	orderList.add(criteriaBuilder.asc(root.get("price")));
	                }else if(price.equals("DESC")){
	                	orderList.add(criteriaBuilder.desc(root.get("price")));
	                }
	                if(duration == null || duration.equals("ASC")){
	                	orderList.add(criteriaBuilder.asc(root.get("duration")));
	                }else if(duration.equals("DESC")){
	                	orderList.add(criteriaBuilder.desc(root.get("duration")));
	                }
	                if(flightName!=null && !flightName.isEmpty()) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("flightName"), flightName)));
	                }
	                if(offercode!=null && !offercode.isEmpty()) {
	                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("offercode"), offercode)));
	                }
	                if(!orderList.isEmpty()){
	                	query.orderBy(orderList);
	                }
	                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	            }
	        });
	    }

		public List<String> getAllDestinations() {
			return repo.findAllDestination();
		}

		public List<String> getAirlines() {
			return repo.findAllAirlines();
		}

		public List<String> getCabins() {
			return repo.findAllCabins();
		}

		public List<String> getOfferCode() {
			return repo.findAllOfferCode();
		}

	

}
