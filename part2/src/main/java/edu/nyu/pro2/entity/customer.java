package edu.nyu.pro2.entity;

import java.io.Serializable;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class customer implements Serializable{

    private static final long serialVersionUID = 1L;

    private String cid;

    private String firstName;

    private String lastName;

    private String billingAddress;

    private Integer isDeleted;


}
