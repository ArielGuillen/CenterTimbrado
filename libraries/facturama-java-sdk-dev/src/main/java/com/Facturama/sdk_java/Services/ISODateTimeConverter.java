//preguntar a diego

package com.Facturama.sdk_java.Services;

import java.util.Date;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

public class ISODateTimeConverter extends BaseConverter<Date>
{
    private DateTimeFormatter isoDateTimeFormat = ISODateTimeFormat.basicDateTime();
    public ISODateTimeConverter(String string) {
        super(string);
    }

    public Date convert(String format) {
         return isoDateTimeFormat.parseDateTime(format).toDate();
    }
    
}
