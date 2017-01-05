package com.vossie.elasticsearch.annotations.common;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.core.annotation.AnnotationUtils;

import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.enums.BooleanValue;
import com.vossie.elasticsearch.annotations.enums.DateFormat;
import com.vossie.elasticsearch.annotations.enums.FieldDatatype;
import com.vossie.elasticsearch.annotations.enums.MetaFieldName;

/**
 * Copyright © 2013 GSMA. GSM and the GSM Logo are registered and owned by the GSMA.
 * User: cvosloo
 * Date: 10/12/2013
 * Time: 10:02
 */
public final class MetadataXContentBuilder {

    private static final Logger logger = Logger.getLogger(MetadataXContentBuilder.class);

    private static HashMap<String, XContentBuilder> cache = new HashMap<>();

    protected static XContentBuilder getXContentBuilder(ElasticsearchDocumentMetadata elasticsearchDocumentMetadata) {

        String key = String.format("%s-%s", elasticsearchDocumentMetadata.getIndexName(), elasticsearchDocumentMetadata.getTypeName());

        // Return from cache if it has been previously parsed.
        if(cache.containsKey(key))
            return cache.get(key);

        try {

            /** Set the objects type name */
            XContentBuilder xbMapping = jsonBuilder()
                    .startObject()
                    .startObject(elasticsearchDocumentMetadata.getTypeName());


            for(String fieldName : elasticsearchDocumentMetadata.getFieldNames()) {

                ElasticsearchNodeMetadata field = elasticsearchDocumentMetadata.getFieldMetaData(fieldName);
                xbMapping.startObject(field.getFieldName());

                for(String attributeName : field.getAttributes().keySet()) {

                    if(fieldName.equals(MetaFieldName._PARENT.toString()) && attributeName.equals("type")) {

                        xbMapping.field(
                                attributeName,
                                elasticsearchDocumentMetadata.getParent().getTypeName()
                        );
                    }else if(field.getAttributes().get(attributeName).getClass().isArray()) {
                        xbMapping.field(attributeName,field.getAttributes().get(attributeName));
                    }else
                        xbMapping.field(attributeName,field.getAttributes().get(attributeName).toString());
                }

                xbMapping.endObject();
            }

            // Add the fields.
            setXContentBuilderFields(xbMapping, elasticsearchDocumentMetadata.getProperties());
           
            // End
            xbMapping
            	.endObject()
                   .endObject();

            // Add to local cache.
            cache.put(key, xbMapping);

            return xbMapping;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    /**
     * Populate the child field nodes.
     * @param xbMapping The content builder to use.
     * @param fields The fields to append
     */
    private static void setXContentBuilderFields(XContentBuilder xbMapping, Map<String, ElasticsearchNodeMetadata> fields) {

        if(fields.keySet().size() < 1)
            return;

        try {
            xbMapping.startObject(ElasticsearchMapping.OBJECT_PROPERTIES);

            // Iterate over all the annotated fields
            for(String fieldName : fields.keySet()) {

                ElasticsearchNodeMetadata elasticsearchField = fields.get(fieldName);

                xbMapping.startObject(elasticsearchField.getFieldName());
                setFields(elasticsearchField, xbMapping);
       
                setXContentBuilderFields(xbMapping, elasticsearchField.getChildren());
                xbMapping.endObject();
            }

            xbMapping.endObject();
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    private static void setFields(ElasticsearchNodeMetadata elasticsearchField, XContentBuilder xbMapping) throws IOException {

        for(String attribute : elasticsearchField.getAttributes().keySet()) {
        	
            Object values = elasticsearchField.getAttributes().get(attribute);

           /* if(MultiFieldType[].class.isAssignableFrom(values.getClass())) {

                xbMapping.startObject(attribute);
                for(MultiFieldType fieldType:  (MultiFieldType[]) values) {
                    setElasticsearchParamenters(fieldType, xbMapping,fieldType._name());
                }
                xbMapping.endObject();
            }*/
            if(values instanceof Annotation[]){
            	xbMapping.startObject(attribute);
            	for(Annotation annotation:( Annotation[])values){
            		Map<String, Object> allAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
            		setElasticsearchParamenters(annotation,xbMapping,allAttributes.get("_name").toString());
            	}
            	xbMapping.endObject();
            }else if(values instanceof Annotation){
            	setElasticsearchParamenters(values,xbMapping,attribute);
            }else {
                xbMapping.field(attribute, elasticsearchField.getAttributes().get(attribute));
            }
        }
    }
    private static boolean hadValue(Annotation data){
    	boolean hadValue=false; 
    	Map<String, Object> allAttributes = AnnotationUtils.getAnnotationAttributes(data);
    	for(java.util.Map.Entry<String, Object> attr:allAttributes.entrySet()){
    		if(attr.getValue() instanceof Annotation){
    			hadValue=hadValue((Annotation)attr.getValue());
    		}else{
    			if(!(attr.getValue().equals(Empty.NULL)||
    					attr.getValue().equals(BooleanValue.NULL))||
    					attr.getValue().equals(FieldDatatype.NULL)||
    					attr.getValue().equals(DateFormat.NULL)){
    				hadValue=true;
    			}
    		}
    		if(hadValue)break;
    	}
    	return hadValue;
    }

    private static void setElasticsearchParamenters(Object data,XContentBuilder xbMapping,String attribute) throws IOException{
    	if(!hadValue((Annotation)data)){
    		return;
    	}
    	 xbMapping.startObject(attribute);
    	 if(data instanceof Annotation){
    		 Map<String, Object> allAttributes = AnnotationUtils.getAnnotationAttributes((Annotation)data);
    		 for(java.util.Map.Entry<String, Object> attr:allAttributes.entrySet()){
    			 
    			 if(attr.getValue() instanceof Annotation){
    				 setElasticsearchParamenters(attr.getValue(),xbMapping,attr.getKey().toLowerCase());
    			 }else{
    				if(!(attr.getValue().equals(Empty.NULL)||
    						attr.getValue().equals(BooleanValue.NULL)||
    						attr.getValue().equals(FieldDatatype.NULL)||
    						attr.getValue().equals(DateFormat.NULL))){
    					if(attr.getKey().toLowerCase().equals("_name")){
    					}else{
    						xbMapping.field(attr.getKey().toLowerCase(), attr.getValue());
    					}
    					
    				} 
    			 }
    		 }
    	 }

    	xbMapping.endObject();
    }
}
