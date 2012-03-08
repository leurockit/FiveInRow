package com.tao.entity;

public class Table {
	private Player black;
	private Player white;
	private Boolean[][] board;
	private Boolean isBlackTurn;
	private int lastX;
	private int lastY;

	public Table() {
		super();
		board = new Boolean[15][15];
		isBlackTurn = true;
		lastX = -1;
		lastY = -1;
	}

	public String get(Player player) {
		if (lastX == -1 || lastY == -1) {
			System.out.println("empty board");
			return null;
		}
		if (board[lastX][lastY]==isPlayerBlack(player)) {
			System.out.println("don't get your self");
			return null;
		}
		return String.valueOf(lastX) + "," + String.valueOf(lastY) + ","
				+ String.valueOf(board[lastX][lastY]) + ",";
	}

	public boolean put(int x, int y, Player player) {
		if (isPlayerBlack(player) != isBlackTurn) {
			return false;
		}
		if (null != board[x][y]) {
			return false;
		}
		try {
			board[x][y] = isBlackTurn;
			System.out.println("put on point:" + x + "," + y + "with: "
					+ isBlackTurn);
			isBlackTurn = !isBlackTurn;
			this.lastX = x;
			this.lastY = y;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Player getBlack() {
		return black;
	}

	public int getLastX() {
		return lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public Player getWhite() {
		return white;
	}

	private void setBlack(Player black) {
		this.black = black;
	}

	private void setWhite(Player white) {
		this.white = white;
	}

	// null: empty table, true: seats on black, false: seats on white
	public Boolean isPlayerBlack(Player player) {
		if (getBlack() != null && getBlack().equals(player)) {
			return true;
		}
		if (getWhite() != null && getWhite().equals(player)) {
			return false;
		}
		return null;
	}

	public Player getAdversary(Player player) {
		Boolean isPlayerBlack = isPlayerBlack(player);
		if (null == isPlayerBlack) {
			return null;
		}
		if (isPlayerBlack) {
			return this.getWhite();
		} else {
			return this.getBlack();
		}
	}

	public synchronized Boolean playerSit(Player player) {
		if (!isAvailable()) {
			return false;
		}
		if (black == null) {
			this.setBlack(player);
		} else {
			this.setWhite(player);
		}
		return true;
	}

	public void playerLeave(Player player) {
		Boolean isBlack = isPlayerBlack(player);
		if (isBlack == null) {
			return;
		}
		if (isBlack) {
			setBlack(null);
		} else {
			setWhite(null);
		}
	}

	public boolean isAvailable() {
		if (black != null && white != null) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
