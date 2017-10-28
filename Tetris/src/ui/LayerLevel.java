package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerLevel extends Layer {

	

	private static int NUMBER_W = Img.IMAGE_NUM.getWidth(null) / 10;
	private static int NUMBER_H = Img.IMAGE_NUM.getHeight(null);

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.createWindow(g);
		//�ȼ�
		int centerX = this.w-Img.WINDOW_LEVEL.getWidth(null)>>1;
		g.drawImage(Img.WINDOW_LEVEL, this.x+centerX, this.y + PADDING, null);
		//����
		drawNumLeftPad(centerX, 60, this.dto.getNowLevel(), 2,g);
	}

	

}
