package org.openmrs.module.webservices.rest.web.resource.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.util.OpenmrsConstants;

@Handler(supports = BigDecimal.class, order = 0)
public class BigDecimalConverter implements Converter<BigDecimal> {

	@Override
	public BigDecimal newInstance(String type) {
		return new BigDecimal(0);
	}
	
	@Override
	public BigDecimal getByUniqueId(String string) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getDefaultLocale());
		
		BigDecimal bd = null;
		try {
			Number n = currencyFormatter.parse(string);
			bd = new BigDecimal(n.toString());
		} catch (ParseException e) {
			try {
				NumberFormat formatter = NumberFormat.getInstance(getDefaultLocale());
				Number n = formatter.parse(string);
				bd = new BigDecimal(n.toString());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}

		return bd;
	}

	@Override
	public SimpleObject asRepresentation(BigDecimal instance, Representation rep)
			throws ConversionException {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(getDefaultLocale());

		BigDecimal displayVal = instance.setScale(2, RoundingMode.HALF_UP);

		SimpleObject obj = new SimpleObject();
		obj.add("value", currencyFormatter.format(displayVal.doubleValue()));
		return obj;
	}

	@Override
	public Object getProperty(BigDecimal instance, String propertyName)
			throws ConversionException {
		return null;
	}

	@Override
	public void setProperty(Object instance, String propertyName, Object value)
			throws ConversionException {
	}

	private Locale getDefaultLocale() {
		String[] localeData = Context.getAdministrationService().getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCALE).split("_");
		if (localeData.length >= 1) {
			Locale locale = new Locale(localeData[0], localeData[1]);
			return locale;
		} else {
			return Locale.UK;
		}
	}
}
