/**
 *@Title: ClassUtils.java
 *@Package com.nis.utils 
 *@Description TODO
 *@author dell
 *@date 2016年10月24日 上午9:26:43
 *@version  版本号
 */
package com.nis.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
/**
 * @ClassName: ClassUtils.java 
 * @Description: TODO
 * @author (dell)
 * @date 2016年10月24日 上午9:26:43
 * @version V1.0
 */
public class ClassUtil {
	public static final String DEFAULT_RESOURCE_PATTERN="/*.class";
	public static final String packageName="com.nis.domain.restful";
	private static final Logger logger = Logger.getLogger(ClassUtil.class);
	/**
	 * 
	 * loadClass(根据包名获取其中包含的所有分class)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param packageName
	 * @return 
	 *List<String>
	 * @exception 
	 * @since  1.0.0
	 */
	public static List<String> loadClass(){
		List<String> classNameList=new ArrayList<>();
		if(StringUtils.isEmpty(packageName)){
			logger.error("package is empty");
		}
		ResourcePatternResolver resourcePatternResolver=new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory=new CachingMetadataReaderFactory(resourcePatternResolver);
		String os=System.getProperty("os.name");
		String packageSearchPath=null;
		if(os.startsWith("Windows")){
			packageSearchPath=ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+
					ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(packageName))
					+"/"+DEFAULT_RESOURCE_PATTERN;
		}else{
			packageSearchPath=ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+
					ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(packageName))
					+DEFAULT_RESOURCE_PATTERN;
		}
		
		
		try{
			Resource[] resources=resourcePatternResolver.getResources(packageSearchPath);
			if(resources.length==0){
				logger.error(packageSearchPath+" can not get resources");
			}
			for(Resource resource:resources){
				String loadClassName=loadClassName(metadataReaderFactory,resource);
				if(loadClassName!=null){
					classNameList.add(loadClassName);
//					System.out.println(loadClassName);
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage()+"\r\n"+e.getCause());
			// TODO: handle exception
		}
		
		return classNameList;
	}
	public static String loadClassName(MetadataReaderFactory metadataReaderFactory,Resource resource){
		String className=null;
		try {
			if(resource.isReadable()){
				MetadataReader metadataReader=metadataReaderFactory.getMetadataReader(resource);
				if(metadataReader!=null){
					 className=metadataReader.getClassMetadata().getClassName();
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage()+"\r\n"+e.getCause());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return className;
	}
//	@SuppressWarnings("resource")
//	public static List<Class> getClasses() throws ClassNotFoundException, MalformedURLException{
//		logger.info("start to compile class");
//		List<Class> classList=new ArrayList<>();
//		String[] classNames=runCommand(Constants.CLASS_FOLDER,Constants.CLASS_FOLDER);
//		URL[] javaFileUrls=new URL[]{new File(Constants.CLASS_FOLDER+"/classes").toURI().toURL()};
//		ClassLoader classLoader=new URLClassLoader(javaFileUrls);
//		for(String className: classNames){
//			Class<?> clazz=classLoader.loadClass(Constants.PACKAGE+"."+className);
//			classList.add(clazz);
//		}
//		logger.info("compile class finish");
//		return classList;
//	}
	/**
	 * 
	 * runCommand(编译某个文件夹下的类)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param folder
	 * @param path 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
//	private static String[] runCommand(String folder,String path){
//		List<String> filesList=new ArrayList<>();
//		File file=new File(folder);
//		File classFolder=new File(path+"/classes");
//		if(!classFolder.exists()){
//			classFolder.mkdir();
//		}
//		File[] fileArray=file.listFiles();
//		ProcessBuilder pd=new ProcessBuilder();
//		pd.redirectErrorStream(true);
//		BufferedReader reader=null;
//		String os=System.getProperty("os.name");
//		System.out.println(os);
//		try {
//			List<String> commands=new ArrayList<>();
//			for(File _file:fileArray){
//				if(_file.getName().endsWith(".class")){
//					 _file.delete() ;
//					 continue;
//				}
//				if(_file.isDirectory()&&_file.getName().equals("classes")){
//					continue;
//				}else if(_file.isDirectory()){
//					runCommand(_file.getAbsolutePath().replace("/"+_file.getName(), ""),path);
//					continue;
//				} 
//				commands.add("javac");
//				commands.add("-encoding");
//				commands.add("UTF-8");
//				commands.add("-classpath");
//				if(os!=null&&os.startsWith("Windows")){
//					System.setProperty("java.class.path","%CLASSPATH%;"+path+"/classes;"+);
//					commands.add("D:/workspace2/elasticsearch-annotations/src/main/java/com/vossie/elasticsearch"+";%CLASSPATH%;"+path+"/classes;"+Constants.extend_classpath == null?System.getProperty("user.dir"):System.getProperty("user.dir")+"/"+Constants.extend_classpath);
//				}else{
//					System.setProperty("java.class.path","$CLASSPATH:"+path+"/classes:"+Constants.extend_classpath == null?"":Constants.extend_classpath);
//					commands.add("$CLASSPATH:"+path+"/classes:"+Constants.extend_classpath == null?System.getProperty("user.dir"):System.getProperty("user.dir")+"/"+Constants.extend_classpath);
//				}
//				commands.add("-d");
//				commands.add(path+"/classes");
//				commands.add(_file.getAbsolutePath());
//				Process p=pd.command(commands).start();
//				reader=new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
//				StringBuffer buffer=new StringBuffer();
//				String result="";
//				while((result=reader.readLine())!=null){
//					buffer.append(result);
//				}
//				logger.info("result: "+buffer);
//				filesList.add(_file.getName().substring(0, _file.getName().lastIndexOf(".")));
//			}
//			
//		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String[] fieldArray=new String[filesList.size()];
//		filesList.toArray(fieldArray);
//		return fieldArray;
//	}
}
