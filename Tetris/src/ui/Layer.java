package ui;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

public abstract class Layer {

	/**
	 * SIZE 
	 * PADDING
	 */
	protected static final int SIZE;
	protected static final int PADDING;
	static{
		//获得游戏配置
		FrameConfig fCfg = GameConfig.getFrameConfig();
		SIZE = fCfg.getBorder();
		PADDING = fCfg.getPadding();
	}
	/**
	 * 矩形方框的宽度和高度
	 */
	private static int IMG_W = Img.WINDOW_IMG.getWidth(null);
	private static int IMG_H = Img.WINDOW_IMG.getWidth(null);
	/**
	 * 字体
	 */
	private static Font DEF_FONT = new Font("黑体", Font.BOLD, 20);
	/**
	 * 进度条的宽度和高度
	 */
	protected static int BAR_H = Img.IMAGE_BAR.getHeight(null);
	protected static int BAR_W = Img.IMAGE_BAR.getWidth(null);
	protected static int IMG_RECT_W;
	/**
	 * 数字图片的宽度和高度
	 */
	protected static int NUMBER_W = Img.IMAGE_NUM.getWidth(null) / 10;
	protected static int NUMBER_H = Img.IMAGE_NUM.getHeight(null);
	
	/**
	 * 窗口左上角x坐标
	 */
	protected int x;
	/**
	 * 窗口左上角y坐标
	 */
	protected int y;
	/**
	 * 窗口宽度
	 */
	protected int w;
	/**
	 * 窗口高度
	 */
	protected int h;
	protected GameDto dto = null;
	public Layer(int x,int y,int w,int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		IMG_RECT_W = w - (PADDING<<1);
	}

	protected void createWindow(Graphics g) {

		// 左上
		g.drawImage(Img.WINDOW_IMG, x, y, x + SIZE, y + SIZE, 0, 0, SIZE, SIZE,
				null);
		// 中上
		g.drawImage(Img.WINDOW_IMG, x + SIZE, y, x + w - SIZE, y + SIZE, SIZE, 0,
				IMG_W - SIZE, SIZE, null);
		// 右上
		g.drawImage(Img.WINDOW_IMG, x + w - SIZE, y, x + w, y + SIZE, IMG_W - SIZE,
				0, IMG_W, SIZE, null);
		// 左中
		g.drawImage(Img.WINDOW_IMG, x, y + SIZE, x + SIZE, y + h - SIZE, 0, SIZE,
				SIZE, IMG_H - SIZE, null);
		// 中
		g.drawImage(Img.WINDOW_IMG, x + SIZE, y + SIZE, x + w - SIZE, y + h - SIZE,
				SIZE, SIZE, IMG_W - SIZE, IMG_H - SIZE, null);
		// 右中
		g.drawImage(Img.WINDOW_IMG, x + w - SIZE, y + SIZE, x + w, y + h - SIZE,
				IMG_W - SIZE, SIZE, IMG_W, IMG_H - SIZE, null);
		// 下左
		g.drawImage(Img.WINDOW_IMG, x, y + h - SIZE, x + SIZE, y + h, 0, IMG_H
				- SIZE, SIZE, IMG_H, null);
		// 下中
		g.drawImage(Img.WINDOW_IMG, x + SIZE, y + h - SIZE, x + w - SIZE, y + h,
				SIZE, IMG_H - SIZE, IMG_W - SIZE, IMG_H, null);
		// 下右
		g.drawImage(Img.WINDOW_IMG, x + w - SIZE, y + h - SIZE, x + w, y + h, IMG_W
				- SIZE, IMG_H - SIZE, IMG_W, IMG_H, null);
	}
	abstract protected void paint(Graphics g);

	public void setDto(GameDto dto) {
		this.dto = dto;
	}
	protected void drawNumLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		String strNum = Integer.toString(num);
		for (int i = 0; i < maxBit; i++) {
			// 判断是否满足绘制条件
			if (maxBit - i <= strNum.length()) {
				// 获得数字在字符串中的下标
				int idx = i - maxBit + strNum.length();
				// 把数字中的number每一位取出
				int bit = strNum.charAt(idx) - '0';
				g.drawImage(Img.IMAGE_NUM, 
						this.x + x + NUMBER_W * i, this.y + y, 
						this.x + x + NUMBER_W * (i + 1), this.y+ y + NUMBER_H ,
						bit * NUMBER_W, 0, (bit + 1)* NUMBER_W, NUMBER_H, null);
			}
		}
	}
	/**
	 * 绘制进度条
	 * @param y Y坐标
	 * @param title 标题
	 * @param number 得分
	 * @param percent 进度条百分比
	 * @param g
	 */
	protected void drawBar(int y,String title,String number,double percent,Graphics g){
		int rect_x = this.x+PADDING;
		int rect_y = this.y+y;
		//绘制背景
		int h = 32;
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.IMG_RECT_W, BAR_H+4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x+1, rect_y+1, this.IMG_RECT_W-2, BAR_H+2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x+2, rect_y+2, this.IMG_RECT_W-4, BAR_H);
		//绘制值槽
		//求出宽度
		int subw = (int)(percent*(this.IMG_RECT_W-4));
		//求出颜色
		int subIdx = (int)(percent*BAR_W)-1;
		g.drawImage(Img.IMAGE_BAR, 
				rect_x+2, rect_y+2, 
				rect_x+2+subw, rect_y+2+BAR_H, 
				subIdx, 0, subIdx+1, BAR_H, null);
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x+4, rect_y+BAR_H-4);
		if(number!=null){
			g.drawString(number, rect_x+223, rect_y+BAR_H-4);
		}
	}
	/**
	 * 绘制正中图形
	 * @param img
	 * @param g
	 */
	protected void drawImageCenter(Image img,Graphics g){
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x+(this.w-imgW>>1), this.y+(this.h-imgH>>1), null);
	}
}
