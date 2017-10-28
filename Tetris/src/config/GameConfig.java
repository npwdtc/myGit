package config;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GameConfig {

	private static FrameConfig frameConfig;
	private static SystemConfig systemConfig;
	private static DataConfig dataConfig;
	/**
	 * 构造函数 读取XML文件，获取游戏全部配置
	 * 
	 * @throws Exception
	 */
	static {
		// 创建XML解析器
		SAXReader reader = new SAXReader();
		// 读取XML文档
		Document doc = null;
		try {
			doc = reader.read("data/cfg.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取跟元素
		Element root = doc.getRootElement();

		// 配置窗口参数
		// this.setUiConfig(root.element("frame"));
		frameConfig = new FrameConfig(root.element("frame"));
		// 配置系统参数
		// this.setSystemConfig(root.element("system"));
		systemConfig = new SystemConfig(root.element("system"));
		// 配置数据访问参数
		// this.setDataConfig(root.element("data"));
		dataConfig = new DataConfig(root.element("data"));
	}

	public GameConfig(){
	}

	public static FrameConfig getFrameConfig() {
		return frameConfig;
	}

	public static SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public static DataConfig getDataConfig() {
		return dataConfig;
	}

}
