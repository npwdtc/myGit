package ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;

public class JFrameGame extends JFrame{

	FrameConfig fCfg = GameConfig.getFrameConfig();
	
	public JFrameGame(JPanelGame panel) {
		//设置标题
		this.setTitle(fCfg.getTitle());
		//设置默认关闭属性
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口大小
		this.setSize(fCfg.getWidth(), fCfg.getHeight());
		//不允许用户改变窗口大小
		this.setResizable(false);
		//居中
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth())>>1;
		int y = ((screen.height - this.getHeight())>>1)-20;
		this.setLocation(x, y);
		this.setContentPane(panel);
		this.setVisible(true);
	}
}
