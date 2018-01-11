package com.osg.framework.util;

import java.io.File;

public class FileNameUtil {
	public static String getSuffix(String filename){
		return filename.substring(filename.lastIndexOf("."));
	}

	public static String getSuffixNoPoint(String filename){
		return filename.substring(filename.lastIndexOf(".")+1);
	}
	
	public static String getReFileName(String filepath,String userid, String filename, int index) {
		File file = new File(filepath + "/" + userid + "/"
				+ filename);
		if (file.exists()) {
			//名称
			String name = "";
			//后缀
			String suffix = filename.substring(filename.lastIndexOf("."));
			if(index>1){
				name = filename.substring(0,filename.lastIndexOf("_("+(index-1)+")."));
			}else{
				name = filename.substring(0,filename.lastIndexOf("."));
			}
			filename = name+ "_("+index+")"+suffix; 
			index ++;
			return getReFileName(filepath,userid,filename,index);
		}else{
			return filename;
		}
	}
}
