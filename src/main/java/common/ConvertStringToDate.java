package common;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class ConvertStringToDate {
    public static Date convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            if(localDate.getYear() > 1900 && localDate.format(formatter).equals(dateString) && localDate.isBefore(LocalDate.now())) {
                return Date.valueOf(localDate);
            }  else {
                throw new DateTimeParseException("Invalid date conditions: ", dateString, 0);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + dateString);
            System.out.println("Date format: yyyy-mm-dd and year of birth after 1900");
            System.out.print("Please re-enter date: ");
            return null;
        }
    }
}
