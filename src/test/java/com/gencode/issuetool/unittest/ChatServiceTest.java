package com.gencode.issuetool.unittest;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gencode.issuetool.service.ChatService;

@SpringBootTest
public class ChatServiceTest {
	@Autowired
	ChatService chatService;
	
	@DisplayName("TEST Spring @Autowired Integration")
	@Test
	void testGet() {
		chatService.getAllMessageList().ifPresent(e->{
			System.out.println("=============================================");
			System.out.println("=============================================");
			System.out.println("=============================================");
			System.out.println(e.toString());
			System.out.println("=============================================");
			System.out.println("=============================================");
			System.out.println("=============================================");
		});
		//assertNotNull(chatService.l);
	}
}
