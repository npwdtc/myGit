package config;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig {

	private String title;
	/**
	 * ´°¿Ú¿í¶È
	 */
	private int width;
	/**
	 * ´°¿Ú¸ß¶È
	 */
	private int height;
	/**
	 * ±ß¿ò³ß´ç
	 */
	private int border;
	/**
	 * ±ß¿òÄÚ±ß¾à
	 */
	private int padding;
	/**
	 * Í¼²ãÊôÐÔ
	 */
	List<LayerConfig> layersConfig;
	public FrameConfig(Element frame) {
		this.title = frame.attributeValue("title");
		this.width = Integer.parseInt(frame.attributeValue("width"));
		this.height = Integer.parseInt(frame.attributeValue("height"));
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		this.border = Integer.parseInt(frame.attributeValue("border"));
		List<Element> layers = frame.elements("layer");
		layersConfig =new ArrayList<LayerConfig>(layers.size());
		for(Element layer:layers){
			LayerConfig lc = new LayerConfig(
					layer.attributeValue("className"), 
					Integer.parseInt(layer.attributeValue("x")), 
					Integer.parseInt(layer.attributeValue("y")), 
					Integer.parseInt(layer.attributeValue("w")), 
					Integer.parseInt(layer.attributeValue("h")));
			layersConfig.add(lc);
		}
	}
	public String getTitle() {
		return title;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getBorder() {
		return border;
	}
	public int getPadding() {
		return padding;
	}
	public List<LayerConfig> getLayersConfig() {
		return layersConfig;
	}

}
