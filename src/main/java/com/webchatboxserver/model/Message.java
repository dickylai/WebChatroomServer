package com.webchatboxserver.model;

public class Message {

	private User from;
	private String content;
	
	public Message(User from, String content) {
		this.from = from;
		this.content = content;
	}

	public User getFrom() {
		return from;
	}
	
	public void setFrom(User from) {
		this.from = from;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
