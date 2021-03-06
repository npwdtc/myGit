package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

public class LayerBackground extends Layer {

	public LayerBackground(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		int bgIdx = this.dto.getNowLevel()%Img.BG_LIST.size();
		g.drawImage(Img.BG_LIST.get(bgIdx), 0, 0, 1162, 686, null);
	}

}
