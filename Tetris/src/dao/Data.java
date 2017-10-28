package dao;

import java.util.List;

import dto.Player;

public interface Data {

	List<Player> loadData();
	void saveData(Player plas);
}
