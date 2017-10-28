package entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GameAct {

	private Point[] actPoint;
	private int typeCode;
	private static int MIN_X=0;
	private static int MAX_X=9;
	private static int MIN_Y=0;
	private static int MAX_Y=17;
	
	private static List<Point[]> TYPE_CONFIG;

	static{
		TYPE_CONFIG = new ArrayList<Point[]>();
		TYPE_CONFIG.add(new Point[] {new Point(4, 0),
				new Point(3, 0),new Point(5, 0), new Point(6, 0) });
		TYPE_CONFIG.add(new Point[] {new Point(4, 0),
				new Point(3, 0),new Point(5, 0), new Point(4, 1) });
		TYPE_CONFIG.add(new Point[] {new Point(4, 0),
				new Point(3, 0),new Point(5, 0), new Point(3, 1) });
		TYPE_CONFIG.add(new Point[] {new Point(4, 0),
				new Point(5, 0),new Point(3, 1), new Point(4, 1) });
		TYPE_CONFIG.add(new Point[] {new Point(4, 0),
				new Point(5, 0),new Point(4, 1), new Point(5, 1) });
		TYPE_CONFIG.add(new Point[] {new Point(4, 0),
				new Point(3, 0),new Point(5, 0), new Point(5, 1) });
		TYPE_CONFIG.add(new Point[] {new Point(4, 0),
				new Point(3, 0),new Point(4, 1), new Point(5, 1) });
		
	}
	public GameAct(int typeCode) {
		this.init(typeCode);
	}
	public void init(int typeCode){
		this.typeCode = typeCode;
		Point[] temp = TYPE_CONFIG.get(typeCode);
		actPoint = new Point[temp.length];
		for (int i = 0; i < temp.length; i++) {
			actPoint[i]= new Point(temp[i].x,temp[i].y);
		}
	}

	public Point[] getActPoint() {
		return actPoint;
	}

	/**
	 * 方块移动
	 * @param moveX 水平方向上怎么移动
	 * @param moveY 竖直方向上怎么移动
	 * @param gameMap 二维数组，记录哪个位置已经存在方块
	 * @return
	 */
	public boolean move(int moveX, int moveY,boolean[][] gameMap) {
		for (int i = 0; i < actPoint.length; i++) {
			int newX = actPoint[i].x + moveX;
			int newY = actPoint[i].y + moveY;
			if(this.isOverZone(newX, newY,gameMap)){
				return false;
			}
		}
		for (int i = 0; i < actPoint.length; i++) {
			actPoint[i].x += moveX;
			actPoint[i].y +=moveY;
		}
		return true;
	}
	/**
	 * 方块的翻转
	 * @param gameMap 二维数组，记录哪个位置已经存在方块
	 */
	public void round(boolean[][] gameMap){
		if(this.getTypeCode()==4){
			return;
		}
		for (int i = 1; i < actPoint.length; i++) {
			int newX = actPoint[0].y+actPoint[0].x -actPoint[i].y;
			int newY = actPoint[0].y-actPoint[0].x +actPoint[i].x;
			if(this.isOverZone(newX, newY,gameMap)){
				return;
			}
		}
		for (int i = 0; i < actPoint.length; i++) {
			int newX = actPoint[0].y+actPoint[0].x -actPoint[i].y;
			int newY = actPoint[0].y-actPoint[0].x +actPoint[i].x;
			actPoint[i].x = newX;
			actPoint[i].y = newY;
		}
	}
	//判断是否超过区域
	private boolean isOverZone(int x,int y,boolean[][] gameMap){
		return x<MIN_X||x>MAX_X||y<MIN_Y||y>MAX_Y||gameMap[x][y];
	}
	public int getTypeCode() {
		return typeCode;
	}
	
}
