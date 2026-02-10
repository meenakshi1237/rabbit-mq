package com.demo.mq.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Event {
    private String id;
    private String eventName;
    private String startDate;
    private String endDate;
}
