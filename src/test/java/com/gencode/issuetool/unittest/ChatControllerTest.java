package com.gencode.issuetool.unittest;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.gencode.issuetool.client.LogWrapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {
	LogWrapper logger = new LogWrapper(ChatControllerTest.class);
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void getChatList() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(
						new URL("http://localhost:"+port+"/prototype/chat"+"/list").toString()
						, String.class);
		logger.info(response.getBody());
		assertEquals("Hello Chat", response.getBody());
	}
	

}
