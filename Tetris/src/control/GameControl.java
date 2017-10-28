package control;


import service.GameService;
import ui.JFrameGame;
import ui.JFrameSavePoint;
import ui.JPanelGame;
import dao.Data;
import dao.DataDB;
import dao.DataDisk;
import dao.DataTest;
import dto.GameDto;
import dto.Player;

public class GameControl {

	private GameDto dto;
	private JPanelGame panel;
	private GameService service;
	private Data dataA;
	private Data dataB;
	private Thread gameThread;

	public JFrameSavePoint frameSavePoint;
	public GameControl() {
		// ������Ϸ����Դ
		this.dto = new GameDto();
		// ������Ϸ���ݿ죨������[Ϸ����Դ��
		this.service = new GameService(dto);
		// �������ݽӿ�A����
		this.dataA = new DataTest();
		this.dataB = new DataDisk();
		this.service.setDbRecord(dataA.loadData());
		this.service.setDiskRecord(dataB.loadData());
		// ������Ϸ���
		this.panel = new JPanelGame(this, dto);
		frameSavePoint = new JFrameSavePoint(this);
		// ������Ϸ����
		new JFrameGame(panel);
	}

	public void keyUp() {
		if (dto.isPause()||!dto.isStart()) {
			return;
		}
		this.service.keyUp();
		this.panel.repaint();
	}

	public void keyDown() {
		if (dto.isPause()||!dto.isStart()) {
			return;
		}
		this.service.keyDown();
		this.panel.repaint();
	}

	public void keyLeft() {
		if (dto.isPause()||!dto.isStart()) {
			return;
		}
		this.service.keyLeft();
		this.panel.repaint();
	}

	public void keyRight() {
		if (dto.isPause()||!dto.isStart()) {
			return;
		}
		this.service.keyRight();
		this.panel.repaint();
	}

	public void testA() {
		this.service.testA();
		this.panel.repaint();
	}

	// ��ͣ
	public void keyPause() {
		if(this.dto.isStart()){
			this.service.keyPause();
			this.panel.repaint();			
		}
	}

	// ��ʼ��ť
	public void start() {
		this.panel.btnStartSwitch(false);
		// ��Ϸ���ݳ�ʼ��
		this.service.startGame();
		// �����̶߳���
		this.gameThread = new MainThread();
		this.gameThread.start();
		this.panel.repaint();
	}
	public void savePoint(String name) {
		Player pla = new Player(name, this.dto.getNowPoint());
		this.dataA.saveData(pla);
		this.dataB.saveData(pla);
		this.service.setDbRecord(dataA.loadData());
		this.service.setDiskRecord(dataB.loadData());
		this.panel.repaint();
	}
	public void afterLose() {
		//�������
		this.frameSavePoint.show(this.dto.getNowPoint());
		
		this.panel.btnStartSwitch(true);
		
	}
	private class MainThread extends Thread{
		@Override
		public void run() {
			while (dto.isStart()) {
				panel.repaint();
				try {
					Thread.sleep(500);
					// ��ͣ
					if (dto.isPause()) {
						continue;
					}
					service.keyDown();
					panel.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}

	}
	
}
