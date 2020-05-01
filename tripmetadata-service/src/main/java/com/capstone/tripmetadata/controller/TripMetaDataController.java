package com.capstone.tripmetadata.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.tripmetadata.model.TripMetaData;
import com.capstone.tripmetadata.service.TripMetadataService;

@RestController
@RequestMapping("/api/tripmetadata")
public class TripMetaDataController {
	
		@Autowired
	 	private TripMetadataService tripservice;

		@GetMapping("")
	    @ResponseStatus(code= HttpStatus.OK)
	    public List<TripMetaData> findAll(@RequestParam Map<String, String> params){
	        return tripservice.findAll(params);
	    }
		  

		@GetMapping("/{tripId}")
	    @ResponseStatus(code= HttpStatus.OK)
	    public TripMetaData getTrip(@PathVariable("tripId") Long id) {
			return tripservice.getUserByTripId(id);
	    }
		
		@DeleteMapping("/{tripId}")
	    @ResponseStatus(code= HttpStatus.OK)
	    public String removeTrip(@PathVariable("tripId") Long id) {
	        return tripservice.removeTrip(id);
	    }
		
		

	    @PostMapping("")
	    @ResponseStatus(HttpStatus.OK)
	    public void createUser(@RequestBody @Valid TripMetaData trip) {
	        tripservice.createTrip(trip);
	    }

	    @PutMapping("/{tripId}")
	    @ResponseStatus(HttpStatus.OK)
	    public void updateUser(@PathVariable("tripId") Long id,
                @RequestBody TripMetaData trip) {

	        tripservice.updateTrip(id,trip);
	    }

	    
		@GetMapping("/health-status")
		public String healthStatus(){
			return "Trip Metadata Service UP and RUNNING";
			
		}

}
