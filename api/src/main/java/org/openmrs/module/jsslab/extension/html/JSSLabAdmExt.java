package org.openmrs.module.jsslab.extension.html;

import java.util.Map;
import java.util.LinkedHashMap;

import org.openmrs.module.web.extension.AdministrationSectionExt;

public class JSSLabAdmExt extends AdministrationSectionExt {

	@Override
	public Map<String, String> getLinks() {
		Map<String, String> map = new LinkedHashMap<String, String>(); 
		map.put("module/jsslab/admin/settings.form", "jsslab.settings.link");
		map.put("module/jsslab/admin/catalog.form", "jsslab.catalog.link");
		map.put("module/jsslab/admin/templates.form", "jsslab.templates.link");
		map.put("module/jsslab/admin/instruments.form", "jsslab.instruments.link");
		map.put("module/jsslab/admin/reports.form", "jsslab.reports.link");
		return map;   
	}

	@Override
	public String getTitle() {
		return "jsslab.title";
	}

}
