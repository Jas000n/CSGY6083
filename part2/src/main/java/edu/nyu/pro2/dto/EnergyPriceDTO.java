package edu.nyu.pro2.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

// this DTO is used for the display of lat 7 day energy price
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EnergyPriceDTO {
    //private String cid;
    private String price;
    private String time;
    private String TimeSlot;
}