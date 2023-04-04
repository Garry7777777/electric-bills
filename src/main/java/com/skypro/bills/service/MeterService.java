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
        var meter =  meterRepository.findUseSerialNumber(serialNumber)
                                    .orElseThrow(ValueNotFoundException::new);
        return new MeterDTO(meter.getSerialNumber(),meter.getIndication());
    }

    public MeterDTO setIndication( String serialNumber, int newIndication){
        var meterDTO = getMeter(serialNumber);
        if (meterDTO.getLastIndication() > newIndication) throw new IncorrectValueException();
        indicationRepository.save(new Indication
                (UUID.randomUUID().toString(), newIndication, Instant.now(), meterDTO.toMeter()));
        return new MeterDTO(meterDTO.getSerialNumber(), newIndication);
    }
}




