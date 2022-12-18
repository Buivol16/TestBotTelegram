package ua.denis.project;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;


public class Main {
    public static void main(String[] args){
        TelegramBot telegramBot = new TelegramBot.Builder("5908021717:AAG5LoMklYUDYUy7jtQ7cItgYG0HsR9aui0").build();
        telegramBot.setUpdatesListener(updates -> {
            System.err.println("__________________Debug__________________");
            updates.forEach(System.out::println);
            updates.forEach(update -> {

                long myId = 653804698;
                long victimId = 0;
                if (update.message() != null) {
                    if (update.message().text() != null && update.message().text().equals("/start")) {
                        victimId = update.message().chat().id();
                        telegramBot.execute(new SendMessage(victimId, "Испытать свою удачу?").replyMarkup(new InlineKeyboardMarkup().addRow(new InlineKeyboardButton("Да").callbackData("yes"), new InlineKeyboardButton("Нет").callbackData("no"))));
                    }
                } else if (update.callbackQuery() != null) {
                    var callBack = update.callbackQuery().data();
                    if (update.callbackQuery().data().equals("yes")) {
                        victimId = update.callbackQuery().message().chat().id();
                        telegramBot.execute(new SendMessage(victimId, "Кручу-верчу, запутать хочу."));
                        telegramBot.execute(new SendMessage(myId, "Настало время пошалить) Для пользователя " + update.callbackQuery().from().username()).replyMarkup(new InlineKeyboardMarkup().addRow(new InlineKeyboardButton("Обеспечить выигрыш").callbackData("giveWinTo " + victimId), new InlineKeyboardButton("Поднасрать)").callbackData("giveLoseTo " + victimId))));
                    } else if (update.callbackQuery().data().equals("no")) {
                        victimId = update.callbackQuery().message().chat().id();
                        telegramBot.execute(new SendMessage(victimId, "Значит, в следующий раз(((("));
                    }
                    if (update.callbackQuery().data().startsWith("giveWinTo ")){
                        telegramBot.execute(new SendMessage(getVictimId(callBack), "Ты выиграл миллион долларов"));
                    }
                    else if (update.callbackQuery().data().startsWith("giveLoseTo ")){
                        telegramBot.execute(new SendMessage(getVictimId(callBack), "Как обидно, тебе не повезло"));
                    }
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
    private static long getVictimId(String callBackData){
        var string = "";
        if (callBackData.startsWith("giveWinTo ")) string = callBackData.replaceAll("giveWinTo ", "");
        else if (callBackData.startsWith("giveLoseTo ")) string = callBackData.replaceAll("giveLoseTo ", "");
        return Long.parseLong(string);
    }
}
