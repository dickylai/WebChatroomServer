package com.webchatboxserver.model;

import java.util.*;

public class Room {

	private final String chatroomId;
	private List<User> users;

	public Room(String chatroomId) {
		this.chatroomId = chatroomId;
		users = new ArrayList<>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getChatroomId() == null) ? 0 : getChatroomId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (getChatroomId() == null) {
			if (other.getChatroomId() != null)
				return false;
		} else if (!getChatroomId().equals(other.getChatroomId()))
			return false;
		return true;
	}

	public String getChatroomId() {
		return chatroomId;
	}

	public boolean register(User user) {
		if (users.contains(user)) {
			return false;
		} else {
			users.add(user);
			return true;
		}
	}

	public int getTotalNumOfUsers() {
		return users.size();
	}

	public void deregister(User user) {
		users.remove(user);
	}

	public boolean isAllowed(User user) {
		if (users.contains(user)) {
			return true;
		} else {
			return false;
		}
	}

}
