package tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {

	private String pattern = null;
	
	
	public StringToDateConverter(String pattern) {
		super();
		this.pattern = pattern;
	}


	@Override
	public Date convert(String from) {
		// TODO Auto-generated method stub
		Date date = null;
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			date= df.parse(from);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

}
