package com.insightweb.datastreamingserver;

import com.insightweb.datastreamingserver.usecase.TrackerEventProcessing;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DataStreamingServerApplication implements CommandLineRunner {
	private final TrackerEventProcessing trackerEventProcessing;

	public static void main(String[] args) {
		SpringApplication.run(DataStreamingServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		trackerEventProcessing.startStream();
	}
}
