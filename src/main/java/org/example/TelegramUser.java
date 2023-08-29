package org.example;

public class TelegramUser {
    private String chatId;
    private String step;
    private String mag;
    private String fullName;
    private String selectLang;
    private String Car;

    public String getCar() {
        return Car;
    }

    public void setCar(String car) {
        Car = car;
    }

    public String getSelectLang() {
        return selectLang;
    }

    public void setSelectLang(String selectLang) {
        this.selectLang = selectLang;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getStep() {
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
