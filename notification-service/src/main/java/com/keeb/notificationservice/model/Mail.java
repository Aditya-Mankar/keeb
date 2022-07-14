package com.keeb.notificationservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class Mail {

    private String mailFrom;
    private String mailTo;
    private String subject;
    private Map<String, Object> props;

}
