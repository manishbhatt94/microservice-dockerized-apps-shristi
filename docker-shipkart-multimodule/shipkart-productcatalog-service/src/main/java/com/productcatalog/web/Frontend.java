package com.productcatalog.web;

import java.util.List;

public class Frontend implements ICourses {

	@Override
	public List<String> getCourses() {
		return List.of("React", "Tailwind");
	}

}
