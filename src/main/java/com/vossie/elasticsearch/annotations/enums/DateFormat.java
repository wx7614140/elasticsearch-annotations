package com.vossie.elasticsearch.annotations.enums;

import com.vossie.elasticsearch.annotations.common.Empty;

public enum DateFormat {
	NULL {
		@Override
		public String toString() {
			return Empty.NULL;
		}
	},
	EPOCH_MILLIS {
        @Override
        public String toString() {
            return "epoch_millis";
        }
    },
	EPOCH_SECOND {
    	@Override
    	public String toString() {
    		return "epoch_second";
    	}
    },
	DATE_OPTIONAL_TIME  {
    	@Override
    	public String toString() {
    		return "date_optional_time";
    	}
    },
	STRICT_DATE_OPTIONAL_TIME  {
    	@Override
    	public String toString() {
    		return "strict_date_optional_time";
    	}
    },
	BASIC_DATE  {
    	@Override
    	public String toString() {
    		return "basic_date";
    	}
    },
	BASIC_DATE_TIME  {
    	@Override
    	public String toString() {
    		return "basic_date_time";
    	}
    },
	BASIC_DATE_TIME_NO_MILLIS  {
    	@Override
    	public String toString() {
    		return "basic_date_time_no_millis";
    	}
    },
	BASIC_ORDINAL_DATE  {
    	@Override
    	public String toString() {
    		return "basic_ordinal_date";
    	}
    },
	BASIC_ORDINAL_DATE_TIME  {
    	@Override
    	public String toString() {
    		return "basic_ordinal_date_time";
    	}
    },
	BASIC_ORDINAL_DATE_TIME_NO_MILLIS  {
    	@Override
    	public String toString() {
    		return "basic_ordinal_date_time_no_millis";
    	}
    },
	BASIC_TIME  {
    	@Override
    	public String toString() {
    		return "basic_time";
    	}
    },
	BASIC_TIME_NO_MILLIS  {
    	@Override
    	public String toString() {
    		return "basic_time_no_millis";
    	}
    },
	BASIC_T_TIME  {
    	@Override
    	public String toString() {
    		return "basic_t_time";
    	}
    },
	BASIC_T_TIME_NO_MILLIS  {
    	@Override
    	public String toString() {
    		return "basic_t_time_no_millis";
    	}
    },
	BASIC_WEEK_DATE   {
    	@Override
    	public String toString() {
    		return "basic_week_date";
    	}
    },
	STRICT_BASIC_WEEK_DATE   {
    	@Override
    	public String toString() {
    		return "strict_basic_week_date";
    	}
    },
	BASIC_WEEK_DATE_TIME    {
    	@Override
    	public String toString() {
    		return "basic_week_date_time";
    	}
    },
	STRICT_BASIC_WEEK_DATE_TIME   {
    	@Override
    	public String toString() {
    		return "strict_basic_week_date_time";
    	}
    },
	BASIC_WEEK_DATE_TIME_NO_MILLIS    {
    	@Override
    	public String toString() {
    		return "basic_week_date_time_no_millis";
    	}
    },
	STRICT_BASIC_WEEK_DATE_TIME_NO_MILLIS   {
    	@Override
    	public String toString() {
    		return "strict_basic_week_date_time_no_millis";
    	}
    },
	DATE    {
    	@Override
    	public String toString() {
    		return "date";
    	}
    },
	STRICT_DATE    {
    	@Override
    	public String toString() {
    		return "strict_date";
    	}
    },
	DATE_HOUR{
    	@Override
    	public String toString() {
    		return "date_hour";
    	}
    },
	STRICT_DATE_HOUR{
    	@Override
    	public String toString() {
    		return "strict_date_hour";
    	}
    },
	DATE_HOUR_MINUTE{
    	@Override
    	public String toString() {
    		return "date_hour_minute";
    	}
    },
	STRICT_DATE_HOUR_MINUTE{
    	@Override
    	public String toString() {
    		return "strict_date_hour_minute";
    	}
    },
	DATE_HOUR_MINUTE_SECOND{
    	@Override
    	public String toString() {
    		return "date_hour_minute_second";
    	}
    },
	STRICT_DATE_HOUR_MINUTE_SECOND{
    	@Override
    	public String toString() {
    		return "strict_date_hour_minute_second";
    	}
    },
	DATE_HOUR_MINUTE_SECOND_FRACTION{
    	@Override
    	public String toString() {
    		return "date_hour_minute_second_fraction";
    	}
    },
	STRICT_DATE_HOUR_MINUTE_SECOND_FRACTION{
    	@Override
    	public String toString() {
    		return "strict_date_hour_minute_second_fraction";
    	}
    },
	DATE_HOUR_MINUTE_SECOND_MILLIS{
    	@Override
    	public String toString() {
    		return "date_hour_minute_second_millis";
    	}
    },
	STRICT_DATE_HOUR_MINUTE_SECOND_MILLIS{
    	@Override
    	public String toString() {
    		return "strict_date_hour_minute_second_millis";
    	}
    },
	DATE_TIME{
    	@Override
    	public String toString() {
    		return "date_time";
    	}
    },
	STRICT_DATE_TIME{
    	@Override
    	public String toString() {
    		return "strict_date_time";
    	}
    },
	DATE_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "date_time_no_millis";
    	}
    },
	STRICT_DATE_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "strict_date_time_no_millis";
    	}
    },
	HOUR{
    	@Override
    	public String toString() {
    		return "hour";
    	}
    },
	STRICT_HOUR{
    	@Override
    	public String toString() {
    		return "strict_hour";
    	}
    },
	HOUR_MINUTE{
    	@Override
    	public String toString() {
    		return "hour_minute";
    	}
    },
	STRICT_HOUR_MINUTE{
    	@Override
    	public String toString() {
    		return "strict_hour_minute";
    	}
    },
	HOUR_MINUTE_SECOND{
    	@Override
    	public String toString() {
    		return "hour_minute_second";
    	}
    },
	STRICT_HOUR_MINUTE_SECOND{
    	@Override
    	public String toString() {
    		return "strict_hour_minute_second";
    	}
    },
	HOUR_MINUTE_SECOND_FRACTION{
    	@Override
    	public String toString() {
    		return "hour_minute_second_fraction";
    	}
    },
	STRICT_HOUR_MINUTE_SECOND_FRACTION{
    	@Override
    	public String toString() {
    		return "strict_hour_minute_second_fraction";
    	}
    },
	HOUR_MINUTE_SECOND_MILLIS{
    	@Override
    	public String toString() {
    		return "hour_minute_second_millis";
    	}
    },
	STRICT_HOUR_MINUTE_SECOND_MILLIS{
    	@Override
    	public String toString() {
    		return "strict_hour_minute_second_millis";
    	}
    },
	ORDINAL_DATE{
    	@Override
    	public String toString() {
    		return "ordinal_date";
    	}
    },
	STRICT_ORDINAL_DATE{
    	@Override
    	public String toString() {
    		return "strict_ordinal_date";
    	}
    },
	ORDINAL_DATE_TIME{
    	@Override
    	public String toString() {
    		return "ordinal_date_time";
    	}
    },
	STRICT_ORDINAL_DATE_TIME{
    	@Override
    	public String toString() {
    		return "strict_ordinal_date_time";
    	}
    },
	ORDINAL_DATE_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "ordinal_date_time_no_millis";
    	}
    },
	STRICT_ORDINAL_DATE_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "strict_ordinal_date_time_no_millis";
    	}
    },
	TIME{
    	@Override
    	public String toString() {
    		return "time";
    	}
    },
	STRICT_TIME{
    	@Override
    	public String toString() {
    		return "strict_time";
    	}
    },
	TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "time_no_millis";
    	}
    },
	STRICT_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "strict_time_no_millis";
    	}
    },
	T_TIME{
    	@Override
    	public String toString() {
    		return "t_time";
    	}
    },
	STRICT_T_TIME{
    	@Override
    	public String toString() {
    		return "strict_t_time";
    	}
    },
	T_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "t_time_no_millis";
    	}
    },
	STRICT_T_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "strict_t_time_no_millis";
    	}
    },
	WEEK_DATE{
    	@Override
    	public String toString() {
    		return "week_date";
    	}
    },
	STRICT_WEEK_DATE{
    	@Override
    	public String toString() {
    		return "strict_week_date";
    	}
    },
	WEEK_DATE_TIME{
    	@Override
    	public String toString() {
    		return "week_date_time";
    	}
    },
	STRICT_WEEK_DATE_TIME{
    	@Override
    	public String toString() {
    		return "strict_week_date_time";
    	}
    },
	WEEK_DATE_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "week_date_time_no_millis";
    	}
    },
	STRICT_WEEK_DATE_TIME_NO_MILLIS{
    	@Override
    	public String toString() {
    		return "strict_week_date_time_no_millis";
    	}
    },
	WEEKYEAR{
    	@Override
    	public String toString() {
    		return "weekyear";
    	}
    },
	STRICT_WEEKYEAR{
    	@Override
    	public String toString() {
    		return "strict_weekyear";
    	}
    },
	WEEKYEAR_WEEK{
    	@Override
    	public String toString() {
    		return "weekyear_week";
    	}
    },
	STRICT_WEEKYEAR_WEEK{
    	@Override
    	public String toString() {
    		return "strict_weekyear_week";
    	}
    },
	WEEKYEAR_WEEK_DAY{
    	@Override
    	public String toString() {
    		return "weekyear_week_day";
    	}
    },
	STRICT_WEEKYEAR_WEEK_DAY{
    	@Override
    	public String toString() {
    		return "strict_weekyear_week_day";
    	}
    },
	YEAR{
    	@Override
    	public String toString() {
    		return "year";
    	}
    },
	STRICT_YEAR{
    	@Override
    	public String toString() {
    		return "strict_year";
    	}
    },
	YEAR_MONTH{
    	@Override
    	public String toString() {
    		return "year_month";
    	}
    },
	STRICT_YEAR_MONTH{
    	@Override
    	public String toString() {
    		return "strict_year_month";
    	}
    },
	YEAR_MONTH_DAY{
    	@Override
    	public String toString() {
    		return "STRICT_YEAR_MONTH";
    	}
    },
	STRICT_YEAR_MONTH_DAY{
    	@Override
    	public String toString() {
    		return "strict_year_month_day";
    	}
    },
	YYYY_MM_DD{
    	@Override
    	public String toString() {
    		return "yyyy-MM-dd";
    	}
    },
	YYYY_MM_DD_HH{
    	@Override
    	public String toString() {
    		return "yyyy-MM-dd-HH";
    	}
    },
	YYYY_MM_DD_HH_MM_SS{
    	@Override
    	public String toString() {
    		return "yyyy-MM-dd HH:mm:ss";
    	}
    }
}
