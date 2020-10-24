package com.flight.searchengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flight.searchengine.entity.FlightDetails;

@Repository
public interface SearchEngineRepository
		extends CrudRepository<FlightDetails, Integer>, JpaSpecificationExecutor<FlightDetails> {

	FlightDetails save(FlightDetails e);

	@Query(value = "SELECT distinct source FROM FLIGHT_DETAILS", nativeQuery = true)
	List<String> findAllSources();

	@Query(value = "SELECT distinct destination FROM FLIGHT_DETAILS", nativeQuery = true)
	List<String> findAllDestination();

	@Query(value = "SELECT distinct flight_name FROM FLIGHT_DETAILS", nativeQuery = true)
	List<String> findAllAirlines();

	@Query(value = "SELECT distinct cabin FROM FLIGHT_DETAILS", nativeQuery = true)
	List<String> findAllCabins();

	@Query(value = "SELECT distinct offercode FROM FLIGHT_DETAILS", nativeQuery = true)
	List<String> findAllOfferCode();

	// @Modifying
	// @Query(value = "SELECT * FROM FLIGHT_DETAILS flights WHERE "
	// + " flights.source = :source AND flights.destination = :destination AND
	// flights.departure >= :departure AND flights.price <= :price AND
	// flights.duration <= :duration AND flights.flight_Name = :flight_Name AND
	// flights.offercode = :offercode",
	// nativeQuery = true)
	// List<FlightDetails> getAvailableFlights(@Param("source") String source,
	// @Param("destination") String destination, @Param("departure") Timestamp
	// departure, @Param("price") int price,
	// @Param("duration") long duration, @Param("flight_Name") String
	// flight_Name, @Param("offercode") String offercode);

}
