package dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import util.GameFunction;

import entity.GameAct;


public class GameDto {

	private List<Player> dbRecord;
	private List<Player> diskRecord;
	
	private boolean[][] gameMap;
	
	private GameAct gameAct;
	
	private int next;
	private int nowLevel;
	private int nowPoint;
	private int nowRemoveLine;
	//开始控制
	private boolean start;
	//暂停控制
	private boolean pause;
	private long sleepTime;
	public GameDto(){
		dtoInit();
	}
	
	public void dtoInit(){
		this.gameMap = new boolean[10][18];
		this.nowLevel=1;
		this.nowPoint=0;
		this.nowRemoveLine=0;
		this.pause=false;
		this.sleepTime = GameFunction.getSleepTime(this.nowLevel);
	}
	
	public List<Player> getDbRecord() {
		return dbRecord;
	}
	public void setDbRecord(List<Player> dbRecord) {
		
		this.dbRecord = this.setFillPlayer(dbRecord);
	}
	public List<Player> getDiskRecord() {
		return diskRecord;
	}
	public void setDiskRecord(List<Player> diskRecord) {
		this.diskRecord = this.setFillPlayer(diskRecord);
	}
	private List<Player> setFillPlayer(List<Player> players){
		if(players==null){
			players = new ArrayList<Player>();
		}
		while(players.size()<5){
			players.add(new Player("No Data", 0));
		}
		Collections.sort(players);
		return players;
	}
	public boolean[][] getGameMap() {
		return gameMap;
	}
	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}
	public GameAct getGameAct() {
		return gameAct;
	}
	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getNowLevel() {
		return nowLevel;
	}
	public void setNowLevel(int nowLevel) {
		this.nowLevel = nowLevel;
		this.sleepTime = GameFunction.getSleepTime(this.nowLevel);
	}
	public int getNowPoint() {
		return nowPoint;
	}
	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}
	public int getNowRemoveLine() {
		return nowRemoveLine;
	}
	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void changeStart(boolean start) {
		this.start = !this.start;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}

	public long getSleepTime() {
		return sleepTime;
	}
}
