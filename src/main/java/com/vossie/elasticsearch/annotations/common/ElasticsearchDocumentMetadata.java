package com.vossie.elasticsearch.annotations.common;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.common.settings.Settings;
import org.springframework.core.annotation.AnnotationUtils;

import com.vossie.elasticsearch.annotations.ElasticsearchDocument;
import com.vossie.elasticsearch.annotations.ElasticsearchMapping;
import com.vossie.elasticsearch.annotations.alias.ESAlias;
import com.vossie.elasticsearch.annotations.alias.ESAliases;
import com.vossie.elasticsearch.annotations.enums.FieldDatatype;
import com.vossie.elasticsearch.annotations.enums.MetaFieldName;

/**
 * Copyright © 2013 Carel Vosloo.
 * User: cvosloo
 * Date: 06/12/2013
 * Time: 09:34
 */
@SuppressWarnings("unused")
public class ElasticsearchDocumentMetadata {

    private final String typeName;
    private final ElasticsearchDocument elasticsearchDocument;
    private final Map<String,ElasticsearchNodeMetadata> elasticsearchTypes;
    private final Map<String,ElasticsearchNodeMetadata> elasticsearchFields;
//    private final Map<String,ElasticsearchDynamicTemplate> elasticsearchDynamicTemplates;
    private final ElasticsearchAlias elasticsearchAliases;
    private final Map<String, Object> attributes;
    private final ElasticsearchIndexMetadata elasticsearchIndex;

    public ElasticsearchDocumentMetadata(Class<?> clazz, ElasticsearchDocument elasticsearchDocument, Map<String, ElasticsearchNodeMetadata> elasticsearchTypes, Map<String, ElasticsearchNodeMetadata> elasticsearchFields,/*Map<String,ElasticsearchDynamicTemplate> elasticsearchDynamicTemplates,*/ ElasticsearchAlias elasticsearchAliases, ElasticsearchIndexMetadata elasticsearchIndex) {

        // Set the type name
        this.typeName = (elasticsearchDocument.type().equals(Empty.NULL))
                ? UPPER_CAMEL.to(LOWER_HYPHEN, clazz.getSimpleName().toLowerCase())
                : elasticsearchDocument.type();

        this.elasticsearchDocument = elasticsearchDocument;
        this.elasticsearchTypes = Collections.unmodifiableMap(elasticsearchTypes);
        this.elasticsearchFields = Collections.unmodifiableMap(elasticsearchFields);
//        this.elasticsearchDynamicTemplates=Collections.unmodifiableMap(elasticsearchDynamicTemplates);
        this.elasticsearchAliases=elasticsearchAliases;
        // Todo: Find a way of doing this without the spring dependency.
        this.attributes = Collections.unmodifiableMap(AnnotationUtils.getAnnotationAttributes(elasticsearchDocument));

        this.elasticsearchIndex = elasticsearchIndex;
    }

    public Map<String, ElasticsearchNodeMetadata> getElasticsearchProperties() {
        return elasticsearchTypes;
    }

    /**
     * Get the index name.
     * @return index name
     */
    public String getIndexName(){
        return this.getElasticsearchIndex().getIndexName();
    }
    public Settings.Builder getSettings(){
    	return this.getElasticsearchIndex().getSettings();
    }

    /**
     * Get the type name for this index mapping.
     * If it is not set the class name will be changed from upper camel case to lower hyphen case.
     * @return
     */
    public String getTypeName() {
        return this.typeName;
    }
    public ElasticsearchAlias getElasticsearchAliases(){
    	return this.elasticsearchAliases;
    }
    /**
     * Get the parent
     * @return Parent
     */
    public ElasticsearchDocumentMetadata getParent()  {

        Class<?> parentClass = (this.elasticsearchFields.get(MetaFieldName._PARENT.toString()).getAttributes().containsKey("type"))
                    ?(Class<?>) this.elasticsearchFields.get(MetaFieldName._PARENT.toString()).getAttributes().get("type")
                    : null;

        if(parentClass != null)
            return ElasticsearchMapping.get(parentClass);

        return null;
    }


    public boolean hasParent() {
        return (this.elasticsearchFields.containsKey(MetaFieldName._PARENT.toString()));
    }


