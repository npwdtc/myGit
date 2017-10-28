package util;

public class GameFunction {

	public static long getSleepTime(int level){
		long sleep = -40*level + 700;
		sleep =  sleep<100?100:sleep;
		return sleep;
	}
}
