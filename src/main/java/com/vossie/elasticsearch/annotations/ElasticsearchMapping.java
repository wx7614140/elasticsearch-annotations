package com.vossie.elasticsearch.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vossie.elasticsearch.annotations.common.ElasticsearchAlias;
import com.vossie.elasticsearch.annotations.common.ElasticsearchDocumentMetadata;
import com.vossie.elasticsearch.annotations.common.ElasticsearchIndexMetadata;
import com.vossie.elasticsearch.annotations.common.ElasticsearchNodeMetadata;
import com.vossie.elasticsearch.annotations.common.Empty;
import com.vossie.elasticsearch.annotations.enums.FieldDatatype;
import com.vossie.elasticsearch.annotations.mappingparameters.ESMappingProperties;
import com.vossie.elasticsearch.constants.Constants;

import scala.Option;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:33
 */
public abstract class ElasticsearchMapping {

    private static final HashMap<String, ElasticsearchDocumentMetadata> mappingCache = new HashMap<>();
    private static final HashMap<String, ElasticsearchIndexMetadata> indexCache = new HashMap<>();

    public static final String OBJECT_PROPERTIES= "properties";

    /**
     * Get the ElasticsearchDocument annotations for the provided class object.
     * @param clazz The class to inspect.
     * @return The ElasticsearchDocument details
     *
     */
    private static ElasticsearchDocument getElasticsearchType(Class<?> clazz) {

        return clazz.getAnnotation(ElasticsearchDocument.class);
    }
    private static ElasticsearchAlias getElasticsearchAliases(Class<?> clazz){
    	return new ElasticsearchAlias(clazz);
    }
    
    /**
     * Get the ElasticsearchDocument annotations for the provided class object.
     * @param clazz The class to inspect.
     * @return The ElasticsearchDocument details
     *
     */
    private static ESIndex getElasticsearchIndex(final Class<?> clazz) {

        ESIndex elasticsearchIndex = clazz.getAnnotation(ESIndex.class);

        if(elasticsearchIndex != null)
            return elasticsearchIndex;

        elasticsearchIndex = clazz.getSuperclass().getAnnotation(ESIndex.class);

        return elasticsearchIndex;
    }

    private static Field[] getAllDeclaredFields(final Class<?> clazz) {

        // Get all the fields declared in the class
        Field[] fieldsFromClass = clazz.getDeclaredFields();


        // Also get the super class fields to support polymorphism
        final List<Field> fieldsFromSuperList = new ArrayList<>();

        Class<?> current = clazz;
        while(current.getSuperclass()!=null){ // we don't want to process Object.class
            for(Field f : current.getSuperclass().getDeclaredFields())
                fieldsFromSuperList.add(f);
            current = current.getSuperclass();
        }

        final Field[] fieldsFromSuper = fieldsFromSuperList.toArray(new Field[fieldsFromSuperList.size()]);

        // Copy all the fields into a single array.
        Field[] fields = new Field[fieldsFromClass.length + fieldsFromSuper.length];

        if(fieldsFromClass.length > 0)
            System.arraycopy(fieldsFromClass,0,fields,0,fieldsFromClass.length);

        if(fieldsFromSuper.length > 0)
            System.arraycopy(fieldsFromSuper,0,fields,fieldsFromClass.length,fieldsFromSuper.length);

        return fields;
    }

    /**
     * Retrieve the metadata describing the annotated class fields.
     * @param clazz The class to scan
     * @return Returns a list containing the field metadata
     */
    private static Map<String, ElasticsearchNodeMetadata> getElasticsearchFieldsMetadata(final Class<?> clazz) {

        final Map<String, ElasticsearchNodeMetadata> elasticsearchFieldMappings = new HashMap<>();

        if(clazz == null)
            return elasticsearchFieldMappings;

        // get all the declared fields in the class.
        Field[] fields = getAllDeclaredFields(clazz);

        // Iterate over all the annotated fields
        for(Field field : fields) {

            // Get the annotation from the field
            ESMappingProperties elasticsearchType = field.getAnnotation(ESMappingProperties.class);

            // Skip over the field is not annotated
            if(elasticsearchType == null)
                continue;

            ElasticsearchNodeMetadata elasticsearchNodeMetadata;
            boolean isArray = false;
            Class<?> childClass = null;

            if(elasticsearchType.type().equals(FieldDatatype.GEO_POINT) ||
                    elasticsearchType.type().equals(FieldDatatype.OBJECT) ||
                    elasticsearchType.type().equals(FieldDatatype.NESTED)) {

                // If it is an array we need the component type
                isArray = field.getType().isArray();


                // Get the child class
                childClass = (isArray)
                        ? field.getType().getComponentType()
                        : field.getType();

                // Scala options handler
                if(Option.class.isAssignableFrom(field.getType())) {
                    ParameterizedType fieldType = (ParameterizedType) field.getGenericType();
                    childClass = (Class<?>) fieldType.getActualTypeArguments()[0];

                    isArray = childClass.isArray();

                    childClass = (isArray)
                            ? childClass.getComponentType()
                            : childClass;
                }


                if (Collection.class.isAssignableFrom(childClass)){
                    isArray = true;

                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType) {

                        ParameterizedType pType = (ParameterizedType)type;
                        Type[] arr = pType.getActualTypeArguments();

                        for (Type tp: arr) {
                            childClass = (Class<?>)tp;
                        }
                    }
                }
            }

            // Set the children
            elasticsearchNodeMetadata = new ElasticsearchNodeMetadata(field.getName(), elasticsearchType, isArray, getElasticsearchFieldsMetadata(childClass));

            // Add to the response list
            elasticsearchFieldMappings.put(elasticsearchNodeMetadata.getFieldName(), elasticsearchNodeMetadata);
        }

