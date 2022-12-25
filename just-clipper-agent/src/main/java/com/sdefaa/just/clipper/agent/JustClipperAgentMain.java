package com.sdefaa.just.clipper.agent;

import com.sdefaa.just.clipper.core.configuration.JustClipperConfigManager;
import com.sdefaa.just.clipper.transformer.JustClipperTransformer;

import java.lang.instrument.Instrumentation;
import java.util.Objects;

/**
 * @author Julius Wong
 * <p>
 *
 * </p>
 * @since 1.0.0
 */
public class JustClipperAgentMain {

    public static void premain(String args, Instrumentation instrumentation) {
        if (Objects.nonNull(args)) {
            JustClipperConfigManager.INSTANCE.loadConfig(args);
        }
        instrumentation.addTransformer(new JustClipperTransformer(), true);
    }

}
