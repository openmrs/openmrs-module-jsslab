package org.openmrs.module.jsslab.extension.html;

import java.util.Map;
import java.util.LinkedHashMap;

import org.openmrs.module.Extension; 
import org.openmrs.module.web.extension.AdministrationSectionExt; 

public class JSSLabAdmExt extends AdministrationSectionExt {

	@Override
	public Map<String, String> getLinks() {
		Map<String, String> map = new LinkedHashMap<String, String>(); 
		map.put("jsslabForm.jsp", "jsslab.settings.link");   
		return map;   
	}

	@Override
	public String getTitle() {
		return "jsslab.title";
	}

}
