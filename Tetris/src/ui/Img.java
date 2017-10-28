package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Img {
	//数字图片，暂停
	public static Image PAUSES = new ImageIcon("graphics/string/pause.png").getImage();
	//数字图片，开始
	public static ImageIcon IMAGE_START = new ImageIcon("graphics/string/start.png");
	//数字图片，暂停1
	public static ImageIcon IMAGE_EXIT = new ImageIcon("graphics/string/exit.png");
	//数字图片，消行
	public static Image IMAGE_RMLINE = new ImageIcon("graphics/string/rmline.png").getImage();
	//数字图片，得分
	public static Image IMAGE_POINT = new ImageIcon("graphics/string/point.png").getImage();
	//数字图片，等级
	public static Image WINDOW_LEVEL = new ImageIcon("graphics/string/level.png").getImage();
	//数字图片，本地记录
	public static Image WINDOW_DISK = new ImageIcon("graphics/string/disk.png").getImage();
	//数字图片，数据库
	public static Image WINDOW_DB = new ImageIcon("graphics/string/db.png").getImage();
	//数字图片
	public static Image IMAGE_NUM = new ImageIcon("graphics/string/num.png").getImage();
	//进度条
	public static Image IMAGE_BAR = new ImageIcon("graphics/window/bar.png").getImage();
	//矩形方框
	public static Image WINDOW_IMG = new ImageIcon("graphics/window/Window.png").getImage();
	//方块
	public static Image ACT = new ImageIcon("graphics/game/rect.png").getImage();
	//阴影
	public static Image SHADOW = new ImageIcon("graphics/game/shadow.png").getImage();
	//数字图片，说明
	public static Image ABOUT = new ImageIcon("graphics/other/about.png").getImage();
	//背景图片
	public static List<Image> BG_LIST=null;
	//七个形状的方块图片
	public static Image[] NEXT_ACT;
	
	static{
		//七个方块图片组
		NEXT_ACT = new Image[7];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("graphics/game/"+i+".png").getImage();
		}
		//背景图片组
		File dir = new File("graphics/background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<Image>();
		for (File f:files) {
			if(!f.isDirectory()){
				BG_LIST.add(new ImageIcon(f.getPath()).getImage());
			}
		}
	}
}
