package com.task.demo.services;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.task.demo.dao.MyCurrencyDAO;
import com.task.demo.models.MyCurrency;
import com.task.demo.settings.ConverterSettings;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

@Component
public class MyCurrencyUpdateService extends Thread {

    @Autowired
    private final MyCurrencyDAO myCurrencyDAO;
    @Autowired
    private final ConverterSettings converterSettings;

    private final JsonFactory jsonFactory = new JsonFactory();


    private final String url = "https://api.nbrb.by/exrates/rates?periodicity=0";

    {
        this.start();
    }

    public MyCurrencyUpdateService(MyCurrencyDAO myCurrencyDAO, ConverterSettings converterSettings) {
        this.myCurrencyDAO = myCurrencyDAO;
        this.converterSettings = converterSettings;
    }

    public void doUpdate(){
        try {
            JsonParser jsonParser = jsonFactory.createParser(new URI("https://api.nbrb.by/exrates/rates?periodicity=0").toURL());
            jsonParser.nextToken();
            List<MyCurrency> myCurrencies = new LinkedList<>();
            while(jsonParser.nextToken() != JsonToken.END_ARRAY){
                MyCurrency myCurrency = new MyCurrency();
                while(jsonParser.nextToken() != JsonToken.END_OBJECT){
                    String property = jsonParser.currentName();
                    jsonParser.nextToken();
                    switch (property) {
                        case "Cur_Abbreviation":
                            myCurrency.setName(jsonParser.getText());
                            break;
                        case "Cur_Scale":
                            myCurrency.setScale(jsonParser.getIntValue());
                            break;
                        case "Cur_OfficialRate":
                            myCurrency.setSellRate(Precision.round(jsonParser.getDoubleValue() * (1+ converterSettings.getMargin()),7));
                            myCurrency.setBuyRate(Precision.round(jsonParser.getDoubleValue() * (1- converterSettings.getMargin()),7));
                            break;

                    }
                }
                myCurrencies.add(myCurrency);
            }
            jsonParser.close();
            myCurrencyDAO.updateCurrencies(myCurrencies);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        while (true) {
            if(converterSettings.isUpdateActive()){
                this.doUpdate();
                try {
                    Thread.sleep(converterSettings.getTimeToUpdateInSeconds()*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
