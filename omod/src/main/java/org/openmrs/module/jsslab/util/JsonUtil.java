package org.openmrs.module.jsslab.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Location;
import org.openmrs.LocationTag;

public class JsonUtil {

	public static String getLocationListAsJsonTree(List<Location> locations) {
		// TODO fetch all locations at once to avoid n+1 lazy-loads
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Location loc : locations) {
			list.add(toJsonHelper(loc));
		}
		
		// If this gets slow with lots of locations then switch out ObjectMapper for the
		// stream-based version. (But the TODO above is more likely to be a performance hit.)
		StringWriter w = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(w, list);
			return w.toString();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
	private static Map<String, Object> toJsonHelper(Location loc) {
		
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
		
		StringBuilder locationName = new StringBuilder(loc.getName());
		
		if (loc.getTags() != null && loc.getTags().size() > 0) {
			locationName.append(" (");
			for (Iterator<LocationTag> i = loc.getTags().iterator(); i.hasNext();) {
				LocationTag t = i.next();
				locationName.append(t.getName());
				if (i.hasNext())
					locationName.append(", ");
			}
			locationName.append(")");
		}
		
		ret.put("data", locationName.toString());

		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("id", loc.getUuid());
		
		ret.put("attr", attributes);

		if (loc.getChildLocations() != null && loc.getChildLocations().size() > 0) {
			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			for (Location child : loc.getChildLocations())
				children.add(toJsonHelper(child));
			ret.put("children", children);
			
			if (children.size() > 0) {
				ret.put("state", "open");
			}
		}
		return ret;
	}
	
}
