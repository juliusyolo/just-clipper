package com.sdefaa.just.clipper.core.configuration;

/**
 * @author Julius Wong
 * @date 2022/8/31 22:01
 * @since 1.0.0
 */
public class JustClipperConfigProperties {
    private ClipperConfig clipper;

    public ClipperConfig getClipper() {
        return clipper;
    }

    public void setClipper(ClipperConfig clipper) {
        this.clipper = clipper;
    }

    @Override
    public String toString() {
        return "JustClipperConfigProperties{" +
                "clipper=" + clipper +
                '}';
    }

}
