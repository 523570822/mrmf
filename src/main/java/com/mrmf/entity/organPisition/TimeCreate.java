package com.mrmf.entity.organPisition;

import com.osg.entity.DataEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by sq on 2017/4/24.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TimeCreate extends DataEntity {

    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
