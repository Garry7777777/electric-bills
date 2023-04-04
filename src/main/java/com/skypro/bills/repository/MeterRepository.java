package com.skypro.bills.repository;

import com.skypro.bills.model.ElectricityMeter;
import com.skypro.bills.projection.LastIndicationProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MeterRepository extends JpaRepository<ElectricityMeter, String> {

    @Query(value ="""
                SELECT m.serial_number as serialNumber, m.balance, i.indication
                FROM electricity_meter m
                    LEFT JOIN indication i
                        ON m.serial_number = i.electricity_meter_serial_number
                WHERE m.serial_number = ?1
                GROUP BY m.serial_number, i.indication, m.balance, i.sending_date
                ORDER BY i.sending_date
                DESC LIMIT 1
                """ ,
    nativeQuery = true)
    Optional<LastIndicationProj> findUseSerialNumber(String serialNumber);
}


