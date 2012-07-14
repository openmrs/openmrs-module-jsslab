package org.openmrs.module.webservices.rest.web.resource.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.openmrs.annotation.Handler;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.response.ConversionException;

@Handler(supports = BigDecimal.class, order = 0)
public class BigDecimalConverter implements Converter<BigDecimal> {

	@Override
	public BigDecimal newInstance(String type) {
		return new BigDecimal(0);
	}

	@Override
	public BigDecimal getByUniqueId(String string) {
		DecimalFormat df = new DecimalFormat();
		df.setParseBigDecimal(true);
		try {
			return (BigDecimal) df.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SimpleObject asRepresentation(BigDecimal instance, Representation rep)
			throws ConversionException {
		SimpleObject obj = new SimpleObject();
		obj.add("value", instance.toString());
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

}
