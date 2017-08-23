package com.webchatboxserver.model;

public class RegisterUser {

	private String nickname;
	private String chatroomId;

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public void setChatroomId(String chatroomId) {
		this.chatroomId = chatroomId;
	}
	
	public String getNickname() {
		return nickname;
	}

	public String getChatroomId() {
		return chatroomId;
	}
	
}
