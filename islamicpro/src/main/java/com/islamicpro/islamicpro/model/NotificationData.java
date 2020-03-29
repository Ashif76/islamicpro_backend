package com.islamicpro.islamicpro.model;

import com.google.gson.annotations.SerializedName;

public class NotificationData {
    private String title;
    private String arabicText;
    private String pronounciation;
    private String englishExplanation;
    private String source;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getArabicText() {
        return arabicText;
    }

    public void setArabicText(final String arabicText) {
        this.arabicText = arabicText;
    }

    public String getPronounciation() {
        return pronounciation;
    }

    public void setPronounciation(final String pronounciation) {
        this.pronounciation = pronounciation;
    }

    public String getEnglishExplanation() {
        return englishExplanation;
    }

    public void setEnglishExplanation(final String englishExplanation) {
        this.englishExplanation = englishExplanation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }
}

