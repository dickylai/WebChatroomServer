package com.webchatboxserver.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webchatboxserver.model.*;

@Controller
public class WebController {

	@Resource(name = "userRegistrator")
	private UserRegistrator userRegistrator;
	@Resource(name = "roomRegistrator")
	private RoomRegistrator roomRegistrator;
	@Resource(name = "roomUserValidator")
	private RoomUserValidator roomUserValidator;

	public WebController() {
	}

	public WebController(UserRegistrator userRegistrator, RoomRegistrator roomRegistrator,
			RoomUserValidator roomUserValidator) {
		this.userRegistrator = userRegistrator;
		this.roomRegistrator = roomRegistrator;
		this.roomUserValidator = roomUserValidator;
	}

	@RequestMapping(value = "/")
	public String goToHomePage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		Room room = (Room) session.getAttribute("room");

		if (user == null && room == null) {
			model.addAttribute("user", new RegisterUser());
			model.addAttribute("rooms", roomRegistrator.getRooms());
			return "home";
		} else {
			return "redirect:/chatroom/" + room.getChatroomId();
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, RegisterUser registerUser) {
		String nickname = registerUser.getNickname();
		String chatroomId = registerUser.getChatroomId();

		Room room = roomRegistrator.getRoom(chatroomId);
		User user = userRegistrator.register(nickname, room);
		if (user == null) {
			return "redirect:/";
		} else {
			session.setAttribute("user", user);
			session.setAttribute("room", room);
			return "redirect:/chatroom/" + chatroomId;
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		Room room = (Room) session.getAttribute("room");

		roomRegistrator.deregister(room, user);

		model.addAttribute("user", new RegisterUser());
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/chatroom/{chatroomId}")
	public String goToChatRoom(HttpSession session, Model model, @PathVariable String chatroomId) {
		User user = (User) session.getAttribute("user");
		Room room = (Room) session.getAttribute("room");

		if (user != null && room != null) {
			model.addAttribute("chatroomId", chatroomId);
			model.addAttribute("isAllowed", this.roomUserValidator.validate(user, room, chatroomId));
			return "chatroom";
		} else {
			return "redirect:/";
		}
	}

	@MessageMapping(value = "/chatroom/{chatroomId}/join")
	@SendTo("/topic/messages/{chatroomId}")
	public JoinControlMessage join(@DestinationVariable("chatroomId") String chatroomId, Message message) {
		message.setContent(message.getContent().replace("<script>", "").replace("</script>", ""));
		return new JoinControlMessage(message);
	}

	@MessageMapping(value = "/chatroom/{chatroomId}/leave")
	@SendTo("/topic/messages/{chatroomId}")
	public LeaveControlMessage leave(@DestinationVariable("chatroomId") String chatroomId, Message message) {
		message.setContent(message.getContent().replace("<script>", "").replace("</script>", ""));
		return new LeaveControlMessage(message);
	}

	@MessageMapping(value = "/chatroom/{chatroomId}/message")
	@SendTo("/topic/messages/{chatroomId}")
	public OutputMessage send(@DestinationVariable("chatroomId") String chatroomId, Message message) {
		message.setContent(message.getContent().replace("<script>", "").replace("</script>", ""));
		return new OutputMessage(message, chatroomId);
	}
}
