package com.vossie.elasticsearch.annotations.common;

import java.util.Date;

import org.elasticsearch.common.settings.Settings;

import com.vossie.elasticsearch.annotations.ESIndex;
import com.vossie.elasticsearch.annotations.enums.SettingsFormat;
import com.vossie.elasticsearch.constants.Constants;

/**
 * Copyright (c) 2014 Carel Vosloo <code@bronzegate.com>
 * All rights reserved. No warranty, explicit or implicit, provided.
 * Created: 25/10/14 08:50 by carel
 */
public class ElasticsearchIndexMetadata {

    private final String indexName;

    private final Settings.Builder settings;

    private final Class<?> clazz;
    public ElasticsearchIndexMetadata(Class<?> clazz, ESIndex elasticsearchIndex) {
        this.clazz = clazz;
        this.indexName = (elasticsearchIndex ==null) ? null : elasticsearchIndex._indexName().toLowerCase();
        this.settings = (elasticsearchIndex ==null) ? null : loadSettings(elasticsearchIndex.settingsFormat(), elasticsearchIndex.settings());
    }
    public ElasticsearchIndexMetadata(Class<?> clazz, ESIndex elasticsearchIndex,Date date) {
    	this.clazz = clazz;
    	this.indexName = (elasticsearchIndex ==null) ? null : elasticsearchIndex._indexName().toLowerCase()
    			.indexOf(Constants.index_name_date_format)==-1?
    					elasticsearchIndex._indexName():
    						elasticsearchIndex._indexName().replace(Constants.index_name_date_format, Constants.format.format(date)).toLowerCase();
    					this.settings = (elasticsearchIndex ==null) ? null : loadSettings(elasticsearchIndex.settingsFormat(), elasticsearchIndex.settings());
    }
    public ElasticsearchIndexMetadata(Class<?> clazz, ESIndex elasticsearchIndex,String date) {
    	this.clazz = clazz;
    	this.indexName = (elasticsearchIndex ==null) ? null : elasticsearchIndex._indexName().toLowerCase()+"-"+date;
		this.settings = (elasticsearchIndex ==null) ? null : loadSettings(elasticsearchIndex.settingsFormat(), elasticsearchIndex.settings());
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getIndexName() {
        return indexName;
    }

    public Settings.Builder getSettings() {
        return settings;
    }

    public boolean hasSettings() {
        return (getSettings() != null);
    }

    private Settings.Builder loadSettings(SettingsFormat settingsFormat, String source) {

        if(source == null || source.isEmpty())
            return null;

        switch (settingsFormat) {
            case JSON: {
                return Settings.settingsBuilder().loadFromSource(source);
            }
        }

        return null;
    }
}
