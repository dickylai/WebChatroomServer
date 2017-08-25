package com.webchatboxserver.model;

import java.util.Date;

public class OutputMessage extends Message {

	private Date time = new Date();

	private String chatroomId;

	public OutputMessage() {}
	
	public OutputMessage(Message message, String chatroomId) {
		super(message.getFrom(), message.getContent());
		this.chatroomId = chatroomId;
		this.time = new Date();
	}
	
	public String getChatroomId() {
		return chatroomId;
	}
	
	public void setChatroomId(String chatroomId) {
		this.chatroomId = chatroomId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
