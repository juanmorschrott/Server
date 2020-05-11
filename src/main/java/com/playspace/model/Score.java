package com.playspace.model;

import java.util.List;

public class Score {

	private int level;

	/**
	 * This value will represent the user foreign key
	 */
	private int userId;

	private List<Integer> points;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public void setPoints(List<Integer> points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Score [level=" + level + ", userId=" + userId + ", points=" + points + "]";
	}

}
