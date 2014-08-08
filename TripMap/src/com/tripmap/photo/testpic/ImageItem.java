package com.tripmap.photo.testpic;

import java.io.Serializable;

/**
 * 一个图片对象
 * 
 * @author Administrator
 * 
 */
public class ImageItem implements Serializable {
	public String imageId;  //图片id
	public String thumbnailPath;
	public String imagePath;  //图片路径
	public boolean isSelected = false;//是否被选中
}
