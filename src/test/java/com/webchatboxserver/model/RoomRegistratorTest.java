package com.webchatboxserver.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import com.webchatboxserver.model.Room;
import com.webchatboxserver.model.RoomRegistrator;
import com.webchatboxserver.model.User;

public class RoomRegistratorTest {

	public RoomRegistrator registrator;
	
	@Before
	public void setUp() {
		registrator = new RoomRegistrator();
	}
	
	@Test
	public void registrator_can_show_total_number_of_chatrooms() {
		assertEquals(0, registrator.getTotalNumOfRooms());
	}
	
	@Test
	public void registrator_can_get_new_room_with_chatroomId_provided_if_no_room_matches_the_chatroomId() {
		Room room = registrator.getRoom("firstRoom");
		
		assertEquals(new Room("firstRoom"), room);
		assertEquals(1, registrator.getTotalNumOfRooms());

	}
	
	@Test
	public void registrator_can_get_a_room_with_chatroomId_provided_if_a_room_matches_the_chatroomId() {
		Room room1 = registrator.getRoom("firstRoom");
		Room room2 = registrator.getRoom("firstRoom");
		
		assertEquals(room1, room2);
		assertEquals(1, registrator.getTotalNumOfRooms());
	}
	
	@Test
	public void registrator_can_deregister_a_room_if_no_user_in_that_room() {
		Room room = registrator.getRoom("firstRoom");
		User user = mock(User.class);
		room.register(user);
		assertEquals(1, registrator.getTotalNumOfRooms());
		
		registrator.deregister(room, user);
		assertEquals(0, registrator.getTotalNumOfRooms());
	}
}
