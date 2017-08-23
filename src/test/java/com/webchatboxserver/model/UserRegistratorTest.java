package com.webchatboxserver.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import com.webchatboxserver.model.Room;
import com.webchatboxserver.model.User;
import com.webchatboxserver.model.UserRegistrator;

public class UserRegistratorTest {

	private UserRegistrator registrator;
	
	@Before
	public void setUp() {
		registrator = new UserRegistrator();
	}
	
	@Test
	public void registrator_can_create_new_user() {
		Room room = mock(Room.class);
		when(room.register(any(User.class))).thenReturn(true);
		User user = registrator.register("dicky", room);
		
		assertEquals("dicky", user.getNickname());
	}
	
	@Test
	public void registrator_cannot_create_user_with_duplicated_name_for_the_same_room() {
		Room room = mock(Room.class);
		when(room.register(any(User.class))).thenReturn(true).thenReturn(false);
		User user1 = registrator.register("dicky", room);
		User user2 = registrator.register("dicky", room);
		
		assertEquals("dicky", user1.getNickname());
		assertNull(user2);
	}
}
