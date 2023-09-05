package org.example;

import lombok.NonNull;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyBot extends TelegramLongPollingBot {
    List<TelegramUser> users = new ArrayList<>();


    public MyBot() {
        super("6494265943:AAHiPDr_vuIAGh6ZNyD0zrFU_4_cizYAsdY");
    }
    TelegramUser user = new TelegramUser();
    @Override
    public void onUpdateReceived(Update update) {




        if (update.hasMessage()) {
            String chatId = update.getMessage().getChatId().toString();
            TelegramUser user =saveUser(chatId);
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
                } else if (user.getStep().equals(BotConstant.WRITE_MSG)) {
                    user.setMag(text);
                    setText(chatId, user.getSelectLang().equals(BotQuery.UZ_SELECT) ?
                            "Admin Tez orada siz bilan boglanadi. " :
                            "Админ скоро свяжется с вами. "
                    );

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
            if (user.getStep().equals(BotConstant.SELECT_LANG)) {

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
            }

        }
        else if (update.hasCallbackQuery()) {
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            if (update.getCallbackQuery().getData().equals(BotQuery.BMW_SELECT)) {
                TelegramUser user = saveUser(Objects.requireNonNull(chatId));
                user.setCarType(BotConstant.BMW);
                user.setStep(BotConstant.BMW);

                try {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(chatId);
                    sendPhoto.setPhoto(new InputFile(new File("BMW-M5-CS-G-Power-tuning-5-scaled.jpg")));
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (update.getCallbackQuery().getData().equals(BotQuery.MERCEDES_SELECT)) {
                TelegramUser user = saveUser(chatId);
                user.setCarType(BotConstant.MERCEDES);
                user.setStep(BotConstant.MERCEDES);

                try {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(chatId);
                    sendPhoto.setPhoto(new InputFile(new File("7K1A5572-scaled.jpg")));
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
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