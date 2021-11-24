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
        Date date = new Date();
        date.setTime((Long) value);
        LocalDateTime  localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
