package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Player;

public class DataTest implements Data{

	@Override
	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("С��",100));
		
		players.add(new Player("С��",330));
//		players.add(new Player("С��",440));
		players.add(new Player("С��",550));
		players.add(new Player("С��",232));
		return players;
	}

	@Override
	public void saveData(Player pla) {
		// TODO Auto-generated method stub
		
	}

}
