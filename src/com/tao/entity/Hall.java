package com.tao.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Hall {
	private static List<Room> rooms = new ArrayList<Room>();
	private static List<Player> players = new ArrayList<Player>();
	private static final int TABLENUMBER = 10;

	static {
		for (int i = 0; i < TABLENUMBER; i++) {
			Room room = new Room();
			rooms.add(room);
		}
	}

	public Hall() {
		super();
	}

	public static void kickPlayers() {
		for (Player player : players) {
			Calendar now = Calendar.getInstance();
			now.add(Calendar.MINUTE, -1);
			Calendar playerAlive = Calendar.getInstance();
			playerAlive.setTime(player.getAliveDate());
			if (now.after(playerAlive)) {
				players.remove(player);
				for (Room room : rooms) {
					room.playerLeave(player);
				}
			}
		}
	}

	public static synchronized String getPlayerNames() {
		StringBuilder playerNames = new StringBuilder("");
		for (Room room : rooms) {
			playerNames.append(room.getBlackName()).append(",")
					.append(room.getWhiteName()).append(",");
		}
		return playerNames.toString();
	}

	public static boolean isPlayerInHall(Player player) {
		if (players.contains(player)) {
			return true;
		}
		return false;
	}

	public static boolean isPlayerInHall(String playerName) {
		for (Player player : players) {
			if (player.getName().equals(playerName)) {
				return true;
			}
		}
		return false;
	}

	public static List<Room> getRooms() {
		return rooms;
	}

	public static void playerExit(Player player) {
		players.remove(player);
	}

	public static boolean playerEnter(Player player) {
		if (players.size() == TABLENUMBER * 2) {
			return false;
		}
		players.add(player);
		return true;
	}

	public static List<Player> getPlayers() {
		return players;
	}

	public static Room findRoomByPlayer(Player player) {
		for (Room room : rooms) {
			if (room.isPlayerBlack(player) != null) {
				return room;
			}
		}
		return null;
	}

	public static boolean playerSit(Player player) {
		for (Room room : rooms) {
			if (room.playerSit(player)) {
				return true;
			}
		}
		return false;
	}

	public static boolean playerSitInRoom(Player player, int roomIndex) {
		// data validation
		if (player == null || roomIndex < 0 || roomIndex > 9) {
			return false;
		}
		// leave first
		Room room = findRoomByPlayer(player);
		if (room != null) {
			room.playerLeave(player);
		}
		return room.playerSit(player);
	}

	public static Boolean isPlayerBlack(Player player) {
		Room room = findRoomByPlayer(player);
		if (room == null) {
			return null;
		}
		return room.isPlayerBlack(player);
	}

}
