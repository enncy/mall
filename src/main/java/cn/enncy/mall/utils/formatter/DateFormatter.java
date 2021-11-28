package cn.enncy.mall.utils.formatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * //TODO
 * <br/>Created in 21:41 2021/11/23
 *
 * @author enncy
 */
public class DateFormatter implements Formatter{

    @Override
    public String format(Object value) {
        return DateFormatter.format((Long) value, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(long time,String pattern){
        Date date = new Date();
        date.setTime(time);
        LocalDateTime  localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }
}
