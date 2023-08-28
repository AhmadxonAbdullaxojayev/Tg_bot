package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    List<TelegramUser> users = new ArrayList<>();


    public  MyBot(){
        super("6380066071:AAFfg8yZJVEkREVurBcqivZrdvGNL321j08");
    }

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage()) {
            String chatId= update.getMessage().getChatId().toString();
            Message message = update.getMessage();
            TelegramUser user =saveUser(chatId);
            if (message.hasText()) {
                String text = message.getText();
               if (text.equals("/list")){
                   System.out.println(users);
               }
                if (text.equals("/start")) {
                    if (user.getFullName()!= null){
                        try {
                            setLang(chatId,user);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Assalomu Alekum botka hush kelibsiz\n" +
                                "ILTIMOS ISIM FAMILIYANGIZNI KIRITING\n" +
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

                } else if (user.getStep().equals(BotConstant.ENTER_NAME)){

                try {
                    user.setFullName(text);
                        setLang(chatId,user);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

                {

                }
                    user.setStep(BotConstant.SELECT_LANG);
                } else if (user.getStep().equals (BotConstant.WRITE_MSG)){
                   user.setMag(text);
                   setText(chatId,user.getSelectLang().equals(BotQuery.UZ_SELECT) ?
                           "Admin Tez orada siz bilan boglanadi. " :
                                   "Админ скоро свяжется с вами. "
                   );

                }
            }

            }


        else if (update.hasCallbackQuery()){
            String chatId = String.valueOf(Long.valueOf(update.getCallbackQuery().getFrom().getId().toString()));
            String data = update.getCallbackQuery().getData();
            TelegramUser user = saveUser(chatId);
            if (user.getStep().equals(BotConstant.SELECT_LANG)){
                user.setSelectLang(BotQuery.UZ_SELECT);
                if (data.equals(BotQuery.UZ_SELECT)){
                    setText(chatId,"Habaringizni qoldiring ");
                } else if (data.equals(BotQuery.RU_SELECT)) {
                    user.setSelectLang(BotQuery.RU_SELECT);
                    setText(chatId,"Оставьте свое сообщение ");
                }
                user.setStep(BotConstant.WRITE_MSG);
            }

        }
        }
        private void setLang(String chatId,TelegramUser user ) throws TelegramApiException {

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(user.getFullName()+" ILTIMOS TINI TANLANG\n" +
                    "ПОЖАЛУЙСТА, ВЫБЕРИТЕ TI");
            sendMessage.setChatId(chatId);
            InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
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

        }
        private TelegramUser saveUser(String chatId){
            for(TelegramUser user : users){
                if (user.getChatId().equals(chatId)){
                    return user;
                }
            }
            TelegramUser user = new TelegramUser();
            user.setChatId(chatId);
            users.add(user);
            return user;
        }

    @Override
    public String getBotUsername() {
        return "https://t.me/Vluta_bot";
    }
    private void setText(String chatId,String text){
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