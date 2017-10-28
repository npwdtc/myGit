package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerNext extends Layer {

	
	public LayerNext(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.createWindow(g);
		if(this.dto.isStart()){
			drawImageCenter(Img.NEXT_ACT[dto.getNext()],g);
		}
	}
}
