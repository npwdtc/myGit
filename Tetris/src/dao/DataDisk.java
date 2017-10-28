package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import dto.Player;

public class DataDisk implements Data {

	private static String path="data/recode.dat";
	//从文件中拿到数据
	@Override
	public List<Player> loadData() {
		ObjectInputStream in = null;
		List<Player> players = null;
		try {
			in = new ObjectInputStream(new FileInputStream(path));
			players = (List<Player>) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return players;
	}

	//把数据写入到文件中
	@Override
	public void saveData(Player pla) {
		List<Player> players = loadData();
		players.add(pla);
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(players);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
