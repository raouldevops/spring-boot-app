package com.devops.springbootapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootAppApplicationTests {

	private SpringBootAppApplication springBootAppApplication;

	public SpringBootAppApplicationTests(SpringBootAppApplication springBootAppApplication) {
		this.springBootAppApplication = springBootAppApplication;
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testHome() {
		String actual = springBootAppApplication.Home();
		Assertions.assertEquals("index", actual);//NOSONAR
	}

}
