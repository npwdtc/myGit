package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Img {
	//����ͼƬ����ͣ
	public static Image PAUSES = new ImageIcon("graphics/string/pause.png").getImage();
	//����ͼƬ����ʼ
	public static ImageIcon IMAGE_START = new ImageIcon("graphics/string/start.png");
	//����ͼƬ����ͣ1
	public static ImageIcon IMAGE_EXIT = new ImageIcon("graphics/string/exit.png");
	//����ͼƬ������
	public static Image IMAGE_RMLINE = new ImageIcon("graphics/string/rmline.png").getImage();
	//����ͼƬ���÷�
	public static Image IMAGE_POINT = new ImageIcon("graphics/string/point.png").getImage();
	//����ͼƬ���ȼ�
	public static Image WINDOW_LEVEL = new ImageIcon("graphics/string/level.png").getImage();
	//����ͼƬ�����ؼ�¼
	public static Image WINDOW_DISK = new ImageIcon("graphics/string/disk.png").getImage();
	//����ͼƬ�����ݿ�
	public static Image WINDOW_DB = new ImageIcon("graphics/string/db.png").getImage();
	//����ͼƬ
	public static Image IMAGE_NUM = new ImageIcon("graphics/string/num.png").getImage();
	//������
	public static Image IMAGE_BAR = new ImageIcon("graphics/window/bar.png").getImage();
	//���η���
	public static Image WINDOW_IMG = new ImageIcon("graphics/window/Window.png").getImage();
	//����
	public static Image ACT = new ImageIcon("graphics/game/rect.png").getImage();
	//��Ӱ
	public static Image SHADOW = new ImageIcon("graphics/game/shadow.png").getImage();
	//����ͼƬ��˵��
	public static Image ABOUT = new ImageIcon("graphics/other/about.png").getImage();
	//����ͼƬ
	public static List<Image> BG_LIST=null;
	//�߸���״�ķ���ͼƬ
	public static Image[] NEXT_ACT;
	
	static{
		//�߸�����ͼƬ��
		NEXT_ACT = new Image[7];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("graphics/game/"+i+".png").getImage();
		}
		//����ͼƬ��
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
