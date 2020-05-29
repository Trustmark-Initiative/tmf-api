package edu.gatech.gtri.trustmark.v1_0.impl.dao.objects;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import edu.gatech.gtri.trustmark.v1_0.dao.TrustmarkFrameworkDao;

import java.util.Calendar;
import java.util.Date;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 9/14/16
 */
@DatabaseTable(tableName = "tmfapi_tip_cache")
public class TrustInteroperabilityProfileCache {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    public static final String COL_ID = "id";
    public static final String COL_IDENTIFIER_URL = "identifier_url";
    public static final String COL_NAME = "td_name";
    public static final String COL_NUMBER = "td_number";
    public static final String COL_VERSION = "td_version";
    public static final String COL_DESCRIPTION = "td_description";
    public static final String COL_PUBLICATION_DATE = "td_publication_timestamp";
    public static final String COL_ORG_ID = "publishing_org_id";
    public static final String COL_ORG_NAME = "publishing_org_name";
    public static final String COL_DEPRECATED = "deprecated";
    public static final String COL_LAST_UPDATED = "last_updated";
    public static final String COL_LAST_CHECKED = "last_checked";
    public static final String COL_DATE_CREATED = "date_created";
    public static final String COL_ENABLED = "enabled";
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================
    public static String toColumn(TrustmarkFrameworkDao.SortField sortField){
        // We cheat for now.
        return TrustmarkDefinitionCache.toColumn(sortField);
    }
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public TrustInteroperabilityProfileCache(){
        this.createDate = Calendar.getInstance().getTime();
        this.enabled = true;
        this.lastUpdated = this.createDate;
        this.lastChecked = this.createDate;
    }
    //==================================================================================================================
    //  INSTANCE FIELDS
    //==================================================================================================================
    @DatabaseField(generatedId = true, columnName = COL_ID, dataType = DataType.INTEGER_OBJ, useGetSet = true)
    private Integer id;
    @DatabaseField(canBeNull = false, columnName = COL_IDENTIFIER_URL, dataType = DataType.STRING, useGetSet = true, unique = true)
    private String identifierURL;
    @DatabaseField(canBeNull = false, columnName = COL_NAME, dataType = DataType.STRING, useGetSet = true)
    private String name;
    @DatabaseField(canBeNull = false, columnName = COL_NUMBER, dataType = DataType.INTEGER_OBJ, useGetSet = true)
    private Integer number;
    @DatabaseField(canBeNull = false, columnName = COL_VERSION, dataType = DataType.STRING, useGetSet = true)
    private String version;
    @DatabaseField(canBeNull = false, columnName = COL_DESCRIPTION, dataType = DataType.LONG_STRING, useGetSet = true)
    private String description;
    @DatabaseField(canBeNull = false, columnName = COL_ORG_ID, dataType = DataType.STRING, useGetSet = true)
    private String publishingOrgId;
    @DatabaseField(canBeNull = false, columnName = COL_ORG_NAME, dataType = DataType.STRING, useGetSet = true)
    private String publishingOrgName;
    @DatabaseField(canBeNull = false, columnName = COL_PUBLICATION_DATE, dataType = DataType.DATE_STRING, useGetSet = true)
    private Date publicationDateTime;
    @DatabaseField(canBeNull = false, columnName = COL_LAST_UPDATED, dataType = DataType.DATE_STRING, useGetSet = true)
    public Date lastUpdated;
    @DatabaseField(canBeNull = false, columnName = COL_LAST_CHECKED, dataType = DataType.DATE_STRING, useGetSet = true)
    public Date lastChecked;
    @DatabaseField(canBeNull = false, columnName = COL_DATE_CREATED, dataType = DataType.DATE_STRING, useGetSet = true)
    public Date createDate;
    @DatabaseField(canBeNull = false, columnName = COL_ENABLED, dataType = DataType.BOOLEAN_OBJ, useGetSet = true)
    public Boolean enabled;
    //==================================================================================================================
    //  GETTERS
    //==================================================================================================================
    public Integer getId() {
        return id;
    }
    public Integer getNumber() {
        return number;
    }
    public String getIdentifierURL() {
        return identifierURL;
    }
    public String getName() {
        return name;
    }
    public String getVersion() {
        return version;
    }
    public String getDescription() {
        return description;
    }
    public Date getPublicationDateTime() {
        return publicationDateTime;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public Date getLastChecked() {
        return lastChecked;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public String getPublishingOrgId() {
        return publishingOrgId;
    }
    public String getPublishingOrgName() {
        return publishingOrgName;
    }
    //==================================================================================================================
    //  SETTERS
    //==================================================================================================================
    public void setId(Integer id) {
        this.id = id;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public void setIdentifierURL(String identifierURL) {
        this.identifierURL = identifierURL;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPublicationDateTime(Date publicationDateTime) {
        this.publicationDateTime = publicationDateTime;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setLastChecked(Date lastChecked) {
        this.lastChecked = lastChecked;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public void setPublishingOrgId(String publishingOrgId) {
        this.publishingOrgId = publishingOrgId;
    }
    public void setPublishingOrgName(String publishingOrgName) {
        this.publishingOrgName = publishingOrgName;
    }
    //==================================================================================================================
    //  PRIVATE METHODS
    //==================================================================================================================

    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================
    public String toString() {
        return this.getName()+" v"+this.getVersion();
    }


}/* end TrustmarkDefinitionCache */