package com.at.asset_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableCaching
@SpringBootApplication
public class AssetTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetTrackerApplication.class, args);
	}

}
