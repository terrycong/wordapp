package com.terrycong.wordapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class BuildInfoAdvice {

    @Value("${wordapp.version:dev}")
    private String version;

    @Value("${wordapp.build.date:unknown}")
    private String buildDate;

    @ModelAttribute("appVersion")
    public String appVersion() { return version; }

    @ModelAttribute("buildDate")
    public String buildDate() { return buildDate; }
}
