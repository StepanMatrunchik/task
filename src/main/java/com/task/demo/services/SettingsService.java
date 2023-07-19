package com.task.demo.services;

import com.task.demo.settings.ConverterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    private final ConverterSettings converterSettings;

    public SettingsService(ConverterSettings converterSettings) {
        this.converterSettings = converterSettings;
    }

    public ConverterSettings getConverterSettings(){
        return this.converterSettings;
    }

    public void updateSettings(ConverterSettings converterSettings){
        if(converterSettings.getMargin()>0 && converterSettings.getMargin()<1){
            this.converterSettings.setMargin(converterSettings.getMargin());
        }

        if(converterSettings.getTimeToUpdateInSeconds()>0 ){
            this.converterSettings.setTimeToUpdateInSeconds(converterSettings.getTimeToUpdateInSeconds());
        }
        if(converterSettings.isUpdateActive()!=null){
            this.converterSettings.setUpdateActive(converterSettings.isUpdateActive());
        }
    }
}
