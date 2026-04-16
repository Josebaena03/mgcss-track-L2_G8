package com.mgcss.l2g8;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class L2g8ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainDebeInvocarSpringApplicationRun() {
		String[] args = new String[] {"--spring.main.web-application-type=none"};

		try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
			L2g8Application.main(args);
			springApplication.verify(() -> SpringApplication.run(L2g8Application.class, args));
		}
	}

	@Test
	void debeCrearInstanciaDeAplicacion() {
		new L2g8Application();
	}

}
