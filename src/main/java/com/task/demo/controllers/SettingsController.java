package com.task.demo.controllers;

import com.task.demo.services.SettingsService;
import com.task.demo.settings.ConverterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingsController {
    @Autowired
    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }


    @GetMapping("/api/settings")
    public ConverterSettings getConverterSettings(){
        return settingsService.getConverterSettings();
    }

    @PostMapping("/api/settings")
    public void changeConverterSettings(@RequestBody ConverterSettings converterSettings){
        settingsService.updateSettings(converterSettings);
    }

}