    /**
     * Should we store the source data in the index.
     * The _source field is an automatically generated field that stores the actual JSON that was used as the indexed document. It is not indexed (searchable), just stored. When executing "fetch" requests, like get or search, the _source field is returned by default.
     *
     * Though very handy to have around, the source field does incur storage overhead within the index. For this reason, it can be disabled. For example:
     * @return True if the source is stored.
     */
    public boolean isSourceStoredWithIndex() {
        return (this.elasticsearchFields.containsKey(MetaFieldName._SOURCE.toString()))
                ? Boolean.valueOf(this.elasticsearchFields.get(MetaFieldName._SOURCE.toString()).getAttributes().get("enabled").toString())
                : true;
    }

    /**
     * Get a list of field names.
     * @return
     */
    public String[] getPropertyNames() {
        return this.getElasticsearchProperties().keySet().toArray(new String[this.getElasticsearchProperties().size()]);
    }

    /**
     * Get all the fields in this mapping.
     * @return Map of indexed fields.
     */
    public Map<String, ElasticsearchNodeMetadata> getProperties() {
        return this.elasticsearchTypes;
    }
//    public Alias getAlias(){
//    	return this.elasticsearchAlias;
//    }
    /**
     * Get the metadata associated with a specific field.
     * @param fieldName The name of the field to retrieve.
     * @return The meta data if found otherwise null.
     */
    public ElasticsearchNodeMetadata getPropertyMetaData(String fieldName) {

        return (this.elasticsearchTypes.containsKey(fieldName))
                ? this.elasticsearchTypes.get(fieldName)
                : null;
    }

    public ElasticsearchIndexMetadata getElasticsearchIndex() {
        return elasticsearchIndex;
    }
//    public Map<String, ElasticsearchDynamicTemplate> getDynamicTemplates() {
//    	return elasticsearchDynamicTemplates;
//    }
//    public ElasticsearchDynamicTemplate getDynamicTemplate(String name){
//    	return elasticsearchDynamicTemplates.get(name);
//    }
    /**
     * Get a list of field names.
     * @return
     */
    public String[] getFieldNames() {
        return this.getFields().keySet().toArray(new String[this.getFields().size()]);
    }

    /**
     * Get all the fields in this mapping.
     * @return Map of indexed fields.
     */
    public Map<String, ElasticsearchNodeMetadata> getFields() {
        return this.elasticsearchFields;
    }

    /**
     * Get the metadata associated with a specific field.
     * @param fieldName The name of the field to retrieve.
     * @return The meta data if found otherwise null.
     */
    public ElasticsearchNodeMetadata getFieldMetaData(String fieldName) {

        if(this.elasticsearchFields.containsKey(fieldName))
            return this.elasticsearchFields.get(fieldName);

        return null;
    }

    /**
     * Get a json formatted string representing the mapping;
     * @return The mapping.
     */
    @Override
    public String toString() {
        try {
            return toMapping();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a json formatted string representing the mapping;
     * @return The mapping.
     * @throws IOException
     */
    public String toMapping() throws IOException {

        return MetadataXContentBuilder.getXContentBuilder(this).string();
    }
    public String getMapping(String field) throws IOException {
    	
    	return MetadataXContentBuilder.getXContentBuilder(this).field(field).string();
    }

    private Map<String,FieldDatatype> _allAsFlatList;
    public Map<String,FieldDatatype> asMap(){

        if(_allAsFlatList != null)
            return _allAsFlatList;

        Map<String,FieldDatatype> response = new HashMap<>();

        response.put(getTypeName(),FieldDatatype.OBJECT);


        for (String field : this.getElasticsearchProperties().keySet()) {


            ElasticsearchNodeMetadata nodeMetadata = this.getElasticsearchProperties().get(field);

            String prefix=getTypeName() +"."+ nodeMetadata.getFieldName();
            response.put(prefix, (FieldDatatype) nodeMetadata.getAttributes().get("type"));

            loopThroughFields(response,prefix,nodeMetadata);
        }

        _allAsFlatList=response;
        return _allAsFlatList;
    }

    private Map<String,FieldDatatype> loopThroughFields(Map<String,FieldDatatype> list, String preFix, ElasticsearchNodeMetadata nodeMetadata){

        if(list==null || list.size()<1)
            return list;

        for (String field : nodeMetadata.getChildren().keySet()) {

            ElasticsearchNodeMetadata n = nodeMetadata.getChildren().get(field);
            String current = preFix + "." + field;
            list.put(current, (FieldDatatype) n.getAttributes().get("type"));
            loopThroughFields(list, current, n);
        }

        return list;
    }
}
