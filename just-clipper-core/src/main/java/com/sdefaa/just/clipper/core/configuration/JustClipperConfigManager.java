package com.sdefaa.just.clipper.core.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Julius Wong
 * @date 2022/8/31 22:33
 * @since 1.0.0
 */
public class JustClipperConfigManager {
    public final static JustClipperConfigManager INSTANCE = new JustClipperConfigManager();
    private JustClipperConfigProperties properties;
    private final YAMLMapper yamlMapper = new YAMLMapper();

    private JustClipperConfigManager() {
    }

    public void loadConfig(String yamlFilePathName) {
        try {
            properties = yamlMapper.readValue(new File(yamlFilePathName), JustClipperConfigProperties.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LogConfig getLogConfig() {
        if (Objects.isNull(properties)) {
            throw new IllegalStateException("JustClipperConfigProperties are not loaded!");
        }
        return properties.getClipper().getLogConfig();
    }

    public boolean isDebug(){
        if (Objects.isNull(properties)) {
            throw new IllegalStateException("JustClipperConfigProperties are not loaded!");
        }
        return properties.getClipper().isDebug();
    }

    public ViewConfig getViewConfig() {
        if (Objects.isNull(properties)) {
            throw new IllegalStateException("JustClipperConfigProperties are not loaded!");
        }
        return properties.getClipper().getViewConfig();
    }

}
