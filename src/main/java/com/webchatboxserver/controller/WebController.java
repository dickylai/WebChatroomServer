package com.webchatboxserver.controller;

import javax.servlet.http.HttpSession;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webchatboxserver.model.*;

public class WebController {

	@RequestMapping(value="/")
	public String goToHomePage(Model model) {
		model.addAttribute("user", new RegisterUser());
		return "home";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, RegisterUser registerUser, UserRegistrator userRegistrator, RoomRegistrator roomRegistrator) {
		String nickname = registerUser.getNickname();
		String chatroomId = registerUser.getChatroomId();

		Room room = roomRegistrator.getRoom(chatroomId);		
		User user = userRegistrator.register(nickname, room);
		session.setAttribute("user", user);
		session.setAttribute("room", room);
		return "redirect:/chatroom/" + chatroomId;
	}

	@RequestMapping(value="/logout")
	public String logout(HttpSession session, Model model, RoomRegistrator roomRegistrator) {
		User user = (User) session.getAttribute("user");
		Room room = (Room) session.getAttribute("room");
		
		roomRegistrator.deregister(room, user);
		
		model.addAttribute("user", new RegisterUser());
		session.invalidate();
		return "home";
	}

	@RequestMapping(value="/chatroom/{chatroomId}")
	public String goToChatRoom(HttpSession session, Model model, @PathVariable String chatroomId, RoomUserValidator validator) {
		User user = (User) session.getAttribute("user");
		Room room = (Room) session.getAttribute("room");

		model.addAttribute("isAllowed", validator.validate(user, room, chatroomId));

		return "chatroom";
	}
	
	@MessageMapping(value="/chatroom/{chatroomdId}")
	@SendTo("/chatroomId/messages")
	public OutputMessage send(@DestinationVariable("chatroomId") String chatroomId, Message message) {
		return new OutputMessage(message, chatroomId);
	}

}
