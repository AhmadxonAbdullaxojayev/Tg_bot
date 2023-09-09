package org.example;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.awt.SystemColor.text;

public class MyBot extends TelegramLongPollingBot {
    List<TelegramUser> users = new ArrayList<>();


    public MyBot() {
        super("6494265943:AAHiPDr_vuIAGh6ZNyD0zrFU_4_cizYAsdY");
    }

    TelegramUser user = new TelegramUser();

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage()) {
            String chatId = update.getMessage().getChatId().toString();
            TelegramUser user = saveUser(chatId);
            Message message = update.getMessage();
            System.out.println("user step => " + user.getStep());
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/list")) {
                    System.out.println(users);
                } else if (text.equals("/start")) {
                    if (user.getFullName() != null) {
                        try {
                            setLang(chatId, user);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Assalomu Alekum botka hush kelibsiz\n" +
                                "ILTIMOS ISIM FAMILIYANGIZNI KIRITING\n\n" +
                                "Привет, Алекум Ботка, добро пожаловать\n" +
                                "ПОЖАЛУЙСТА, ВВЕДИТЕ СВОЕ ИМЯ И ФАМИЛИЮ");


                        sendMessage.setChatId(chatId);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        user.setStep(BotConstant.ENTER_NAME);
                    }

                } else if (user.getStep().equals(BotConstant.ENTER_NAME)) {

                    try {
                        user.setFullName(text);
                        setLang(chatId, user);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                    user.setStep(BotConstant.SELECT_LANG);
                }
            }


