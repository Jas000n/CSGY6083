package edu.nyu.pro2.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ServiceLocation {
    private static final long serialVersionUID = 1L;

    private String slid;

    private String state;

    private String city;

    private String zipcode;

    private String address;

    private String squareFootage;

    private String numberOfBedrooms;

    private String numberOfOccupants;

    private Date startDate;

    private Integer isDeleted;
}
