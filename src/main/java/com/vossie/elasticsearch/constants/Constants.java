/**
 *@Title: Constants.java
 *@Package com.vossie.elasticsearch.constants 
 *@Description TODO
 *@author dell
 *@date 2016年10月18日 下午4:55:03
 *@version  版本号
 */
package com.vossie.elasticsearch.constants;

import java.text.SimpleDateFormat;
import java.util.Properties;

import com.nis.utils.ESUtils;

/**
 * @ClassName: Constants.java 
 * @Description: TODO
 * @author (dell)
 * @date 2016年10月18日 下午4:55:03
 * @version V1.0
 */
public class Constants {
	private static Properties props = ESUtils.getProps();
	public static String index_name_date_format=props.getProperty("index_name_last_format", "yyyy-MM-dd-HH");
	
	public final static String number_of_shards=props.getProperty("number_of_shards", "10");
	public final static String number_of_replicas=props.getProperty("number_of_replicas", "1");
	public final static String time_period=props.getProperty("time_period", "3600000");
//	public final static String CLASS_FOLDER=props.getProperty("class_folder", null);
//	public final static String PACKAGE=props.getProperty("package", null);
//	public final static String extend_classpath=props.getProperty("extend_classpath", null);
	public static SimpleDateFormat format=new SimpleDateFormat(index_name_date_format);
	public static final String create_index_day=props.getProperty("create_index_day", "0");
	public static final String bulk_threads=props.getProperty("bulk_threads", "27");
}
