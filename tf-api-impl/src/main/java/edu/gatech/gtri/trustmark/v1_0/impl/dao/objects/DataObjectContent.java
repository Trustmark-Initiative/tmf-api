package edu.gatech.gtri.trustmark.v1_0.impl.dao.objects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;

/**
 * This object holds the larger String content for TDs, TIPs, and other objects.  It is a separate object to speed-up
 * loading the essential details like name, version and description.
 * <br/><br/>
 * @user brad
 * @date 9/15/16
 */
@DatabaseTable(tableName = "tmfapi_data_object_cache")
public class DataObjectContent {
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public DataObjectContent(){
        this.createDate = Calendar.getInstance().getTime();
    }
    //====================================================================================================================
    //  INSTANCE VARIABLES
    //====================================================================================================================
    @DatabaseField(generatedId = true, columnName = "id", dataType = DataType.INTEGER_OBJ, useGetSet = true)
    private Integer id;
    @DatabaseField(canBeNull = false, columnName = "identifier_url", dataType = DataType.STRING, useGetSet = true, unique = true)
    private String identifierURL;
    @DatabaseField(canBeNull = false, columnName = "date_created", dataType = DataType.DATE_STRING, useGetSet = true)
    public Date createDate;
    @DatabaseField(canBeNull = false, columnName = "size", dataType = DataType.LONG_OBJ, useGetSet = true)
    public Long size;
    @DatabaseField(canBeNull = false, columnName = "contentType", dataType = DataType.STRING, useGetSet = true)
    public String contentType;
    /**
     * Note that using 'TEXT' seems to work here for H2, also.  @See TI-1640 for more details.
     */
    @DatabaseField(canBeNull = false, columnName = "content", columnDefinition = "LONGTEXT", useGetSet = true)
    public String content;
    //====================================================================================================================
    //  GETTERS
    //====================================================================================================================
    public Integer getId() {
        return id;
    }
    public String getIdentifierURL() {
        return identifierURL;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public Long getSize() {
        return size;
    }
    public String getContentType() {
        return contentType;
    }
    public String getContent() {
        return content;
    }
    //====================================================================================================================
    //  SETTERS
    //====================================================================================================================
    public void setId(Integer id) {
        this.id = id;
    }
    public void setIdentifierURL(String identifierURL) {
        this.identifierURL = identifierURL;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public void setContent(String content) {
        this.content = content;
    }
    //====================================================================================================================
    //  PUBLIC METHODS
    //====================================================================================================================
    public String toString() {
        return "DataObjectContent["+this.getIdentifierURL()+"]";
    }

}/* end DataObjectContent */