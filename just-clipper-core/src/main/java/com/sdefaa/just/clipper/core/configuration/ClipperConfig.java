package com.sdefaa.just.clipper.core.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Julius Wong
 * @date 2022/8/31 22:09
 * @since 1.0.0
 */
public class ClipperConfig {
    private boolean debug;
    @JsonProperty("log-config")
    private LogConfig logConfig;
    @JsonProperty("view-config")
    private ViewConfig viewConfig;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public LogConfig getLogConfig() {
        return logConfig;
    }

    public void setLogConfig(LogConfig logConfig) {
        this.logConfig = logConfig;
    }

    public ViewConfig getViewConfig() {
        return viewConfig;
    }

    public void setViewConfig(ViewConfig viewConfig) {
        this.viewConfig = viewConfig;
    }

    @Override
    public String toString() {
        return "ClipperConfig{" +
                "debug=" + debug +
                ", logConfig=" + logConfig +
                ", viewConfig=" + viewConfig +
                '}';
    }
}
