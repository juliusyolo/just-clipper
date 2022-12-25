package com.sdefaa.just.clipper.core.clip;

import com.sdefaa.just.clipper.core.configuration.AbstractConfig;

import java.util.Objects;

/**
 * @author Julius Wong
 * @date 2022/8/31 23:14
 * @since 1.0.0
 */
public abstract class AbstractClipper<T, E extends AbstractConfig> implements IClipper<T> {

    public abstract T clip(T origin, E config);

    public abstract E getConfig();

    public T clip(T origin) {
        E config = this.getConfig();
        if (Objects.isNull(config)) {
            throw new NullPointerException("Clipper config is null!");
        }
        return this.clip(origin, config);
    }

}
