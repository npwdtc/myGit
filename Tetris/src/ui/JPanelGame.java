package ui;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;

public class JPanelGame extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton btnstart;
	private JButton btnexit;
	
	private List<Layer> layers= null;
	private GameDto dto;
	private GameControl gameControl;
	private PlayerControl control;
	public JPanelGame(GameControl gameControl,GameDto dto) {
		this.gameControl = gameControl;
		this.dto = dto;
		//设置布局管理器为自由布局
		this.setLayout(null);
		//初始化组建
		initComponent();
		//初始化层
		initLayer();
		this.control = new PlayerControl(gameControl);
	}
	
	private void initComponent(){
		this.btnstart = new JButton(Img.IMAGE_START);
		this.btnstart.setBounds(828, 50, 100, 53);
		this.btnstart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
				addKeyListener(control);
			}
		});
		this.add(btnstart);
		this.btnexit = new JButton(Img.IMAGE_EXIT);
		this.btnexit.setBounds(970, 50, 100, 53);
		this.add(btnexit);
		this.btnexit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void initLayer(){
		try {
			//获得游戏配置
			FrameConfig fCfg = GameConfig.getFrameConfig();
			//获得参数配置
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			//创建游戏层数组
			layers  = new ArrayList<Layer>(layersCfg.size());
			//创建所有曾对象
			for(LayerConfig layerCfg : layersCfg){
				//获得类对象
				Class<?> cls = Class.forName(layerCfg.getClassName());
				//获得构造函数
				Constructor ctr = cls.getConstructor(int.class,int.class,int.class,int.class);
				//调用构造函数创建对象
				Layer layer = (Layer) ctr.newInstance(
						layerCfg.getX(),layerCfg.getY(),layerCfg.getW(),layerCfg.getH());
				layer.setDto(this.dto);
				//把创建的Layer对象放入集合中
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		//调用基类方法
		super.paintComponent(g);
		//绘制游戏界面
		for(int i=0;i<layers.size();layers.get( i++).paint(g));
		//返回焦点
		this.requestFocus();
	}

	public void btnStartSwitch(boolean off){
		this.btnstart.setEnabled(off);
	}
}
