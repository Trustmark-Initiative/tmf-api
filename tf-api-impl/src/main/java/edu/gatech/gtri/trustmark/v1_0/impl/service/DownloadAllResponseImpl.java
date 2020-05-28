package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.impl.util.DateUtils;
import edu.gatech.gtri.trustmark.v1_0.service.DownloadAllResponse;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * Created by brad on 4/12/17.
 */
public class DownloadAllResponseImpl implements DownloadAllResponse {

    public DownloadAllResponseImpl(){}
    public DownloadAllResponseImpl(JSONObject json, File file){
        if( json == null )
            throw new IllegalArgumentException("Invalid empty JSON object given to download all response!");

        this.zipFile = file;
        try {
            this.downloadUrl = new URL(json.optString("url"));
        }catch(Throwable t){
            throw new UnsupportedOperationException("Invalid URL given from server: "+json.optString("url"), t);
        }
        this.versionSetName = json.optJSONObject("versionSet").optString("name");
        this.size = json.optLong("size");
        this.humanReadableSize = json.optString("humanSize");

        String dateTimeString = json.optString("createDate");
        this.createDate = DateUtils.parseDate(dateTimeString);
    }

    private File zipFile;
    private URL downloadUrl;
    private String versionSetName;
    private Long size;
    private String humanReadableSize;
    private Date createDate;


    @Override
    public File getZipFile() {
        return zipFile;
    }

    public void setZipFile(File zipFile) {
        this.zipFile = zipFile;
    }

    @Override
    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(URL downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String getVersionSetName() {
        return versionSetName;
    }

    public void setVersionSetName(String versionSetName) {
        this.versionSetName = versionSetName;
    }

    @Override
    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String getHumanReadableSize() {
        return humanReadableSize;
    }

    public void setHumanReadableSize(String humanReadableSize) {
        this.humanReadableSize = humanReadableSize;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Override
    public String toString() {
        return "DownloadAllResponse[vs="+this.getVersionSetName()+"]";
    }
}
