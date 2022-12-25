package com.sdefaa.just.clipper.core.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Julius Wong
 * @date 2022/8/31 22:22
 * @since 1.0.0
 */
public class ViewConfig extends AbstractConfig{
    private boolean enable;
    private List<ClipRule> rules;

    @JsonProperty("scan-packages")
    private List<String> scanPackages;

    private List<String> urls;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<ClipRule> getRules() {
        return rules;
    }

    public void setRules(List<ClipRule> rules) {
        this.rules = rules;
    }

    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "ViewConfig{" +
                "enable=" + enable +
                ", rules=" + rules +
                ", scanPackages=" + scanPackages +
                ", urls=" + urls +
                '}';
    }
}
