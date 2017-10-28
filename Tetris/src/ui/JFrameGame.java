package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;

public class JFrameGame extends JFrame{

	FrameConfig fCfg = GameConfig.getFrameConfig();
	
	public JFrameGame(JPanelGame panel) {
		//���ñ���
		this.setTitle(fCfg.getTitle());
		//����Ĭ�Ϲر�����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ô��ڴ�С
		this.setSize(fCfg.getWidth(), fCfg.getHeight());
		//�������û��ı䴰�ڴ�С
		this.setResizable(false);
		//����
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth())>>1;
		int y = ((screen.height - this.getHeight())>>1)-20;
		this.setLocation(x, y);
		this.setContentPane(panel);
		this.setVisible(true);
	}
}
