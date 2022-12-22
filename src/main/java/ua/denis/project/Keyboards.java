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
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        try {
            keyboard = keyboard.addRow(
                    new InlineKeyboardButton("<").callbackData("<" + getDateFrom(data, -3) + "withId:" + messageId),
                    getButton(data, 0),
                    getButton(data, 1),
                    getButton(data, 2),
                    new InlineKeyboardButton(">").callbackData(">" + getDateFrom(data, 3) + "withId:" + messageId)
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            return keyboard;
        }
    }

    private static String getDateFrom(String date, int addingDays) throws ParseException {
        var data = "";
        Date date1;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (date != null && !date.equals("")) {
            date1 = new SimpleDateFormat("dd.MM.yyyy").parse(date);
            data = date1.toInstant()
                    .plus(addingDays, ChronoUnit.DAYS)
                    .atZone(ZoneId.systemDefault())
                    .format(dtf);
        } else
            data = Calendar.getInstance().toInstant().plus(addingDays, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).format(dtf);
        return data;
    }

    private static InlineKeyboardButton getButton(String data, int addingDays) throws ParseException {
        return new InlineKeyboardButton(getDateFrom(data, addingDays)).callbackData(getDateFrom(data, addingDays));
    }
}