package com.sdefaa.just.clipper.core.configuration;

/**
 * @author Julius Wong
 * @date 2022/8/31 22:15
 * @since 1.0.0
 */
public class ClipRule {
    private String keyword;
    private String regex;
    private String replacement;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    @Override
    public String toString() {
        return "ClipRule{" +
                "keyword='" + keyword + '\'' +
                ", regex='" + regex + '\'' +
                ", replacement='" + replacement + '\'' +
                '}';
    }
}
