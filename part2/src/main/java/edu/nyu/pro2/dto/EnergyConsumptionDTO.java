package edu.nyu.pro2.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

// this DTO is used for the display of user energy consumption in the past
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EnergyConsumptionDTO {
    //private String cid;
    private String totalConsumption;
    private String did;
    private String slid;
    private String address;
    private String squarefootage;
}
