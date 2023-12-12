package edu.nyu.pro2.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceDto {
    private static final long serialVersionUID = 1L;

    private String did;

    private String dtid;

    private String slid;

    private String status;

    private String value1;

    private String value2;

    private Integer isDeleted;
}