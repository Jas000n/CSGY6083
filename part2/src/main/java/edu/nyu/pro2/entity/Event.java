package edu.nyu.pro2.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Event {
    private String eid;

    private String did;

    private String label;

    private String value1;

    private String value2;

    private String timestamp;

    private Integer isDeleted;

    private Integer versionID;
}
