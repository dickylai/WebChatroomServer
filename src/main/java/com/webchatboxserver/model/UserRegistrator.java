package com.webchatboxserver.model;

public class UserRegistrator {

	public User register(String nickname, Room room) {
		User user = new User(nickname);
		
		if (room.register(user)) {
			return user;
		} else {
			return null;
		}

	}

}
