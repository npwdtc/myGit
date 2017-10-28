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
		//���ò��ֹ�����Ϊ���ɲ���
		this.setLayout(null);
		//��ʼ���齨
		initComponent();
		//��ʼ����
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
			//�����Ϸ����
			FrameConfig fCfg = GameConfig.getFrameConfig();
			//��ò�������
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			//������Ϸ������
			layers  = new ArrayList<Layer>(layersCfg.size());
			//��������������
			for(LayerConfig layerCfg : layersCfg){
				//��������
				Class<?> cls = Class.forName(layerCfg.getClassName());
				//��ù��캯��
				Constructor ctr = cls.getConstructor(int.class,int.class,int.class,int.class);
				//���ù��캯����������
				Layer layer = (Layer) ctr.newInstance(
						layerCfg.getX(),layerCfg.getY(),layerCfg.getW(),layerCfg.getH());
				layer.setDto(this.dto);
				//�Ѵ�����Layer������뼯����
				layers.add(layer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		//���û��෽��
		super.paintComponent(g);
		//������Ϸ����
		for(int i=0;i<layers.size();layers.get( i++).paint(g));
		//���ؽ���
		this.requestFocus();
	}

	public void btnStartSwitch(boolean off){
		this.btnstart.setEnabled(off);
	}
}
