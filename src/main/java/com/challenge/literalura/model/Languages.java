package com.challenge.literalura.model;

public enum Languages {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    ITALIAN("it"),
    PORTUGUESE("pt");

    private String languageCode;

    Languages(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public static Languages fromString(String text) {
        for (Languages language : Languages.values()) {
            if (language.getLanguageCode().equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("No language found for code: " + text);
    }
}
