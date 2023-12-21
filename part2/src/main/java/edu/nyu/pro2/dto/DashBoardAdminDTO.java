package edu.nyu.pro2.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

// this DTO is used for the display every customer consumption
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DashBoardAdminDTO {
    private String cid;
    private String totalConsumption;
}
