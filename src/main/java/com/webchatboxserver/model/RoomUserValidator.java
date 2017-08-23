package com.webchatboxserver.model;

public class RoomUserValidator {

	public boolean validate(User user, Room room, String urlString) {
		if (room.getChatroomId().equals(urlString)) {
			return room.isAllowed(user);
		} else {
			return false;
		}
	}

}
