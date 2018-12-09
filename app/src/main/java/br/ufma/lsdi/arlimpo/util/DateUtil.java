package br.ufma.lsdi.arlimpo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Renan on 15/11/16.
 */

public class DateUtil {

    public static final String DATA_SEPARADO_POR_TRACO_AMERICANO = "yyyy-MM-dd";
    public static final String DATA_SEPARADO_POR_TRACO = "dd-MM-yyyy";
    public static final String DATA_DIA_MES_SEPARADO_POR_TRACO = "dd-MM";
    public static final String DATA_DIA= "dd";
    public static final String DATA_SEPARADO_POR_BARRA = "dd/MM/yyyy";
    public static final String HORA_MINUTO = "hh:mm";
    public static final String DATA_SEPARADO_POR_BARRA_SEM_HORA = "dd/MM/yyyy";
    public static final String HORA_SEPARADO_POR_DOIS_PONTOS = "hh:mm";
    public static final String DATA_ESCRITA = "dd 'de' MM 'de' yyyy";

    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

    public static Date dateTime(Date date, Date time) {

        Calendar aDate = Calendar.getInstance();
        aDate.setTime(date);

        Calendar aTime = Calendar.getInstance();
        aTime.setTime(time);

        Calendar aDateTime = Calendar.getInstance();
        aDateTime.set(Calendar.DAY_OF_MONTH, aDate.get(Calendar.DAY_OF_MONTH));
        aDateTime.set(Calendar.MONTH, aDate.get(Calendar.MONTH));
        aDateTime.set(Calendar.YEAR, aDate.get(Calendar.YEAR));
        aDateTime.set(Calendar.HOUR, aTime.get(Calendar.HOUR));
        aDateTime.set(Calendar.MINUTE, aTime.get(Calendar.MINUTE));

        return aDateTime.getTime();
    }

    public static String toDate(Date date, String formato) {
        DateFormat df = new SimpleDateFormat(formato);
        return (date == null ? "" : df.format(date));
    }

    public static Date convertTimestampData(String date) {
        String[] dateAux = date.replace(".428Z","")
                .replace("T"," ")
                .replace("-","/").split(" ");
        try {
            return fmt.parse(dateAux[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
