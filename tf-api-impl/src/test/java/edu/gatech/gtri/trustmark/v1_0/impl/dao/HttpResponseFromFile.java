package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 9/13/16
 */
public class HttpResponseFromFile implements HttpResponse {

    private File file;
    private String contentType;
    private Boolean binary;

    // FIXME incorporate this as necessary...
    private Map<String, List<String>> headers;

    public HttpResponseFromFile(File _file, String _contentType, Boolean _binary){
        this.file = _file;
        this.contentType = _contentType;
        this.binary = _binary;
    }


    @Override
    public List<String> getHeader(String name) {
        return new ArrayList<>();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return new HashMap<>();
    }

    @Override
    public int getResponseCode() {
        return 200;
    }

    @Override
    public String getResponseMessage() {
        return "OK";
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isBinary() {
        return this.binary;
    }

    @Override
    public String getContent() {
        if( !isBinary() ) {
            try {
                return FileUtils.readFileToString(this.file);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }else{
            throw new UnsupportedOperationException("Cannot get string from binary content");
        }
    }

    @Override
    public byte[] getBinaryContent() {
        if( isBinary() ){
            try{
                return FileUtils.readFileToByteArray(this.file);
            }catch(Throwable t){
                throw new RuntimeException(t);
            }
        }else{
            throw new UnsupportedOperationException("Cannot get binary from string content");
        }
    }


}/* end HttpResponseFromFile */