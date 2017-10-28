package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import dto.Player;

public abstract class LayerData extends Layer {

	private static final int MAX_ROW = 5;
	/**
	 * 起始Y坐标
	 */
	private static int START_Y = 0;
	/**
	 * 间距
	 */
	private static int SPA = 0;

	public LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		SPA = (this.h - (BAR_H + 4) * 5 - (PADDING << 1) - Img.WINDOW_DB
				.getHeight(null)) / 5;
		START_Y = PADDING + Img.WINDOW_DB.getHeight(null) + SPA;
	}

	@Override
	protected abstract void paint(Graphics g);

	protected void showData(Image imageTitle, List<Player> players, Graphics g) {
		g.drawImage(imageTitle, this.x + PADDING, this.y + PADDING, null);
		int nowPoint = this.dto.getNowPoint();
		for (int i = 0; i < MAX_ROW; i++) {
			Player player = players.get(i);
			int recodePoint = player.getPoint();
			double percent = (double) nowPoint / recodePoint;
			percent = percent > 1 ? 1.0 : percent;
			
			int recode = recodePoint;//==0?null:recodePoint;
			drawBar(START_Y + i * (SPA + BAR_H + 4), player.getName(), recode
					+ "", percent, g);
		}
	}

}
