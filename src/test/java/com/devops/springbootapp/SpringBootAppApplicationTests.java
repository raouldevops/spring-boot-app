package com.devops.springbootapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootAppApplicationTests {
	@Autowired
	private SpringBootAppApplication springBootAppApplication;
	@Test
	void contextLoads() {
	}

	@Test
	void testHello() {
		System.out.println("Hello ceci est un test unitaire 1");
	}

	@Test
	void testHome() {
		String actual = springBootAppApplication.Home();
		Assertions.assertEquals("index", actual);
	}

}
