package com.webchatboxserver.model;

import java.util.*;

public class RoomRegistrator {

	private final Map<String, Room> rooms = new HashMap<String, Room>();
	
	public Room getRoom(String chatroomId) {
		if (rooms.get(chatroomId) != null) {
			return rooms.get(chatroomId);
		} else {
			Room newRoom = new Room(chatroomId);
			rooms.put(chatroomId, newRoom);
			return newRoom;
		}
	}
	
	public void deregister(Room room, User user) {
		room.deregister(user);
		
		if (room.getTotalNumOfUsers() == 0) {
			rooms.remove(room.getChatroomId());
		}
	}

	public int getTotalNumOfRooms() {
		return rooms.size();
	}
}
