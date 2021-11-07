package com.daar.CvIndex;

import com.daar.CvIndex.services.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CvIndexApplication {


	@Autowired
	private CvService cvService;

	public static void main(String[] args) {
		SpringApplication.run(CvIndexApplication.class, args);

	}


}
