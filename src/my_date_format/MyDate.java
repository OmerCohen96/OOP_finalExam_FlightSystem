package my_date_format;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 the purpose of this class is to make it easier for client handle with times formats/object
 (wrapper class to LocalDataTime class
 the main pattern is to use delegation with LocalDateTime class)

 the second purpose is to
 convert the default date format used in time module
 to EUROPE format from Month/Day/Year to Day/Month/Year
 and make the use with dates more comfortable

 */

public class MyDate implements Comparable<MyDate> {
    public final static String FORMAT = "dd/MM/yyyy HH:mm";

    /**
     * this method create MyDate instance from providing string input,
     * the input should represent date or date and time.
     *
     * @notice:
     * the string argument should be in this format -> "day/month/year hour:minute" or just "day/month/year",
     * with numeric representation.
     *
     * this is the only way that the client can create an instance of this class.
     * @param dateTime - string that represent the requirement date
     * @return MyDate instance that represent date and time
     */
    public static MyDate of(String dateTime) {
        if (isValid(dateTime))
            return convToMyDate(dateTime);
        else
            throw new IllegalArgumentException(String.format("argument format should be in that format %s", FORMAT));
    }

    /**
     * this function check if the provided String is a valid input for
     * creating new instance of MyDate object.
     * @param dateTime - String : the checked string
     * @return true or false
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

//    private static boolean checkValidationToDate2(String[] dateStr) {
//        String[] date = dateStr[0].strip().split("[;/.,-]");
//        if (date.length == 3) {
//            // check if every String element represent a valid number
//            for (String num : date)
//                try {
//                    Integer.parseInt(num);
//                } catch (NumberFormatException o) {
//                    return false;
//                }
//            // check if the year section represent valid year
//            int year = Integer.parseInt(date[2]);
//            if ((year < 100 && year > 0) || (year > 2000 && year < 2100))
//                return true;
//        }
//        return false;
//    }

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

    public static void main(String[] args) {
        MyDate date1 = MyDate.of("02/03/24");
        MyDate date2 = MyDate.of("5/03/2024 8:18");
        System.out.println(MyDate.isValid("31/02/53 5:41"));

        System.out.println(date2.isAfter(date1));


    }
}
