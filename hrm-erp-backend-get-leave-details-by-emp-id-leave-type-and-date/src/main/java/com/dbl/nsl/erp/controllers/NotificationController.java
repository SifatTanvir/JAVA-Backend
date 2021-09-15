package com.dbl.nsl.erp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.dbl.nsl.erp.payload.request.Message;

@RestController
public class NotificationController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/send_notification")
	public void greeting() throws InterruptedException {
		Message message = new Message("You have received a new notification");
		//Thread.sleep(2000);
		simpMessagingTemplate.convertAndSend("/tafuri_news", message);
	}

}
