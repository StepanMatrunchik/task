package com.task.demo.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.task.demo.dao.MyCurrencyDAO;
import com.task.demo.exceptions.MyCurrencyNotFound;
import com.task.demo.models.Exrate;
import com.task.demo.models.MyCurrency;
import com.task.demo.settings.ConverterSettings;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Service
public class MyCurrencyService {
    @Autowired
    public final MyCurrencyDAO myCurrencyDAO;

    @Autowired
    public final ConverterSettings converterSettings;

    public MyCurrencyService(MyCurrencyDAO myCurrencyDAO, ConverterSettings converterSettings) {
        this.myCurrencyDAO = myCurrencyDAO;
        this.converterSettings=converterSettings;
    }

 
    public double convertFromCurrencyToBYN(MyCurrency myCurrency){
        if(!myCurrency.getName().equals("BYN")){
            return myCurrency.getBuyRate() / myCurrency.getScale();
        }
        else return 1;
    }

    public double convertFromBYNToCurrency(MyCurrency myCurrency){
        if(!myCurrency.getName().equals("BYN")){
            return myCurrency.getSellRate() / myCurrency.getScale();
        }
        else return 1;
    }

    public double convertFromCurrencyToCurrency(MyCurrency currency_from, MyCurrency currency_to){
        return Precision.round(convertFromCurrencyToBYN(currency_from) / convertFromBYNToCurrency(currency_to),7);

    }

    public List<Exrate> getConvertationByCurrencyFrom(String curr_from){
        List<Exrate> exrates = new LinkedList<>();
        List<MyCurrency> myCurrencies = myCurrencyDAO.list();
        MyCurrency sourceCurrency = myCurrencyDAO.getMyCurrencyByCurrencyName(curr_from);
        if(sourceCurrency==null){
            throw new MyCurrencyNotFound("currency not found");
        }
        for(MyCurrency myCurrency: myCurrencies){
            if(!myCurrency.getName().equals(curr_from)){
                exrates.add(new Exrate(curr_from,myCurrency.getName(),convertFromCurrencyToCurrency(sourceCurrency,
                        myCurrencyDAO.getMyCurrencyByCurrencyName(myCurrency.getName()))));
            }
        }
        return exrates;
    }

    public Exrate getConvertationByCurrencyFromAndCurrencyTo(String curr_from, String curr_to) {
        MyCurrency sourceCurrency = myCurrencyDAO.getMyCurrencyByCurrencyName(curr_from);
        MyCurrency targetCurrency = myCurrencyDAO.getMyCurrencyByCurrencyName(curr_to);
        if(sourceCurrency == null || targetCurrency==null){
            throw new MyCurrencyNotFound("currency not found");
        }
        return new Exrate(curr_from,curr_to,
                convertFromCurrencyToCurrency(sourceCurrency,
                targetCurrency));
    }
}
