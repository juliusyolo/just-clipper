package com.sdefaa.just.clipper.core.configuration;

import java.util.List;

/**
 * @author Julius Wong
 * @date 2022/8/31 22:13
 * @since 1.0.0
 */
public class LogConfig extends AbstractConfig{
    private boolean enable;
    private List<ClipRule> rules;

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

    @Override
    public String toString() {
        return "LoggerConfig{" +
                "enable=" + enable +
                ", rules=" + rules +
                '}';
    }

}
