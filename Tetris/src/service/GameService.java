package service;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dto.GameDto;
import dto.Player;
import entity.GameAct;

public class GameService {

	private GameDto dto;
	private static Random random = new Random();

	//地图的行数和列数
	private static final int GAMEZONE_H = 18;
	private static final int GAMEZONE_W = 10;
	//消多少行升一级
	private static final int LEVELL_UP = 20;
	//一次消一行加10分，一次消2行加30分，一次消3行加50分，一次消4行加80分，
	private static Map<Integer,Integer> PLUS_POINT;
	public GameService(GameDto dto) {
		this.dto = dto;
		
		PLUS_POINT = new HashMap<Integer, Integer>();
		PLUS_POINT.put(1, 10);
		PLUS_POINT.put(2, 30);
		PLUS_POINT.put(3, 50);
		PLUS_POINT.put(4, 80);
	}

	public void keyUp() {
		synchronized (this.dto) {
		this.dto.getGameAct().round(this.dto.getGameMap());
		}
	}

	public void keyDown() {
		synchronized (this.dto) {
		//向下是否移动
		if(this.dto.getGameAct().move(0, 1,this.dto.getGameMap())){
			return;
		}
		boolean[][] map = this.dto.getGameMap();
		//point存放的是方块坐标
		Point[] points = this.dto.getGameAct().getActPoint();
		for (int i = 0; i < points.length; i++) {
			map[points[i].x][points[i].y] = true;
		}
		//判断消行，并获取经验值		
		int addExp = this.plusExp();
		//如果发生消行
		if(addExp>0){
			//增加经验值
			this.plusPoint(addExp);
		}
		//重新刷一个方块
		this.dto.getGameAct().init(this.dto.getNext());
		//下一个方块
		this.dto.setNext(random.nextInt(6));
		//判断游戏是否失败
		if(isLose()){
			//设置游戏开始按钮为false
			this.dto.changeStart(false);
		}
		}
	}

	public void keyLeft() {
		synchronized (this.dto) {
		this.dto.getGameAct().move(-1, 0,this.dto.getGameMap());
		}
	}

	public void keyRight() {
		synchronized (this.dto) {
		this.dto.getGameAct().move(1, 0,this.dto.getGameMap());
		}
	}	
	public void keyPause() {
		this.dto.changePause();
//		System.out.println(this.dto.isPause());
		
	}

	private boolean isLose() {
		Point[] actPoint = this.dto.getGameAct().getActPoint();
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoint.length; i++) {
			if(map[actPoint[i].x][actPoint[i].y]){
				return true;
			}
		}
		return false;
	}

	private void plusPoint(int addExp) {
		int lv = this.dto.getNowLevel();
		int rmline = this.dto.getNowRemoveLine();
		int score = this.dto.getNowPoint();
		if(rmline%LEVELL_UP+addExp>=LEVELL_UP){
			this.dto.setNowLevel(++lv);
		}
		this.dto.setNowRemoveLine(rmline+addExp);
		this.dto.setNowPoint(score+PLUS_POINT.get(addExp));
		
	}

	private int plusExp() {
		boolean[][] map = this.dto.getGameMap();
		int exp=0;
		//扫描地图看是否有消行
		for (int y = 0; y < GAMEZONE_H; y++) {
			//判断是否可消行
			if(isCanRemoveLine(y,map)){
				//消行
				this.removeLine(y,map);
				exp++;
			}
		}
		return exp;
	}
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GAMEZONE_W; x++) {
			for (int y = rowNumber; y >0; y--) {
				map[x][y] = map[x][y-1];
			}
			map[x][0] = false;
		}
		
	}

	private boolean isCanRemoveLine(int y,boolean[][] map) {
		for (int x = 0; x < GAMEZONE_W; x++) {
			if(!map[x][y]){
				return false;
			}
		}
		return true;
	}

	public void testA() {
		//作弊健
		this.plusPoint(2);
	}
	
	public void setDbRecord(List<Player> players){
		this.dto.setDbRecord(players);
	}
	public void setDiskRecord(List<Player> players){
		this.dto.setDiskRecord(players);
	}

	public void startGame() {
		this.dto.setNext(random.nextInt(6));
		GameAct act = new GameAct(random.nextInt(6));
		this.dto.setGameAct(act);
		this.dto.changeStart(true);
		this.dto.dtoInit();
	}


}
