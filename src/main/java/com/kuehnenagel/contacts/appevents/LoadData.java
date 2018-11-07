package com.kuehnenagel.contacts.appevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.kuehnenagel.contacts.services.LoadDataService;

@Component
public class LoadData implements ApplicationListener<ApplicationReadyEvent> {
	
	private final LoadDataService loadDataService;

	@Autowired
	public LoadData(LoadDataService loadDataService) {
		this.loadDataService = loadDataService;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		loadDataService.loadData();
	}

}