//            }  else if (update.hasCallbackQuery()){
//            String chatId = String.valueOf(Long.valueOf(update.getCallbackQuery().getFrom().getId().toString()));
//            String data = update.getCallbackQuery().getData();
//            TelegramUser user = saveUser(chatId);
//            if (user.getStep().equals(BotConstant.SELECT_LANG)){
//                user.setSelectLang(BotQuery.UZ_SELECT);
//                if (data.equals(BotQuery.UZ_SELECT)){
//                    setText(chatId,"Habaringizni qoldiring ");
//                } else if (data.equals(BotQuery.RU_SELECT)) {
//                    user.setSelectLang(BotQuery.RU_SELECT);
//                    setText(chatId,"Оставьте свое сообщение ");
//                }
//                user.setStep(BotConstant.WRITE_MSG);
//            }


        } else if (update.hasCallbackQuery()) {
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            TelegramUser user = getMe(chatId);

            System.out.println(user.getStep());
            if (update.getCallbackQuery().getData().contains("LANG")) {

                try {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Iltimos mashina turini tanlang.\n\n" +
                            "Пожалуйста, выберите тип машины.");
                    sendMessage.setChatId(chatId);

                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

                    List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
                    InlineKeyboardButton inlineKeyboardButtonUZ = new InlineKeyboardButton();

                    inlineKeyboardButtonUZ.setText("BMW \uD83D\uDCB8");
                    inlineKeyboardButtonUZ.setCallbackData(BotQuery.BMW_SELECT);


                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();

                    inlineKeyboardButton.setText("MERSADES \uD83D\uDCB8");
                    inlineKeyboardButton.setCallbackData(BotQuery.MERCEDES_SELECT);

                    inlineKeyboardButtons.add(inlineKeyboardButtonUZ);
                    inlineKeyboardButtons.add(inlineKeyboardButton);

                    List<List<InlineKeyboardButton>> tr = new ArrayList<>();
                    tr.add(inlineKeyboardButtons);
                    inlineKeyboardMarkup.setKeyboard(tr);

                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);

                    execute(sendMessage);


                    user.setStep(BotConstant.SELECT_CAR_TYPE);

                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } if (update.getCallbackQuery().getData().equals(BotQuery.BMW_SELECT)) {
                user = saveUser(Objects.requireNonNull(chatId));
                user.setCarType(BotConstant.BMW);
                user.setStep(BotConstant.BMW);

                try {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(chatId);
                    sendPhoto.setPhoto(new InputFile(new File("src/main/java/images/img.png")));

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("BMW I8\n" +
                            "Mator: Gibrit\n" +
                            "0-100 4.4 sekund\n" +
                            "BezinBak: 70\n" +
                            "OtKuchi: 500 ta\n" +
                            "Narxi: 250 000$");

                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    List<InlineKeyboardButton> rd = new ArrayList<>();
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText("BUY");
                    inlineKeyboardButton.setCallbackData(BotQuery.BMW_SELECT);
                    rd.add(inlineKeyboardButton);
                    List<List<InlineKeyboardButton>> te = new ArrayList<>();
                    te.add(rd);
                    inlineKeyboardMarkup.setKeyboard(te);
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);

                    execute(sendPhoto);
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println("Error sending photo: " + e.getMessage());
                    e.printStackTrace();
                }
            } else if (update.getCallbackQuery().getData().equals(BotQuery.MERCEDES_SELECT)) {
                user = saveUser(chatId);
                user.setCarType(BotConstant.MERCEDES);
                user.setStep(BotConstant.MERCEDES);

                try {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(chatId);
                    sendPhoto.setPhoto(new InputFile(new File("src/main/java/images/7K1A5572-scaled.jpg")));

                    SendMessage message = new SendMessage();
                    message.setChatId(chatId);
                    message.setText("MERCEDES BENZ G63.\n" +
                            "Yili:2023\n" +
                            "Mator:5.5 turbo\n" +
                            "0-100,3 sekud\n" +
                            "BezinBak: 80l\n" +
                            "OtKuchi: 321\n" +
                            "Narxi:350 000$");

                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    List<InlineKeyboardButton> bt = new ArrayList<>();
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText("BUY");
                    inlineKeyboardButton.setCallbackData(BotQuery.MERCEDES_SELECT);
                    bt.add(inlineKeyboardButton);
                    List<List<InlineKeyboardButton>> in = new ArrayList<>();
                    in.add(bt);
                    inlineKeyboardMarkup.setKeyboard(in);
                    message.setReplyMarkup(inlineKeyboardMarkup);

                    execute(sendPhoto);
                    execute(message);

                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            if (update.getCallbackQuery().getData().equals(BotQuery.BMW_SELECT) &&
                    update.getCallbackQuery().getMessage().getText().equals("BUY")) {
                try {
                    SendMessage adminNotification = new SendMessage();
                    adminNotification.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    adminNotification.setText("User " + update.getCallbackQuery().getMessage().getChatId().toString()
                            + " BMW sotib olindi.");

                    execute(adminNotification);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (update.getCallbackQuery().getData().equals(BotQuery.MERCEDES_SELECT) &&
                    update.getCallbackQuery().getMessage().getText().equals("BUY")) {
                try {
                    SendMessage adminNotification = new SendMessage();
                    adminNotification.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                    adminNotification.setText("User " + update.getCallbackQuery().getMessage().getChatId().toString()
                            + " Mercedes sotib olindi.");

                    execute(adminNotification);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private TelegramUser getMe(String chatId) {
        for (TelegramUser user : users) {
            if (user.getChatId().equals(chatId)) {
                return user;
            }
        }
        TelegramUser newUser = new TelegramUser();
        newUser.setChatId(chatId);
        users.add(newUser);
        return newUser;

    }


    private void setLang(String chatId, TelegramUser user) throws TelegramApiException {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(user.getFullName() + " ILTIMOS TINI TANLANG\n" +
                "ПОЖАЛУЙСТА, ВЫБЕРИТЕ TI");
        sendMessage.setChatId(chatId);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> td = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButtonUZ = new InlineKeyboardButton();
        inlineKeyboardButtonUZ.setText("UZ");
        inlineKeyboardButtonUZ.setCallbackData(BotQuery.UZ_SELECT);

        InlineKeyboardButton inlineKeyboardButtonRU = new InlineKeyboardButton();

        inlineKeyboardButtonRU.setText("RU");
        inlineKeyboardButtonRU.setCallbackData(BotQuery.RU_SELECT);

        td.add(inlineKeyboardButtonUZ);
        td.add(inlineKeyboardButtonRU);

        List<List<InlineKeyboardButton>> tr = new ArrayList<>();
        tr.add(td);

        inlineKeyboardMarkup.setKeyboard(tr);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        execute(sendMessage);
        user.setStep(BotConstant.SELECT_LANG);
    }

    private TelegramUser saveUser(String chatId) {
        for (TelegramUser user : users) {
            if (user.getChatId().equals(chatId)) {
                return user;
            }
        }

        TelegramUser newUser = new TelegramUser();
        newUser.setChatId(chatId);
        users.add(newUser);
        return newUser;
    }

    @Override
    public String getBotUsername() {
        return "https://t.me/Cars_buy_bot";
    }

    private void setText(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }


}