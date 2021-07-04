package com.example.poem.DB;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexManager {
    private String text;
    private String patternStr;
    private Pattern pattern;
    private Matcher matcher;
    private boolean isFind;

    public RegexManager(String text, String patternStr) {
        this.text = text;
        this.patternStr = patternStr;
        this.pattern = Pattern.compile(this.patternStr);
        this.matcher = this.pattern.matcher(this.text);
        this.isFind = this.matcher.find();
    }

    public boolean isFind() {
        return this.isFind;
    }

    public String replaceAll(String replaceStr) {
        String replaceText = this.text;
        if (isFind()) {
            replaceText = this.matcher.replaceAll(replaceStr);
        }
        return replaceText;
    }

}
