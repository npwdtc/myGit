package ui;

import java.awt.Graphics;
import java.util.List;

import dto.Player;

public class LayerDisk extends LayerData {

	public LayerDisk(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.createWindow(g);
//		g.drawImage(Img.WINDOW_DISK, this.x+PADDING, this.y+PADDING, null);
		List<Player> players = this.dto.getDiskRecord();
		showData(Img.WINDOW_DISK, players, g);
	}

}
