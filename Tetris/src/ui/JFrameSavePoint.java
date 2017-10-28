package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControl;

public class JFrameSavePoint extends JFrame {

	private JButton btnOK;
	private JLabel lbPoint;
	private JLabel errMsg;
	private JTextField txName;
	private GameControl gameControl;
	public JFrameSavePoint(GameControl gameControl) {
		this.gameControl = gameControl;
		this.setTitle("�����¼");
		this.setSize(256, 128);
		// ����
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth()) >> 1;
		int y = ((screen.height - this.getHeight()) >> 1) - 50;
		this.setLocation(x, y);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		createCom();
		this.createAction();
	}
	
	public void show(int point){
		this.lbPoint.setText("��ĵ÷֣�"+point);
		this.setVisible(true);
	}
	private void createCom(){
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbPoint = new JLabel();
		north.add(lbPoint);
		this.errMsg = new JLabel();
		north.add(errMsg);
		this.add(north,BorderLayout.NORTH);
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		JLabel lb = new JLabel("������֣�");
		center.add(lb);
		center.add(txName);
		this.add(center);
		
		//����ȷ����ť
		this.btnOK = new JButton("ȷ��");
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		south.add(btnOK);
		this.add(south,BorderLayout.SOUTH);
	}
	
	private void createAction(){
		this.btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txName.getText();
				if(name.length()>16||name==null||"".equals(name)){
					errMsg.setText("�����������");
				}else{
					gameControl.savePoint(name);
					setVisible(false);
				}
			}
		});
	}
}