        return elasticsearchFieldMappings;
    }


    private static Map<String, ElasticsearchNodeMetadata> getElasticsearchSystemFieldsMetadata(final ESMetaField[] systemFields) {

        Map<String, ElasticsearchNodeMetadata> elasticsearchFieldMappings = new HashMap<>();

        // Add the system fields
        if(systemFields != null)
            for (ESMetaField systemField : systemFields){

                ElasticsearchNodeMetadata metadata = new ElasticsearchNodeMetadata(
                        systemField._fieldName().toString(),
                        systemField,
                        new HashMap<String,ElasticsearchNodeMetadata>()
                );

                elasticsearchFieldMappings.put(systemField._fieldName().toString(), metadata);
            }

        return elasticsearchFieldMappings;
    }
//    private static Map<String, ElasticsearchDynamicTemplate> getElasticsearchDynamicTemplates(final DynamicTemplates[] dynamicTemplates) {
//    	 final Map<String, ElasticsearchDynamicTemplate> elasticsearchFieldMappings = new HashMap<>();
//
//         if(dynamicTemplates == null||dynamicTemplates.length==0)
//             return elasticsearchFieldMappings;
//         for(DynamicTemplates template :dynamicTemplates){
//        	 ElasticsearchDynamicTemplate dynamictemplate = new ElasticsearchDynamicTemplate(template._name(), template);
//        	 elasticsearchFieldMappings.put(template._name(), dynamictemplate);
//         }
//         return elasticsearchFieldMappings;
//    }
    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     */
    public static ElasticsearchDocumentMetadata get(final Class<?> clazz) {

        // Check the cache to see if we have already parsed this reference.
        if(mappingCache.containsKey(clazz.getName()))
            return mappingCache.get(clazz.getName());

        // Get the annotation.
        ElasticsearchDocument elasticsearchDocument = getElasticsearchType(clazz);

        if(elasticsearchDocument == null)
            return null;

        // If a class is provided to get the index information from then use that else try to see if the current class has the ES index annotation set.
        ElasticsearchIndexMetadata elasticsearchIndexMetadata = (elasticsearchDocument.index().isAssignableFrom(Empty.class))
                ? getIndex(clazz)
                : getIndex(elasticsearchDocument.index());

        if(elasticsearchIndexMetadata == null) {
            throw new RuntimeException("No ElasticsearchIndex annotation found containing the index information for " + elasticsearchDocument.type());
        }

        ElasticsearchDocumentMetadata documentMetadata = new ElasticsearchDocumentMetadata(
                clazz,
                elasticsearchDocument,
                getElasticsearchFieldsMetadata(clazz),
                getElasticsearchSystemFieldsMetadata(elasticsearchDocument._elasticsearchFields()),
                /*getElasticsearchDynamicTemplates(elasticsearchDocument.dynamic_templates()),*/
                getElasticsearchAliases(clazz),
                elasticsearchIndexMetadata
        );

        // Add this item to the local cache for fast lookup.
        mappingCache.put(clazz.getName(), documentMetadata );

        // Return the reference.
        return mappingCache.get(clazz.getName());
    }
    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     * 多线程下不同步会导致date参数重复出现，所以该方法必须同步
     */
