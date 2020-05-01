package com.capstone.tripmetadata;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.capstone.tripmetadata.model.TripMetaData;
import com.capstone.tripmetadata.repo.TripMetadataRepository;

@Component
public class MyRunner implements CommandLineRunner {

	@Autowired
	private TripMetadataRepository tripRepo;
	@Override
	public void run(String... args) throws Exception {
		
		ArrayList<TripMetaData> trips = new ArrayList();
		trips.add(new TripMetaData("beautiful karnatka", "Enjoy majestic beauty of karnataka", 41000, 4, "karnataka"));
		trips.add(new TripMetaData("Dilwali Delhi", "Delhi Trip", 50000, 5, "Delhi"));
		trips.add(new TripMetaData("majestic kerala", "Enjoy majestic beauty of kerala", 39999, 3, "kerala"));
		trips.add(new TripMetaData("Tirupati Balaji", "Temple tour of Tirupati", 10000, 2, "Tirupati"));
		trips.add(new TripMetaData("Heavenly Kashmir", "Eternal Beauty of Kashmir", 90000, 7, "Kashmir"));
		
		tripRepo.saveAll(trips);

	}

}
