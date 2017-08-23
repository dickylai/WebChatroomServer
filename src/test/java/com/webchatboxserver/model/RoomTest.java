package com.webchatboxserver.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import com.webchatboxserver.model.Room;
import com.webchatboxserver.model.User;

public class RoomTest {

	private Room room;
	
	@Before
	public void setUp() {
		room = new Room("testId");
	}
	
	@Test
	public void room_can_return_chatroom_id() {
		assertEquals("testId", room.getChatroomId());
	}
	
	@Test
	public void room_can_show_the_number_of_users_in_the_room() {
		int totalNum = room.getTotalNumOfUsers();
		
		assertEquals(0, totalNum);
	}
	
	@Test
	public void client_can_register_to_a_room() {
		User user = mock(User.class);
		room.register(user);
		int totalNum = room.getTotalNumOfUsers();
		
		assertEquals(1, totalNum);
	}
	
	@Test
	public void room_cannot_add_multiple_users_with_same_nickname() {
		User user1 = new User("dicky");
		User user2 = new User("dicky");

		assertTrue(room.register(user1));
		assertFalse(room.register(user2));
		
		assertEquals(1, room.getTotalNumOfUsers());
	}
	
	@Test
	public void client_can_deregister_to_a_room() {
		User user = mock(User.class);
		room.register(user);
		assertEquals(1, room.getTotalNumOfUsers());
		
		room.deregister(user);
		assertEquals(0, room.getTotalNumOfUsers());
	}
	
	@Test
	public void client_can_use_the_chatroom_after_registration() {
		User user = mock(User.class);
		room.register(user);
		assertTrue(room.isAllowed(user));
	}
	
	@Test
	public void client_cannot_use_the_chatroom_before_registration() {
		User user = mock(User.class);
		assertFalse(room.isAllowed(user));
	}
}
