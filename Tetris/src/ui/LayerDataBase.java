package ui;

import java.awt.Graphics;
import java.util.List;

import dto.Player;

public class LayerDataBase extends LayerData {

	public LayerDataBase(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		
	}
	@Override
	public void paint(Graphics g){
		this.createWindow(g);
		List<Player> players = this.dto.getDbRecord();
		showData(Img.WINDOW_DB, players, g);
		
	}

}
