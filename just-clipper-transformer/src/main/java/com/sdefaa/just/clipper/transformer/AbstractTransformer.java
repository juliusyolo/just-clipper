package com.sdefaa.just.clipper.transformer;

import com.sdefaa.just.clipper.core.configuration.JustClipperConfigManager;

/**
 * @author Julius Wong
 * @date 2022/8/31 23:47
 * @since 1.0.0
 */
public abstract class AbstractTransformer implements ITransformer {

    protected String getFullyQualifiedClassName(String className) {
        return className.replace("/", ".");
    }

    protected boolean isDebug() {
        return JustClipperConfigManager.INSTANCE.isDebug();
    }

}
