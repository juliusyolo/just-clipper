package com.sdefaa.just.clipper.core.clip.impl;

import com.sdefaa.just.clipper.core.clip.AbstractClipper;
import com.sdefaa.just.clipper.core.configuration.ClipRule;
import com.sdefaa.just.clipper.core.configuration.JustClipperConfigManager;
import com.sdefaa.just.clipper.core.configuration.ViewConfig;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Julius Wong
 * @date 2022/8/31 23:24
 * @since 1.0.0
 */
public class ViewClipper extends AbstractClipper<Object, ViewConfig> {

    public final static ViewClipper INSTANCE = new ViewClipper();

    private ViewClipper() {
    }

    @Override
    public Object clip(Object origin, ViewConfig config) {
        if (origin instanceof CharSequence || origin instanceof Number || origin instanceof Character) {
            return origin;
        }
        try {
            return this.generate(origin, config);
        } catch (Exception e) {
            return origin;
        }
    }

    @Override
    public ViewConfig getConfig() {
        return JustClipperConfigManager.INSTANCE.getViewConfig();
    }


    @SuppressWarnings("unchecked")
    public Object generate(Object origin, ViewConfig clipperConfig) {
        if (origin == null) {
            return null;
        }
        if (origin instanceof Number || origin instanceof Character || origin instanceof Boolean) {
            return origin;
        }
        if (origin instanceof List) {
            List<Object> objects = (List) origin;
            for (Object object : objects) {
                generate(object, clipperConfig);
            }
        } else if (origin instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) origin;
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                Object value = map.get(key);
                if (value instanceof CharSequence) {
                    map.put(key, applyRules((String) value, key, clipperConfig));
                } else {
                    this.generate(value, clipperConfig);
                }
            }
        } else {
            Field[] declaredFields = origin.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                try {
                    String name = declaredField.getName();
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                    }
                    Object value = declaredField.get(origin);
                    if (value instanceof CharSequence) {
                        String modifiedValue = applyRules((String) value, name, clipperConfig);
                        declaredField.set(origin, modifiedValue);
                    } else {
                        this.generate(value, clipperConfig);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return origin;
    }


    private String applyRules(String msg, String fieldName, ViewConfig clipperConfig) {
        String originMsg = msg;
        String keyword;
        if (clipperConfig.isEnable()) {
            for (ClipRule rule : clipperConfig.getRules()) {
                keyword = rule.getKeyword();
                if (keyword.equals(fieldName)) {
                    originMsg = originMsg.replaceAll(rule.getRegex(), rule.getReplacement());
                }
            }
        }
        return originMsg;
    }
}