//    public synchronized static ElasticsearchDocumentMetadata get(final Class<?> clazz,final Date date) {
//    	
//    	// Check the cache to see if we have already parsed this reference.
//    	if(mappingCache.containsKey(clazz.getName()+Constants.format.format(date)))
//    		return mappingCache.get(clazz.getName()+Constants.format.format(date));
//    	
//    	// Get the annotation.
//    	ElasticsearchDocument elasticsearchDocument = getElasticsearchType(clazz);
//    	
//    	if(elasticsearchDocument == null)
//    		return null;
//    	
//    	// If a class is provided to get the index information from then use that else try to see if the current class has the ES index annotation set.
//    	ElasticsearchIndexMetadata elasticsearchIndexMetadata = (elasticsearchDocument.index().isAssignableFrom(Empty.class))
//    			? getIndex(clazz,date)
//    					: getIndex(elasticsearchDocument.index(),date);
//    			
//    			if(elasticsearchIndexMetadata == null) {
//    				throw new RuntimeException("No ElasticsearchIndex annotation found containing the index information for " + elasticsearchDocument.type());
//    			}
//    			
//    			ElasticsearchDocumentMetadata documentMetadata = new ElasticsearchDocumentMetadata(
//    					clazz,
//    					elasticsearchDocument,
//    					getElasticsearchFieldsMetadata(clazz),
//    					getElasticsearchSystemFieldsMetadata(elasticsearchDocument._elasticsearchFields()),
//    					/*getElasticsearchDynamicTemplates(elasticsearchDocument.dynamic_templates()),*/
//    					getElasticsearchAliases(clazz),
//    					elasticsearchIndexMetadata
//    					);
//    			
//    			// Add this item to the local cache for fast lookup.
//    			mappingCache.put(clazz.getName()+Constants.format.format(date), documentMetadata );
//    			
//    			// Return the reference.
//    			return mappingCache.get(clazz.getName()+Constants.format.format(date));
//    }
    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     * 多线程下不同步会导致date参数重复出现，所以该方法必须同步
     */
//    public synchronized static ElasticsearchDocumentMetadata get(final Class<?> clazz,final String date) {
//    	
//    	// Check the cache to see if we have already parsed this reference.
//    	if(mappingCache.containsKey(clazz.getName()+date))
//    		return mappingCache.get(clazz.getName()+date);
//    	
//    	// Get the annotation.
//    	ElasticsearchDocument elasticsearchDocument = getElasticsearchType(clazz);
//    	
//    	if(elasticsearchDocument == null)
//    		return null;
//    	
//    	// If a class is provided to get the index information from then use that else try to see if the current class has the ES index annotation set.
//    	ElasticsearchIndexMetadata elasticsearchIndexMetadata = (elasticsearchDocument.index().isAssignableFrom(Empty.class))
//    			? getIndex(clazz,date)
//    					: getIndex(elasticsearchDocument.index(),date);
//    			
//    			if(elasticsearchIndexMetadata == null) {
//    				throw new RuntimeException("No ElasticsearchIndex annotation found containing the index information for " + elasticsearchDocument.type());
//    			}
//    			
//    			ElasticsearchDocumentMetadata documentMetadata = new ElasticsearchDocumentMetadata(
//    					clazz,
//    					elasticsearchDocument,
//    					getElasticsearchFieldsMetadata(clazz),
//    					getElasticsearchSystemFieldsMetadata(elasticsearchDocument._elasticsearchFields()),
//    					/*getElasticsearchDynamicTemplates(elasticsearchDocument.dynamic_templates()),*/
//    					getElasticsearchAliases(clazz),
//    					elasticsearchIndexMetadata
//    					);
//    			
//    			// Add this item to the local cache for fast lookup.
//    			mappingCache.put(clazz.getName()+date, documentMetadata );
//    			
//    			// Return the reference.
//    			return mappingCache.get(clazz.getName()+date);
//    }
    /**
     * Get the document and field metadata associated with this mapping by class.
     * @param clazz The class to inspect.
     * @return Returns the meta data used to describe this entity.
     */
    public static ElasticsearchIndexMetadata getIndex(Class<?> clazz) {

        // Get the annotation.
        ElasticsearchIndexMetadata elasticsearchIndex = new ElasticsearchIndexMetadata(clazz, getElasticsearchIndex(clazz));

        // Add this item to the local cache for fast lookup.
        if(!indexCache.containsKey(elasticsearchIndex.getIndexName()))
            indexCache.put(elasticsearchIndex.getIndexName(), elasticsearchIndex );

        // Return the reference.
        return elasticsearchIndex;
    }
    public static ElasticsearchIndexMetadata getIndex(Class<?> clazz,Date date) {
    	
    	// Get the annotation.
    	ElasticsearchIndexMetadata elasticsearchIndex = new ElasticsearchIndexMetadata(clazz, getElasticsearchIndex(clazz),date);
    	
    	// Add this item to the local cache for fast lookup.
    	if(!indexCache.containsKey(elasticsearchIndex.getIndexName()))
    		indexCache.put(elasticsearchIndex.getIndexName(), elasticsearchIndex );
    	
    	// Return the reference.
    	return elasticsearchIndex;
    }
    public static ElasticsearchIndexMetadata getIndex(Class<?> clazz,String date) {
    	
    	// Get the annotation.
    	ElasticsearchIndexMetadata elasticsearchIndex = new ElasticsearchIndexMetadata(clazz, getElasticsearchIndex(clazz),date);
    	
    	// Add this item to the local cache for fast lookup.
    	if(!indexCache.containsKey(elasticsearchIndex.getIndexName()))
    		indexCache.put(elasticsearchIndex.getIndexName(), elasticsearchIndex );
    	
    	// Return the reference.
    	return elasticsearchIndex;
    }
    public void clearCache(){
    	indexCache.clear();
    	mappingCache.clear();
    }
    public static boolean clearIndexCache(){
    	indexCache.clear();
    	return indexCache.size()==0;
    }
}
