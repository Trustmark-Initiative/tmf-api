package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.Extension;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 12/7/15.
 */
public class ExtensionImpl implements Extension {

    private List<Object> data;

    @Override
    public List<Object> getData() {
        if( data == null )
            data = new ArrayList<Object>();
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }


    public void addData(Object data){
        this.getData().add(data);
    }

}
