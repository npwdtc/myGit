package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerPoint extends Layer {

	
	private final int pointY  = PADDING;
	
	
	private final int rmlineY = Img.IMAGE_RMLINE.getHeight(null)+(PADDING<<1);
	
	private static int POINT_BIT = 5;
	private static int POINT_X=0;
	private static int expY;
	private static int expW;
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		POINT_X = this.w - NUMBER_W*POINT_BIT-(PADDING<<1);
		expY = this.rmlineY+Img.IMAGE_RMLINE.getHeight(null)+PADDING;
		expW = this.w - (PADDING<<1);
	}

	@Override
	public void paint(Graphics g) {
		this.createWindow(g);
		
		//分数
		g.drawImage(Img.IMAGE_POINT, this.x + PADDING, this.y + pointY, null);
		this.drawNumLeftPad(POINT_X, pointY-5, this.dto.getNowPoint(), 5, g);
		//消行
		g.drawImage(Img.IMAGE_RMLINE, this.x + PADDING, this.y + rmlineY, null);
		this.drawNumLeftPad(POINT_X, rmlineY-5, this.dto.getNowRemoveLine(), 5, g);
		//进度条
		double percent = (double)(this.dto.getNowRemoveLine()%20)/20;
		String strPoint = Integer.toString(this.dto.getNowPoint());
		drawBar(expY, "下一级","", percent, g);
		
	}

	
}
