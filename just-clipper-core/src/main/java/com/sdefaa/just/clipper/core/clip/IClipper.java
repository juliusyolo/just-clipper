package com.sdefaa.just.clipper.core.clip;

/**
 * @author Julius Wong
 * @date 2022/8/31 22:34
 * @since 1.0.0
 */
public interface IClipper<T> {
    T clip(T origin);
}
