package ua.denis.project;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Keyboards {
    public static InlineKeyboardMarkup getKeyboard(String data, long messageId) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup().addRow(
                new InlineKeyboardButton("<").callbackData("<" + getDateFrom(data, -3) + "withId:" + messageId),
                new InlineKeyboardButton(getDateFrom(data, 0)).callbackData(getDateFrom(data, 0)),
                new InlineKeyboardButton(getDateFrom(data, 1)).callbackData(getDateFrom(data, 1)),
                new InlineKeyboardButton(getDateFrom(data, 2)).callbackData(getDateFrom(data, 2)),
                new InlineKeyboardButton(">").callbackData(">" + getDateFrom(data, 3) + "withId:" + messageId)
        );
        return keyboard;
    }
    private static String getDateFrom(String date, int addingDays){
        var data = "";
        Date date1 = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (date != null){
            try {
                date1 = new SimpleDateFormat("dd.MM.yyyy").parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            data = date1.toInstant()
                    .plus(addingDays, ChronoUnit.DAYS)
                    .atZone(ZoneId.systemDefault())
                    .format(dtf);
        }
        else data = Calendar.getInstance().toInstant().plus(addingDays, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).format(dtf);
        return data;
    }
}