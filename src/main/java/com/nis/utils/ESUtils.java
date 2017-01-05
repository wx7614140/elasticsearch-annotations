package com.nis.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.constants.Constants;

/**
 * 
 * @author <a href="mailto:chenghui@intranet.com">chenghui</a> 2015-04-22 <br>
 * 
 *         es通用类,提供获取client方法 <br>
 *         读取配置文件方法
 *
 */
public class ESUtils {
	static {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/config/log4j.properties");
	}

	static Client client = null; // client 唯一实例
	static final Properties props = new Properties();// props 唯一实例
	private static final Logger logger = Logger.getLogger(ESUtils.class);
	
	public static void init(){
		getProps();
		String esHostPort = props.getProperty("es_host_port");
		String clusterName = props.getProperty("cluster_name");
		Map<String, List<Integer>> hostAndPortMap = getEsHostAndPortMap(esHostPort);
		Settings settings = Settings.settingsBuilder()
				// 指定集群名称
				.put("cluster.name", clusterName)
				// 探测集群中机器状态
				.put("client.transport.sniff", true).build();
		/*
		 * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象 用完记得要关闭
		 */
		if(client==null){
			client = TransportClient.builder().settings(settings).build();
			for (Entry<String, List<Integer>> e : hostAndPortMap.entrySet()) {
				if(e.getValue().size()>0){
					for (Integer i : e.getValue()) {
						try {
							((TransportClient) client).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(e.getKey()), i));
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
			}
			logger.info("client created");
		}else{
			logger.info("client exists");
		}
		
	}
	public static void destory(){
		if(client!=null){
			client.close();
			logger.info("client destoryed");
		}
	}
	/**
	 * 获取唯一client
	 * 
	 * @return client
	 * @throws UnknownHostException
	 */
	public static Client getClient() {
		if (client == null){
			init();
		}

		return client;
	}
	private static Map<String, List<Integer>> getEsHostAndPortMap(String hostAndPort) {
		Map<String, List<Integer>> esHostAndPortMap = new HashMap<String, List<Integer>>();
		if (hostAndPort.indexOf(",") == -1) {
			if (hostAndPort.indexOf(":") == -1) {
				throw new RuntimeException("es_host_port in config file not logical");
			} else {
				String[] arr = hostAndPort.split(":");
				ArrayList<Integer> list=new ArrayList<Integer>();
				list.add(Integer.parseInt(arr[1]));
				esHostAndPortMap.put(arr[0],list );
			}
		} else {
			String[] hostAndPortArr = hostAndPort.split(",");
			for (String host_port : hostAndPortArr) {
				if (host_port.indexOf(":") == -1) {
					throw new RuntimeException("es_host_port in config file not logical");
				} else {
					String[] arr = host_port.split(":");
					if (arr[1]!=null&&esHostAndPortMap.containsKey(arr[0])) {
						esHostAndPortMap.get(arr[0]).add(Integer.parseInt(arr[1]));
					} else if(arr[1]!=null) {
						List<Integer> list = new ArrayList<Integer>();
						list.add(Integer.parseInt(arr[1]));
						esHostAndPortMap.put(arr[0], list);
					}
				}
			}
		}
		return esHostAndPortMap;
	}

	/**
	 * 获取唯一配置文件
	 * 
	 * @return props
	 */
	public static Properties getProps() {
		if (props != null && !props.isEmpty())
			return props;

		InputStream is = null;
		String path = System.getProperty("user.dir") + "/config/esconfig.properties";
		try {
			is = new FileInputStream(path);
			System.out.println(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			props.load(is);
		} catch (Exception e) {
			logger.error(
					"Can not read properties file. " + "Please ensure properties the path specified in CLASSPATH");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return props;
	}

//	public static boolean savingMappingToElasticInstance(Class<?> clazz) throws IOException {
//
//		boolean bool = createIndex(clazz);
//		logger.info(clazz.getSimpleName()+" INDEX CREATED? "+bool);
//		return bool;
//	}
	public static boolean savingMappingToElasticInstance(Class<?> clazz,Date date) throws IOException {
		boolean bool = createIndex(clazz,date);
//		logger.info(clazz.getSimpleName().toLowerCase()+"-"+Constants.format.format(date)+" INDEX CREATED? "+bool);
		return bool;
	}
	public static boolean savingMappingToElasticInstance(Class<?> clazz,String date) throws IOException {
		boolean bool = createIndex(clazz,date);
//		logger.info(clazz.getSimpleName().toLowerCase()+"-"+Constants.format.format(date)+" INDEX CREATED? "+bool);
		return bool;
	}
	/**
	 * 創建模板
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public static boolean createTemplate(Class<?> clazz) throws IOException{
		ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(clazz);
		Builder settings=documentMetadata.getSettings();
		//由于注解中的字符串必须是常量，从配置文件加载的配置无法覆盖，在这里进行覆盖
		if(!StringUtils.isEmpty(Constants.number_of_shards)){
			settings.put("number_of_shards", Constants.number_of_shards);
		}
		if(!StringUtils.isEmpty(Constants.number_of_replicas)){
			settings.put("number_of_replicas", Constants.number_of_replicas);
		}
		PutIndexTemplateRequestBuilder builder=client.admin().indices()
				.preparePutTemplate(documentMetadata.getIndexName()+"-template")
				.setSettings(settings)
				.addMapping(documentMetadata.getTypeName(), documentMetadata.toMapping())
				.cause("for create index name start with "+documentMetadata.getIndexName())
				.setTemplate(documentMetadata.getIndexName()+"*");
		//设置别名		
		for(String esalias:documentMetadata.getElasticsearchAliases().getStringAliases()){
			Alias alias=new Alias(esalias);
			builder.addAlias(alias);
		}
		return builder.execute().actionGet().isAcknowledged();
	}
	public static MappingMetaData getMapping(String indexName, String typeName) {
		ClusterState cs = client.admin().cluster().prepareState().setIndices(indexName).execute().actionGet()
				.getState();
		IndexMetaData imd = cs.getMetaData().index(indexName);

		return imd.mapping(typeName);
	}

	public static boolean indexExists(String index) {
		return client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet().isExists();
	}

//	public static boolean createIndex(Class<?> clazz) throws IOException {
//
//		ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(clazz);
//		boolean result =createIndex(documentMetadata);
////		addAlias(documentMetadata.getIndexName(), new ElasticsearchAlias(clazz).getStringAliases());// 别名
////		boolean result = client.admin().indices().preparePutMapping(documentMetadata.getIndexName())
////				.setType(documentMetadata.getTypeName()).setSource(documentMetadata.toMapping()).execute().actionGet()
////				.isAcknowledged();
//
//		return result;
//	}
	public static boolean clearIndexCache(){
		return ElasticsearchMapping.clearIndexCache();
	}
	public static boolean createIndex(Class<?> clazz,Date date) throws IOException {
		
		ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(clazz);
		boolean result=createIndex(documentMetadata,"-"+Constants.format.format(date));
		return result;
	}
	public static boolean createIndex(Class<?> clazz,String date) throws IOException {
		
		ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(clazz);
		boolean result =createIndex(documentMetadata,"-"+date);
//		addAlias(documentMetadata.getIndexName(), new ElasticsearchAlias(clazz).getStringAliases());// 别名
//		boolean result = client.admin().indices().preparePutMapping(documentMetadata.getIndexName())
//				.setType(documentMetadata.getTypeName()).setSource(documentMetadata.toMapping()).execute().actionGet()
//				.isAcknowledged();
//		
		return result;
	}
	public static boolean createIndexWithoutMapping(Class<?> clazz,Date date) throws IOException {
		
		ElasticsearchDocumentMetadata documentMetadata = ElasticsearchMapping.get(clazz);
		boolean result =createIndexWithoutMapping(documentMetadata,"-"+Constants.format.format(date));
		return result;
	}

//	public static boolean createIndex(ElasticsearchDocumentMetadata data) throws IOException {
//
//		try {
//			if (!indexExists(data.getIndexName())) {
//				Builder settings=data.getSettings();
//				//由于注解中的字符串必须是常量，从配置文件加载的配置无法覆盖，在这里进行覆盖
//				if(!StringUtils.isEmpty(Constants.number_of_shards)){
//					settings.put("number_of_shards", Constants.number_of_shards);
//				}
//				if(!StringUtils.isEmpty(Constants.number_of_replicas)){
//					settings.put("number_of_replicas", Constants.number_of_replicas);
//				}
//				
//				CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices()
//						.prepareCreate(data.getIndexName()).setSettings(settings)
//						.addMapping(data.getTypeName(), data.toMapping());
//				//设置别名		
//				for(String esalias:data.getElasticsearchAliases().getStringAliases()){
//					Alias alias=new Alias(esalias);
//					createIndexRequestBuilder.addAlias(alias);
//				}
//				CreateIndexResponse response= createIndexRequestBuilder.execute().actionGet();
////				Iterator<ObjectObjectCursor<Object, Object>>  it=response.getContext().iterator();
////				while(it.hasNext()){
////					ObjectObjectCursor<Object, Object> cursor=it.next();
////					logger.info(cursor.key);
////					logger.info(cursor.value);
////				}
//				return response.isAcknowledged();
//			}else{
//				logger.warn("creating index failed,"+data.getIndexName()+" is already existed");
//			}
//		} catch (IndexAlreadyExistsException e) {
//			logger.warn("creating index failed,"+data.getIndexName()+" is already existed");
//			return true;
//		}
//		return true;
//	}
	public static boolean createIndex(ElasticsearchDocumentMetadata data ,String dateValue) throws IOException {
		String indexName=data.getIndexName()+dateValue;
		try {
			
			if (!indexExists(indexName)) {
				Builder settings=data.getSettings();
				//由于注解中的字符串必须是常量，从配置文件加载的配置无法覆盖，在这里进行覆盖
				if(!StringUtils.isEmpty(Constants.number_of_shards)){
					settings.put("number_of_shards", Constants.number_of_shards);
				}
				if(!StringUtils.isEmpty(Constants.number_of_replicas)){
					settings.put("number_of_replicas", Constants.number_of_replicas);
				}
				CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices()
						.prepareCreate(indexName).setSettings(settings)
						.addMapping(data.getTypeName(), data.toMapping());
				//设置别名		
				for(String esalias:data.getElasticsearchAliases().getStringAliases()){
					Alias alias=new Alias(esalias);
					createIndexRequestBuilder.addAlias(alias);
				}
				CreateIndexResponse response= createIndexRequestBuilder.execute().actionGet();
//				Iterator<ObjectObjectCursor<Object, Object>>  it=response.getContext().iterator();
//				while(it.hasNext()){
//					ObjectObjectCursor<Object, Object> cursor=it.next();
//					logger.info(cursor.key);
//					logger.info(cursor.value);
//				}
				return response.isAcknowledged();
			}else{
				logger.warn("creating index failed,"+indexName+" is already existed");
			}
		} catch (IndexAlreadyExistsException e) {
			logger.warn("creating index failed,"+indexName+" is already existed");
			return true;
		}
		return true;
	}
	public static boolean createIndexWithoutMapping(ElasticsearchDocumentMetadata data ,String dateValue) throws IOException {
		String indexName=data.getIndexName()+dateValue;
		try {
			
			if (!indexExists(indexName)) {
				CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices()
						.prepareCreate(indexName);
				CreateIndexResponse response= createIndexRequestBuilder.execute().actionGet();
//				Iterator<ObjectObjectCursor<Object, Object>>  it=response.getContext().iterator();
//				while(it.hasNext()){
//					ObjectObjectCursor<Object, Object> cursor=it.next();
//					logger.info(cursor.key);
//					logger.info(cursor.value);
//				}
				return response.isAcknowledged();
			}else{
				logger.warn("creating index failed,"+indexName+" is already existed");
			}
		} catch (IndexAlreadyExistsException e) {
			logger.warn("creating index failed,"+indexName+" is already existed");
			return true;
		}
		return true;
	}

	/**
	 * 创建索引
	 * 
	 * @param index
	 * @return
	 */
	public static boolean createIndex(String index) {

		try {
			if (!indexExists(index)) {

				CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(index);
				return createIndexRequestBuilder.execute().actionGet().isAcknowledged();
			}
		} catch (IndexAlreadyExistsException e) {
			return true;
		}
		return true;
	}

	public static boolean addAlias(String index, String[] aliases) {
		if (!indexExists(index))
			createIndex(index);
		IndicesAliasesRequestBuilder indicesAliasesRequestBuilder = client.admin().indices().prepareAliases();
		if (aliases == null || aliases.length < 1)
			return false;
		for (String alia : aliases) {
			indicesAliasesRequestBuilder.addAlias(index, alia);
		}
		return indicesAliasesRequestBuilder.execute().actionGet().isAcknowledged();
	}

	public boolean addAlias(ElasticsearchDocumentMetadata documentMetadata, String[] aliases) {
		if (!indexExists(documentMetadata.getIndexName()))
			createIndex(documentMetadata.getIndexName());
		IndicesAliasesRequestBuilder indicesAliasesRequestBuilder = client.admin().indices().prepareAliases();
		if (aliases == null || aliases.length < 1)
			return false;
		for (String alia : aliases) {
			indicesAliasesRequestBuilder.addAlias(documentMetadata.getIndexName(), alia);
		}
		return indicesAliasesRequestBuilder.execute().actionGet().isAcknowledged();
	}

	public boolean addAlias(String index, Map<String, Map<String, Object>> aliases) {
		if (!indexExists(index))
			createIndex(index);
		if (aliases == null || aliases.size() < 1)
			return false;
		IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
		for (Entry<String, Map<String, Object>> alias : aliases.entrySet()) {
			if (alias.getValue() != null || alias.getValue().size() > 0) {
				if (alias.getValue().size() == 1) {
					ObjectMapper mapper = new ObjectMapper();
					try {
						Map<String, Object> map1 = mapper.readValue(alias.getValue().get("filter").toString(),
								new TypeReference<Map<String, Object>>() {
								});
						for (Entry<String, Object> m : map1.entrySet()) {
							System.out.println(m.getKey() + "   " + m.getValue());
						}
						indicesAliasesRequest.addAlias(alias.getKey(), map1, new String[] { index });
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					indicesAliasesRequest.addAlias(alias.getKey(), alias.getValue(), new String[] { index });
				}
			} else {
				indicesAliasesRequest.addAlias(alias.getKey(), new String[] { index });
			}

		}
		return client.admin().indices().aliases(indicesAliasesRequest).actionGet().isAcknowledged();
	}

	public void addTemplate(String index, String template) {
		if (!indexExists(index))
			createIndex(index);
		client.admin().indices().preparePutTemplate(template).execute().actionGet();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(XContentFactory.jsonBuilder().startObject().startObject("oracle").startObject("properties")
				.startObject("content").field("type", "string").field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject().endObject().endObject().endObject().string());

	}
}