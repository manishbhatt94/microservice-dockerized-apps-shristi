package com.productcatalog.web;

import java.util.List;

public class Backend implements ICourses {

	@Override
	public List<String> getCourses() {
		return List.of("Spring", "Microservices");
	}

}
