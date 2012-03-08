package com.tao.entity;

import java.util.Date;

public class Player {
	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}

	private String name;
	private Date aliveDate;
	private boolean isReady;
	
	public Date getAliveDate() {
		return aliveDate;
	}

	public void setAliveDate() {
		this.aliveDate = new Date();
	}


	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Player player = (Player) obj;
			if (player == null) {
				return false;
			}
			if (this.getName().equals(player.getName())) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name!=null){
			this.name = name.trim();
		}
	}

	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}
}
