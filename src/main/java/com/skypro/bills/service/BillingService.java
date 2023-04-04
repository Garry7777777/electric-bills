package com.skypro.bills.service;

import com.skypro.bills.dto.*;
import com.skypro.bills.exceptions.*;
import com.skypro.bills.model.*;
import com.skypro.bills.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class BillingService {
    //TO DO: Стоит сделать это свойство конфигурируемым чере application.properties
    // -DONE
    @Value("${app.info.priceForKW}")
    private static double priceForKW;

    @Autowired
    MeterRepository  meterRepository;
    @Autowired
    IndicationRepository indicationRepository;

    public BalanceDTO pay(String serialNumber, PaymentDTO paymentDTO) {
        var meterProj = meterRepository.findUseSerialNumber(serialNumber)
                                   .orElseThrow(ValueNotFoundException::new);
        if (paymentDTO != null)
            meterRepository.save(new ElectricityMeter(serialNumber,
                                 meterProj.getBalance() + paymentDTO.getAmount()));
        //Подсчитываем баланс - берем последние показания счетчика и умножаем на стоимость,
        // далее вычитаем из положенных на счетчик средств
        return new BalanceDTO(serialNumber,
                meterProj.getBalance() - meterProj.getIndication() * priceForKW);
    }

}
