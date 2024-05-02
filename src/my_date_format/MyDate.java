package my_date_format;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 The purpose of this class is to simplify client handling of time formats and objects.
 It serves as a wrapper class for the LocalDateTime class, primarily utilizing delegation with the LocalDateTime class.
<p>
 The secondary purpose is to convert the default date format used in the time module to the EUROPE format,
 changing from Month/Day/Year to Day/Month/Year, thus enhancing the usability of dates.
 </p>
 */

public class MyDate implements Comparable<MyDate> {
    public final static String FORMAT = "dd/MM/yyyy HH:mm";


    /**
     This method creates a MyDate instance from a provided string input, which should represent a date or a date and time.
     @notice:
     The string argument should be in the format "day/month/year hour:minute" or just "day/month/year",
     using numeric representation.
     This is the only way for the client to create an instance of this class.
     @param dateTime - String representing the required date
     @return MyDate instance representing the date and time
     */
    public static MyDate of(String dateTime) {
        if (isValid(dateTime))
            return convToMyDate(dateTime);
        else
            throw new IllegalArgumentException(String.format("argument format should be in that format %s", FORMAT));
    }


    /**
     This function checks if the provided String is a valid
     input for creating a new instance of the MyDate object.
     @param dateTime - String: the string being checked
     @return true if the string is valid, otherwise false
     */
    public static boolean isValid(String dateTime) {
        String[] dateAndTime = dateTime.strip().split(" ");
        if (dateAndTime.length == 1)
            return checkValidationToDate(dateAndTime);
        else if (dateAndTime.length == 2)
            return checkValidationToTime(dateAndTime);
        else
            return false;
    }

    private static boolean checkValidationToTime(String[] dateAndTime) {
        String[] hourMinute = dateAndTime[1].strip().split("[:;.-]");
        // check if the hour section is valid
        if (hourMinute.length == 2) {
            for (String s : hourMinute)
                try {
                    Integer.parseInt(s);
                } catch (NumberFormatException o) {
                    return false;
                }
            int hour = Integer.parseInt(hourMinute[0]);
            int minute = Integer.parseInt(hourMinute[1]);
            // if the hour format valid ->
            if ((hour >= 0 && hour < 24) && (minute >= 0 && minute < 60))
                // the hour section is valid now left to check just the date
                return checkValidationToDate(dateAndTime);
        }
        return false;
    }

    private static boolean checkValidationToDate(String[] dateStr) {
        String[] date = dateStr[0].strip().split("[;/.,-]");
        if (date.length == 3) {
            int year, month, day;
            // check if every String element represent a valid number
            try {
                day = Integer.parseInt(date[0]);
                month = Integer.parseInt(date[1]);
                year = Integer.parseInt(date[2]);
            } catch (NumberFormatException o) {
                return false;
            }
            // check if the integer values represent valid numeric integers for real date
            if (year < 100 && year > 0)
                year += 2000;
            try {
                LocalDate.of(year, month,day);
            } catch (DateTimeException o){
                return false;
            }
            return true;
        }
        return false;
    }

    private static MyDate convToMyDate(String dateTime) {
        String[] dateAndTime = dateTime.strip().split(" ");
        if (dateAndTime.length == 1)
            return new MyDate(dateAndTime[0]);
        else
            return new MyDate(dateAndTime[0], dateAndTime[1]);
    }



    // delegation of LocalDateTime Instance
    private final LocalDateTime myDate;



    private MyDate(String dateStr, String timeStr) {
        LocalDate date = convToDate(dateStr);
        LocalTime time = convToTime(timeStr);
        myDate = LocalDateTime.of(date, time);
    }

    private MyDate(String date){
        this(date, "00:00");
    }

    private LocalTime convToTime(String timeStr) {
        String[] hourMinute = timeStr.strip().split("[:;.-]");
        int hour = Integer.parseInt(hourMinute[0]);
        int minute = Integer.parseInt(hourMinute[1]);
        return LocalTime.of(hour, minute);
    }

    private LocalDate convToDate(String dateStr) {
        String[] dayMonthYear = dateStr.strip().split("[;/.,-]");
        int day = Integer.parseInt(dayMonthYear[0]);
        int month = Integer.parseInt(dayMonthYear[1]);
        int year = Integer.parseInt(dayMonthYear[2]);
        // if client write 1/1/24 instead of 1/1/2024
        if (year < 100)
            year += 2000;
        return LocalDate.of(year, month, day);
    }

    public boolean isAfter (MyDate o){
        return this.myDate.isAfter(o.myDate);
    }

    public boolean isBefore (MyDate o){
        return this.myDate.isBefore(o.myDate);
    }

    public boolean equals(MyDate o) {
        return this.myDate.equals(o.myDate);
    }

    public boolean equalsByDate (MyDate o){
        return this.myDate.toLocalDate().equals(o.myDate.toLocalDate());
    }

    @Override
    public int compareTo(MyDate o) {
        return myDate.compareTo(o.myDate);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        return myDate.format(formatter);
    }

}
