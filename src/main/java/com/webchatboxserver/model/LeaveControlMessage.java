package com.webchatboxserver.model;

import java.util.Date;

public class LeaveControlMessage extends Message {

	private Date time = new Date();
	private final String control = "leave";
	
	public LeaveControlMessage() {}
	
	public LeaveControlMessage(Message message) {
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
