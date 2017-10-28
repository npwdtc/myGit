package config;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GameConfig {

	private static FrameConfig frameConfig;
	private static SystemConfig systemConfig;
	private static DataConfig dataConfig;
	/**
	 * ���캯�� ��ȡXML�ļ�����ȡ��Ϸȫ������
	 * 
	 * @throws Exception
	 */
	static {
		// ����XML������
		SAXReader reader = new SAXReader();
		// ��ȡXML�ĵ�
		Document doc = null;
		try {
			doc = reader.read("data/cfg.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ��ȡ��Ԫ��
		Element root = doc.getRootElement();

		// ���ô��ڲ���
		// this.setUiConfig(root.element("frame"));
		frameConfig = new FrameConfig(root.element("frame"));
		// ����ϵͳ����
		// this.setSystemConfig(root.element("system"));
		systemConfig = new SystemConfig(root.element("system"));
		// �������ݷ��ʲ���
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
