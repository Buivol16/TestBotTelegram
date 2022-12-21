package ua.denis.project;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    private static TelegramBot telegramBot = new TelegramBot.Builder("5908021717:AAG5LoMklYUDYUy7jtQ7cItgYG0HsR9aui0").build();
    private static int msgId = 0;

    public static void main(String[] args) {

        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                long chatId = 0;

                if (update.message() != null) {
                    chatId = update.message().chat().id();

                    if (update.message().text().equals("/start"))
                        sendMessage(0, chatId, "Choose date", Keyboards.getKeyboard(null, update.message().messageId() + 1));
                } else if (update.callbackQuery() != null) {
                    chatId = update.callbackQuery().message().chat().id();
                    var data = update.callbackQuery().data();
                    var date = "";
                    int messageId = 0;
                    var matcher = Pattern.compile(".(\\d+.\\d+.\\d+)withId:(?<msgid>\\d+)").matcher(data);
                    if (matcher.find()) {
                        date = matcher.group(1);
                        messageId = Integer.parseInt(matcher.group(2));
                    }
                    telegramBot.execute(new EditMessageReplyMarkup(chatId, messageId).replyMarkup(Keyboards.getKeyboard(date, messageId)));

                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }

    private static void sendMessage(int addingDays, long chatId, String textMessage) {
        msgId = telegramBot.execute(new SendMessage(chatId, textMessage)).message().messageId();
    }

    private static void sendMessage(int addingDays, long chatId, String textMessage, InlineKeyboardMarkup inlineKeyboardMarkup) {
        telegramBot.execute(new SendMessage(chatId, textMessage).replyMarkup(inlineKeyboardMarkup));
    }
}
