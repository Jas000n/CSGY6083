package edu.nyu.pro2.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceType {
    private static final long serialVersionUID = 1L;

    private String dtid;

    private String name;

    private String model;

    private Integer isDeleted;
}
