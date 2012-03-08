package com.tao.entity;

public class Room {
	private Player black;
	private Player white;
	private Boolean[][] board;
	private Boolean isBlackTurn;
	private int lastX;
	private int lastY;
	private int maxIndex;
	private int victoryNum;
	private boolean victory;
	private boolean isPutFinished;
	
	public Room() {
		super();
		init();
	}

	private void init(){
		victoryNum = 5;
		maxIndex = 14;
		board = new Boolean[maxIndex + 1][maxIndex + 1];
		isBlackTurn = true;
		lastX = -1;
		lastY = -1;
		victory = false;
		isPutFinished = false;
	}
	
	public void restart(Player player){
		if(isPlayerBlack(player)){
			black.setReady(true);
		}else{
			white.setReady(true);
		}
		if(isAllReady()){
			init();
		}
	}
	
	public static void main(String[] args) {
		Room r = new Room();
		Player black = new Player();
		black.setName("black");
		Player white = new Player();
		white.setName("white");
		r.setBlack(black);
		r.setWhite(white);
		r.put(0, 0, black);r.put(5, 0, white);
		r.put(1, 1, black);r.put(5, 1, white);
		r.put(2, 2, black);r.put(5, 2, white);
		r.put(3, 3, black);r.put(5,3,white);
		r.put(4, 4, black);r.get(white);
		
		r.put(3, 3, black);r.put(5,3,white);
		r.put(4, 4, black);r.get(white);
	}

	public void showMap() {
		for (int x = 0; x <= maxIndex; x++) {
			for (int y = 0; y <= maxIndex; y++) {
				System.out.print(board[x][y] + ":");
			}
			System.out.println("");
		}
	}
	
	public boolean checkVictory(int x, int y) {
		//showMap();
		// get check area index
		int checkLength = victoryNum - 1;
		// vertical check: same y, x from -length to +length
		int fromX = x - checkLength < 0 ? 0 : x - checkLength;
		int toX = x + checkLength > maxIndex ? maxIndex : x + checkLength;
		int count = 0;
		for (int i = fromX; i <= toX; i++) {
			if (isBlackTurn == board[i][y]) {// same color
				count++;
				if (count == victoryNum) {
					// count reaches victory
					return true;
				}
			} else {// different color or null
				count = 0;
			}
		}
		// horizontal check: same x, y from -length to +length
		int fromY = y - checkLength < 0 ? 0 : y - checkLength;
		int toY = y + checkLength > maxIndex ? maxIndex : y + checkLength;
		count = 0;
		for (int i = fromY; i <= toY; i++) {
			if (isBlackTurn == board[x][i]) {// same color
				count++;
				if (count == victoryNum) {
					// count reaches victory
					return true;
				}
			} else {// different color or null
				count = 0;
			}
		}
		// backslash('\') check: from (x-length,y-length) to (x+length,y+length)
		count = 0;
		int slashX = x - checkLength;
		int slashY = y - checkLength;
		for (int i = 0; i < checkLength * 2 + 1; i++) {
			// must be in the slash range
			if (slashX >= 0 && slashY >= 0 && slashX <= maxIndex
					&& slashY <= maxIndex) {
				if (isBlackTurn == board[slashX][slashY]) {// same color
					count++;
					if (count == victoryNum) {
						// count reaches victory
						return true;
					}
				} else {// different color or null
					count = 0;
				}
			}
			slashX++;
			slashY++;
		}
		// slash('/') check: from (x-length,y+length) to (x+length,y-length)
		count = 0;
		slashX = x - checkLength;
		slashY = y + checkLength;
		for (int i = 0; i < checkLength * 2 + 1; i++) {
			// must be in the slash range
			if (slashX >= 0 && slashY >= 0 && slashX <= maxIndex
					&& slashY <= maxIndex) {
				if (isBlackTurn == board[slashX][slashY]) {// same color
					count++;
					if (count == victoryNum) {
						// count reaches victory
						return true;
					}
				} else {// different color or null
					count = 0;
				}
			}
			slashX++;
			slashY--;
		}
		return false;
	}

	public String get(Player player) {
		if (lastX == -1 || lastY == -1) {
//			System.out.println(player + " applying empty board, failed");
			return null;
		}
		if (board[lastX][lastY] == isPlayerBlack(player)) {
//			System.out.println(player + " get itself, failed");
			return null;
		}
		if(!isPutFinished){
			return null;
		}
		String color = board[lastX][lastY] ? "0" : "1";
		String vic = victory?"0":"1";
		String rs = String.valueOf(lastX) + "," + String.valueOf(lastY) + ","
				+ color+","+vic;
		System.out.println(player + " get: " + rs + " success.");
		return rs;
	}

	public String put(int x, int y, Player player) {
		if(victory){
			System.out.println("vicotry");
			return null;
		}
		if (isPlayerBlack(player) != isBlackTurn) {
			System.out.println(player + "put on " + x + "," + y
					+ ", failed, not the turn.");
			return null;
		}
		if (null != board[x][y]) {
			System.out.println(player + "put on " + x + "," + y
					+ ", failed, can't cover");
			return null;
		}
		isPutFinished = false;
		try {
			board[x][y] = isBlackTurn;
			this.lastX = x;
			this.lastY = y;
			victory = checkVictory(x, y);
			System.out.println("victory? " + victory);
			if(victory){
				black.setReady(false);
				white.setReady(false);
			}else{
				isBlackTurn = !isBlackTurn;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String color = board[lastX][lastY] ? "0" : "1";
		String vic = victory?"0":"1";
		String rs = String.valueOf(x) + "," + String.valueOf(y) + "," + color+","+vic;
		System.out.println("put on: " + rs + " success.");
		isPutFinished = true;
		return rs;
	}

	public String getBlackName() {
		if (this.black == null) {
			return "";
		}
		return black.getName();
	}

	public String getWhiteName() {
		if (this.white == null) {
			return "";
		}
		return white.getName();
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

	public boolean isVictory() {
		return victory;
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

	public boolean isAllReady(){
		if(black.isReady()&&white.isReady()){
			return true;
		}
		return false;
	}
	
	public boolean isAdversaryReady(Player player) {
		if (isPlayerBlack(player)) {
			return this.getWhite().isReady();
		}
		return this.getBlack().isReady();
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
		System.out.println("player leave "+player);
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
