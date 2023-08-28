package org.example;

public class TelegramUser {
    private String chatId;
    private String step;
    private String mag;
    private String fullName;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getStep(String writeMsg) {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "TelegramUser{" +
                "chatId='" + chatId + '\'' +
                ", step='" + step + '\'' +
                ", mag='" + mag + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
