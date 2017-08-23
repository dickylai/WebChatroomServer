package com.webchatboxserver.model;

import org.junit.*;

import com.webchatboxserver.model.Room;
import com.webchatboxserver.model.RoomUserValidator;
import com.webchatboxserver.model.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RoomUserValidatorTest {

	private final RoomUserValidator validator = new RoomUserValidator();
	private final User user = mock(User.class);
	private final Room room = mock(Room.class);
	private final String urlString = "testId";
	
	@Test
	public void validator_can_compare_the_room_id_and_id_from_url_and_return_false() {
		when(room.getChatroomId()).thenReturn("notIdentical");
		boolean isValidated = validator.validate(user, room, urlString);
		
		verify(room).getChatroomId();
		
		assertFalse(isValidated);
	}
	
	@Test
	public void validator_can_compare_the_room_id_and_id_from_url_and_check_if_a_user_is_allowed_then_return_true() {
		when(room.getChatroomId()).thenReturn("testId");
		when(room.isAllowed(user)).thenReturn(true);
		boolean isValidated = validator.validate(user, room, urlString);
		
		verify(room).getChatroomId();
		verify(room).isAllowed(user);
		
		assertTrue(isValidated);
	}
	
	@Test
	public void validator_can_compare_the_room_id_and_id_from_url_and_check_if_a_user_is_not_allowed_then_return_false() {
		when(room.getChatroomId()).thenReturn("testId");
		when(room.isAllowed(user)).thenReturn(false);
		boolean isValidated = validator.validate(user, room, urlString);
		
		verify(room).getChatroomId();
		verify(room).isAllowed(user);
		
		assertFalse(isValidated);
	}
}
