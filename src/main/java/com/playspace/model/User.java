package com.playspace.model;

public class User {

	private Integer userId;
	private String sessionKey;
	private long sessionKeyCreationTime;
	private int score;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public long getSessionKeyCreationTime() {
		return sessionKeyCreationTime;
	}

	public void setSessionKeyCreationTime(long sessionKeyCreationTime) {
		this.sessionKeyCreationTime = sessionKeyCreationTime;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
