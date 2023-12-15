package edu.nyu.pro2.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EnergyPrice {
    private String epid;
    private String zipcode;
    private long price;
    private Timestamp time;
    private Integer isDeleted;
}
