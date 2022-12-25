package com.sdefaa.just.clipper.core.clip.impl;

import com.sdefaa.just.clipper.core.clip.AbstractClipper;
import com.sdefaa.just.clipper.core.configuration.ClipRule;
import com.sdefaa.just.clipper.core.configuration.JustClipperConfigManager;
import com.sdefaa.just.clipper.core.configuration.LogConfig;

import java.util.List;

/**
 * @author Julius Wong
 * @date 2022/8/31 23:24
 * @since 1.0.0
 */
public class LogClipper extends AbstractClipper<String, LogConfig> {

    public final static LogClipper INSTANCE = new LogClipper();

    private LogClipper() {
    }

    @Override
    public String clip(String origin, LogConfig config) {
        List<ClipRule> rules = config.getRules();
        String originMsg = origin;
        if (config.isEnable()) {
            for (ClipRule rule : rules) {
                originMsg = originMsg.replaceAll(rule.getRegex(), rule.getReplacement());
            }
        }
        return originMsg;
    }

    @Override
    public LogConfig getConfig() {
        return JustClipperConfigManager.INSTANCE.getLogConfig();
    }

}
