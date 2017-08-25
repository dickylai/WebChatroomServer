package com.webchatboxserver.model;

import java.util.Date;

public class JoinControlMessage extends Message {

	private Date time = new Date();
	private final String control = "join";
	
	public JoinControlMessage() {}
	
	public JoinControlMessage(Message message) {
		super(message.getFrom(), message.getContent());
	}

	public String getControl() {
		return control;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
