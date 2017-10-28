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

	//��ͼ������������
	private static final int GAMEZONE_H = 18;
	private static final int GAMEZONE_W = 10;
	//����������һ��
	private static final int LEVELL_UP = 20;
	//һ����һ�м�10�֣�һ����2�м�30�֣�һ����3�м�50�֣�һ����4�м�80�֣�
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
		//�����Ƿ��ƶ�
		if(this.dto.getGameAct().move(0, 1,this.dto.getGameMap())){
			return;
		}
		boolean[][] map = this.dto.getGameMap();
		//point��ŵ��Ƿ�������
		Point[] points = this.dto.getGameAct().getActPoint();
		for (int i = 0; i < points.length; i++) {
			map[points[i].x][points[i].y] = true;
		}
		//�ж����У�����ȡ����ֵ		
		int addExp = this.plusExp();
		//�����������
		if(addExp>0){
			//���Ӿ���ֵ
			this.plusPoint(addExp);
		}
		//����ˢһ������
		this.dto.getGameAct().init(this.dto.getNext());
		//��һ������
		this.dto.setNext(random.nextInt(6));
		//�ж���Ϸ�Ƿ�ʧ��
		if(isLose()){
			//������Ϸ��ʼ��ťΪfalse
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
		//ɨ���ͼ���Ƿ�������
		for (int y = 0; y < GAMEZONE_H; y++) {
			//�ж��Ƿ������
			if(isCanRemoveLine(y,map)){
				//����
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
		//���׽�
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
