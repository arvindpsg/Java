package com.grocery.queue.simualtor.util;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author <a href="mailto:asethur@walmartlabs.com">Aravind Sethurathnam</a>
 */
public final class SimulatorUtil {
    
    private static final ThreadLocal<SoftReference<DateFormat>> dateFormat =
            new ThreadLocal<SoftReference<DateFormat>>();
    
    private static DateFormat getDateFormat() {
        SoftReference<DateFormat> ref = dateFormat.get();
        if (ref != null) {
            DateFormat result = ref.get();
            if (result != null) {
                return result;
            }
        }
        DateFormat result = new SimpleDateFormat("dd:MM:yy:HH:mm z");
        result.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        ref = new SoftReference<DateFormat>(result);
        dateFormat.set(ref);

        return result;
    }
    
    public static String formatDate(Date date) throws ParseException {

        return getDateFormat().format(date);

    }

    

}
