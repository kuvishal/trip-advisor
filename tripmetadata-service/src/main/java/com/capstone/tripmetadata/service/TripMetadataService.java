package com.capstone.tripmetadata.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.capstone.tripmetadata.model.TripMetaData;
import com.capstone.tripmetadata.repo.TripMetadataRepository;

@Service
public class TripMetadataService {
	
	@Autowired
	private TripMetadataRepository tripRepo;
	
	public Long createTrip(@Valid TripMetaData trip){
		 
		  ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact());
		  Example<TripMetaData> ex = Example.of(trip, matcher);
		if (tripRepo.exists(ex)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("Trip already exists!", trip.getName()));
		}

		
	        TripMetaData tripdata = ex.getProbe();

	        tripdata.setDesc(trip.getDesc()); 
	        tripdata.setDuration(trip.getDuration()); 
	        tripdata.setName(trip.getName()); 
	        tripdata.setPrice(trip.getPrice());


	        TripMetaData newTrip = tripRepo.save(tripdata);
	        return newTrip.getId();
	}


	public String updateTrip(Long id,  TripMetaData trip) {
		Optional<TripMetaData> tripOptional = tripRepo.findById(id);

		TripMetaData tripdata = tripOptional.orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Trip doesn't exist!")));

		tripdata.setDesc(trip.getDesc());
		tripdata.setDuration(trip.getDuration());
		tripdata.setName(trip.getName());
		tripdata.setPrice(trip.getPrice());

		TripMetaData newTrip = tripRepo.save(tripdata);
		return "Updated sucessfully";
	}


	public TripMetaData getUserByTripId(Long id){
		return tripRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Trip doesn't exist!")));
	}

	public String removeTrip(Long id) {
		Optional<TripMetaData> trip = tripRepo.findById(id);
		if (trip.isPresent()) {
			tripRepo.deleteById(id);
			return "Deleted the trip" + trip.get().getName();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Trip doesn't exist!"));
		}
	}


	public List<TripMetaData> findAll(Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return tripRepo.findAll();
		} else {
			Iterator<String> iterator = params.keySet().iterator();
			TripMetaData trip = new TripMetaData();   
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = params.get(key);
				if (value != null) {
					switch (key) {
					case "destination":
						trip.setDestination(value);
						break;
					case "desc":
						trip.setDesc(value);
						break;
					case "name":
						trip.setName(value);
						break;
					case "duration":
						trip.setDuration(Integer.parseInt(value));
						break;
					case "price":
						trip.setPrice(Double.parseDouble(value));
						break;
					default:
						break;
					}
				}
			}
			
			ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnoreCase()
					.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id")
					.withIgnoreNullValues();
			Example<TripMetaData> ex = Example.of(trip, matcher);
			
			List<TripMetaData> resultList = tripRepo.findAll(ex);
			if (resultList == null || resultList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Trip doesn't exist!"));
			} else {
				return resultList;
			}
		}
	}
	
	
}
