package com.webchatboxserver.controller;

import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.webchatboxserver.controller.WebController;
import com.webchatboxserver.model.*;

public class WebControllerTest {

	private WebController controller;
	private User user;
	private Room room;
	private HttpSession session;
	private Model model;
	private RoomRegistrator roomRegistrator;
	private UserRegistrator userRegistrator;
	private RegisterUser registerUser;

	
	@Before
	public void setUp() {
		controller = new WebController();
		user = mock(User.class);
		room = mock(Room.class);
		session = mock(HttpSession.class);
		model = mock(Model.class);
		roomRegistrator = mock(RoomRegistrator.class);
		userRegistrator = mock(UserRegistrator.class);
		registerUser = mock(RegisterUser.class);
	}
	
	@Test
	public void client_can_go_to_home_page() {
		String page = controller.goToHomePage(model);
		
		verify(model).addAttribute(eq("user"), any(RegisterUser.class));
		assertEquals("home", page);
	}
	
	@Test
	public void client_can_login_and_register_the_chatroom_with_a_nickname() {
		when(registerUser.getNickname()).thenReturn("dicky");
		when(registerUser.getChatroomId()).thenReturn("1");
		when(roomRegistrator.getRoom("1")).thenReturn(room);
		when(userRegistrator.register("dicky", room)).thenReturn(user);
		
		String page = controller.login(session, registerUser, userRegistrator, roomRegistrator);
		
		verify(registerUser).getNickname();
		verify(registerUser).getChatroomId();
		
		verify(roomRegistrator).getRoom("1");
		verify(userRegistrator).register("dicky", room);
		verify(session).setAttribute("user", user);
		verify(session).setAttribute("room", room);
		assertEquals("redirect:/chatroom/1", page);
	}
	
	@Test
	public void client_can_logout_and_return_to_home_page() {
		when(session.getAttribute("room")).thenReturn((Room) room);
		when(session.getAttribute("user")).thenReturn((User) user);
		String page = controller.logout(session, model, roomRegistrator);
		
		verify(session).getAttribute("room");
		verify(session).getAttribute("user");
		verify(roomRegistrator).deregister(room, user);
		verify(model).addAttribute(eq("user"), any(RegisterUser.class));
		verify(session).invalidate();
		assertEquals("home", page);
	}
	
	@Test
	public void client_can_get_into_a_chatroom_if_he_has_the_correct_credential() {
		String urlString = "1";
		when(session.getAttribute("room")).thenReturn((Room) room);
		when(session.getAttribute("user")).thenReturn((User) user);
		RoomUserValidator validator = mock(RoomUserValidator.class);
		when(validator.validate(user, room, urlString)).thenReturn(true);
		String page = controller.goToChatRoom(session, model, urlString, validator);
		
		verify(session).getAttribute("room");
		verify(session).getAttribute("user");
		verify(validator).validate(user, room, urlString);
		verify(model).addAttribute("isAllowed", true);
		
		assertEquals("chatroom", page);
	}
	
	@Test
	public void client_can_get_into_a_chatroom_if_he_has_the_wrong_credential() {
		String urlString = "1";
		when(session.getAttribute("room")).thenReturn((Room) room);
		when(session.getAttribute("user")).thenReturn((User) user);
		RoomUserValidator validator = mock(RoomUserValidator.class);
		when(validator.validate(user, room, urlString)).thenReturn(false);
		String page = controller.goToChatRoom(session, model, urlString, validator);
		
		verify(session).getAttribute("room");
		verify(session).getAttribute("user");
		verify(validator).validate(user, room, urlString);
		verify(model).addAttribute("isAllowed", false);
		
		assertEquals("chatroom", page);
	}
	
	@Test
	public void client_can_send_message() {
		String chatroomId = "1";
		Message message = mock(Message.class);
		when(message.getFrom()).thenReturn(user);
		when(message.getContent()).thenReturn("hello");
		
		OutputMessage outputMessage = controller.send(chatroomId, message);
		
		assertEquals(chatroomId, outputMessage.getChatroomId());
		assertEquals(user, outputMessage.getFrom());
		assertEquals("hello", outputMessage.getContent());
	}
}
