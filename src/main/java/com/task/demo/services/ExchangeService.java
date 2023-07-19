package com.task.demo.services;

import com.task.demo.dao.ExchangeOperationDAO;
import com.task.demo.dao.MyCurrencyDAO;
import com.task.demo.exceptions.BadExchangeRequest;
import com.task.demo.exceptions.MyCurrencyNotFound;
import com.task.demo.models.ExchangeOperation;
import com.task.demo.models.MyCurrency;
import com.task.demo.settings.ConverterSettings;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExchangeService {
    @Autowired
    public final MyCurrencyDAO myCurrencyDAO;

    @Autowired
    public final ExchangeOperationDAO exchangeOperationDAO;

    @Autowired
    public final ConverterSettings converterSettings;

    public ExchangeService(MyCurrencyDAO myCurrencyDAO,ExchangeOperationDAO exchangeOperationDAO, ConverterSettings converterSettings) {
        this.myCurrencyDAO = myCurrencyDAO;
        this.exchangeOperationDAO=exchangeOperationDAO;
        this.converterSettings=converterSettings;
    }

    public double getExchangeRateFromCurrencyToBYN(String curr_from){
        if(!curr_from.equals("BYN")){
            MyCurrency myCurrency = myCurrencyDAO.getMyCurrencyByCurrencyName(curr_from);
            if(myCurrency==null){
                throw new MyCurrencyNotFound("currency not found");
            }
            return myCurrency.getBuyRate() / myCurrency.getScale();
        }
        else return 1;
    }

    public double getExchangeRateFromBYNToCurrency(String curr_to){
        if(!curr_to.equals("BYN")){
            MyCurrency myCurrency = myCurrencyDAO.getMyCurrencyByCurrencyName(curr_to);
            if(myCurrency==null){
                throw new MyCurrencyNotFound("currency not found");
            }
            return myCurrency.getSellRate() / myCurrency.getScale();
        }
        else return 1;

    }
    public double getExchangeRateFromCurrencyToCurrency(String curr_from, String curr_to){
        return Precision.round(getExchangeRateFromCurrencyToBYN(curr_from) / getExchangeRateFromBYNToCurrency(curr_to),7);
    }


    public Map<String,Double> doExchange(ExchangeOperation exchangeOperation){
        if(exchangeOperation.getSourceCurrency()==null || exchangeOperation.getTargetCurrency()==null){
            throw new BadExchangeRequest("sourceCurrency or targetCurrency is null");
        }
        if(exchangeOperation.getSourceCurrency().equals(exchangeOperation.getTargetCurrency())){
            throw new BadExchangeRequest("sourceCurrency == targetCurrency");
        }
        if(exchangeOperation.getSourceAmount()>0 && exchangeOperation.getTargetAmount()==0){
            exchangeOperation.setPrice(getExchangeRateFromCurrencyToCurrency(exchangeOperation.getSourceCurrency(),
                    exchangeOperation.getTargetCurrency()));
            exchangeOperation.setTargetAmount(Precision.round(exchangeOperation.getSourceAmount()
                    *exchangeOperation.getPrice(),7));
            exchangeOperation.setDateTime(LocalDateTime.now());
            exchangeOperationDAO.saveExchangeOperation(exchangeOperation);
            Map<String,Double> exchangeResult =new HashMap<>();
            exchangeResult.put("targetAmount", exchangeOperation.getTargetAmount());
            exchangeResult.put("price", exchangeOperation.getPrice());
            return exchangeResult;

        } else if (exchangeOperation.getSourceAmount()==0 && exchangeOperation.getTargetAmount()>0) {
            exchangeOperation.setPrice(getExchangeRateFromCurrencyToCurrency(exchangeOperation.getSourceCurrency(),
                    exchangeOperation.getTargetCurrency()));
            exchangeOperation.setSourceAmount(Precision.round(exchangeOperation.getTargetAmount()
                    /exchangeOperation.getPrice(),7));
            exchangeOperation.setDateTime(LocalDateTime.now());
            exchangeOperationDAO.saveExchangeOperation(exchangeOperation);
            Map<String,Double> exchangeResult = new HashMap<>();
            exchangeResult.put("sourceAmount", exchangeOperation.getSourceAmount());
            exchangeResult.put("price", exchangeOperation.getPrice());
            return exchangeResult;

        } else throw new BadExchangeRequest("wrong sourceCurrency or targetCurreny values");
    }

    public List<ExchangeOperation> getExchangeOperationsFromDateToDate(LocalDateTime fromDate, LocalDateTime toDate){
        if(toDate.isBefore(fromDate)){
            throw new BadExchangeRequest("toDate is before fromDate");
        }
        List<ExchangeOperation> exchangeOperations = exchangeOperationDAO.list();
        List<ExchangeOperation> filteredExchangeOperations = new LinkedList<>();
        for(ExchangeOperation exchangeOperation: exchangeOperations){
            if ((exchangeOperation.getDateTime().isAfter(fromDate) && exchangeOperation.getDateTime().isBefore(toDate)) || exchangeOperation.getDateTime().isEqual(fromDate) || exchangeOperation.getDateTime().isEqual(toDate)){
                filteredExchangeOperations.add(exchangeOperation);
            }
        }
        return  filteredExchangeOperations;

    }
}
