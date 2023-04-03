package com.skypro.bills.service;

import com.skypro.bills.dto.*;
import com.skypro.bills.exceptions.*;
import com.skypro.bills.model.*;
import com.skypro.bills.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;


@Service
public class MeterService {

    //TODO: Стоит сделать это свойство конфигурируемым чере application.properties
    // -DONE
    @Value("${app.info.priceForKW}")
    private static double priceForKW;

    @Autowired
    MeterRepository  meterRepository;
    @Autowired
    IndicationRepository indicationRepository;

    public MeterDTO createMeter(MeterDTO meterDTO) {
        meterRepository.findById(meterDTO.getSerialNumber())
                       .orElse(meterRepository.save(meterDTO.toMeter()));
        return meterDTO;
        }

    public MeterDTO getMeter( String serialNumber) {
        var meter =  meterRepository.findById(serialNumber)
                                    .orElseThrow(ValueNotFoundException::new);
        var meterDTO = new MeterDTO();
        meterDTO.setSerialNumber(meter.getSerialNumber());
        meterDTO.setLastIndication(getLastIndicationBySerial(serialNumber));
        return meterDTO;
    }

    public ElectricityMeter checkIndication( String serialNumber, int newIndication){
        var meter =  meterRepository.findById(serialNumber)
                                    .orElseThrow(ValueNotFoundException::new);
        if (getLastIndicationBySerial(serialNumber) > newIndication) throw new IncorrectValueException();
        return meter;
    }

    public MeterDTO setIndication(Integer newIndication, ElectricityMeter meter){
        var indication = new Indication();
        indication.setIndication(newIndication);
        indication.setId(UUID.randomUUID().toString());
        indication.setSendingDate(Instant.now());
        indication.setElectricityMeter(meter);
        meter.getIndications().add(indication);
        meterRepository.save(meter);
        return new MeterDTO(meter.getSerialNumber(), newIndication);
    }

    public BalanceDTO pay(String serialNumber, PaymentDTO paymentDTO) {
        var meter = meterRepository.findById(serialNumber).orElseThrow(ValueNotFoundException::new);
        if (paymentDTO != null) {
            meter.setBalance(meter.getBalance() + paymentDTO.getAmount());
            meterRepository.save(meter);
        }
        //Подсчитываем баланс - берем последние показания счетчика и умножаем на стоимость,
        // далее вычитаем из положенных на счетчик средств
        double balance = meter.getBalance() - getLastIndicationBySerial(serialNumber) * priceForKW;
        return new BalanceDTO(serialNumber, balance);
    }

    protected Integer getLastIndicationBySerial(String serialNumber) {
        return indicationRepository
                .getFirstByElectricityMeter_SerialNumberOrderBySendingDateDesc(serialNumber)
                .orElse(new Indication()).getIndication();
    }

}




