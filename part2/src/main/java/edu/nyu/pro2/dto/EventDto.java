package edu.nyu.pro2.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventDto {

    private String eventID;

    private String eventLocation;

    private String eventDeviceModel;

    private String eventDevice;

    private String eventType;

    private String eventValue1;

    private String eventValue2;

    private String eventTime;


}
