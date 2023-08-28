package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
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
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Assalomu Alekum botka hush kelibsiz\n" +
                           "ILTIMOS ISIM FAMILIYANGIZNI KIRITING");
                    sendMessage.setChatId(chatId);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                   user.setStep(BotConstant.ENTER_NAME);
                } else if (user.getStep().equals(BotConstant.ENTER_NAME))

                try {
                    user.setFullName(text);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("ILTIMOS TINI TANLANG");
                    sendMessage.setChatId(chatId);
                    InlineKeyboardMarkup inlineKeyboardMarkup =new InlineKeyboardMarkup();
                  List<InlineKeyboardButton> td = new ArrayList<>();
                  InlineKeyboardButton inlineKeyboardButtonUZ = new InlineKeyboardButton();
                  inlineKeyboardButtonUZ.setText("UZ");
                  inlineKeyboardButtonUZ.setCallbackData("uzbek tili tanlandi");
                   InlineKeyboardButton inlineKeyboardButtonRU = new InlineKeyboardButton();
                  inlineKeyboardButtonRU.setText("RU");
                  inlineKeyboardButtonRU.setCallbackData("Выбран русский язык");

                   td.add(inlineKeyboardButtonUZ);
                   td.add(inlineKeyboardButtonRU);

                   List<List<InlineKeyboardButton>> tr = new ArrayList<>();
                   tr.add(td);

                  inlineKeyboardMarkup.setKeyboard(tr);
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }


                user.setStep(BotConstant.SELECT_LANG);

                {

                } if(user.getStep(BotConstant.WRITE_MSG).equals(BotConstant.SELECT_LANG)){
                    if (text.equals("1")){
                            setText(chatId,"Xabaringizni qoldiring admin tez orada siz bilan boglanadi");
                    }else if (text.equals("2")){
                        setText(chatId,"Оставьте свое сообщение администратор свяжется с вами в ближайшее время");
                    }
                   user.getStep(BotConstant.WRITE_MSG);
                } else if (user.getStep(BotConstant.WRITE_MSG).equals (BotConstant.WRITE_MSG)){
                   user.setMag(text);

                }
            }

            }
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