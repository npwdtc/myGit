package ui;

import java.awt.Graphics;
import java.awt.Point;

import entity.GameAct;

public class LayerGame extends Layer {

	private static int SIZE_ROL = 5;
	private static final int LOST_IDX = 8;

	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.createWindow(g);
		GameAct act =  dto.getGameAct();
		if (act!=null) {
			Point[] points = act.getActPoint();
			this.drawShadow(points, true, g);
			// 打印方块
			this.drawMainAct(points, g);
		}
		// 打印地图
		this.drawMap(g);
		if(this.dto.isPause()){
			drawImageCenter(Img.PAUSES,g);
		}
	}

	private void drawMap(Graphics g) {
		boolean[][] map = this.dto.getGameMap();
		// 计算当前堆积方块的颜色
		int lv = this.dto.getNowLevel();
		int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;

		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					drawActByPoint(x, y, imgIdx, g);
				}
			}
		}
	}

	private void drawMainAct(Point[] points, Graphics g) {
		int typeCode = dto.getGameAct().getTypeCode();
		for (int i = 0; i < points.length; i++) {
			drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);
		}
	}

	private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
		imgIdx= this.dto.isStart()?imgIdx:LOST_IDX; 
		g.drawImage(Img.ACT, this.x + (x << SIZE_ROL) + 7, this.y
				+ (y << SIZE_ROL) + 7, this.x + (x + 1 << SIZE_ROL) + 7, this.y
				+ (y + 1 << SIZE_ROL) + 7, imgIdx << SIZE_ROL, 0,
				(imgIdx + 1) << SIZE_ROL, 1 << SIZE_ROL, null);
	}

	private void drawShadow(Point[] points, boolean isShowShadow, Graphics g) {
		if (!isShowShadow) {
			return;
		}
		int leftX = 9;
		int rightX = 0;
		for (Point p : points) {
			leftX = leftX > p.x ? p.x : leftX;
			rightX = rightX < p.x ? p.x : rightX;
		}
		g.drawImage(Img.SHADOW, this.x + SIZE + (leftX << SIZE_ROL), this.y
				+ SIZE, (rightX - leftX + 1) << SIZE_ROL, this.h - (SIZE << 1),
				null);
	}
}
