package com.skypro.bills.controller;

import com.skypro.bills.dto.BalanceDTO;
import com.skypro.bills.dto.PaymentDTO;
import com.skypro.bills.service.MeterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO: Хорошо бы задокументировать АПИ :-(
// -DONE
@Tag(name="Контроллер биллинга", description="создание платежа и получение биллинга")
@RestController
@RequestMapping("/billing")
public class BillingController {

  @Autowired
  MeterService meterService;

  @Operation(summary = "Отправка платежа", description = "Производит пополнение баланса")
  @PostMapping("/{serial}")
  public ResponseEntity<BalanceDTO> pay(
          @PathVariable("serial")@Parameter(description = "Номер счётчика")
          String serial,
          @RequestBody@Parameter(description = "Платёж")
          PaymentDTO paymentDTO) {

    if (paymentDTO.getAmount() <= 0 || serial.isEmpty() )
                          return ResponseEntity.badRequest().build();
    return ResponseEntity.ok( meterService.pay(serial,paymentDTO)) ;
  }

  @Operation(summary = "Получение биллинга", description = "Получает инфомацию по балансу")
  @GetMapping("/{serial}")
  public ResponseEntity<BalanceDTO> payBalance(
          @PathVariable("serial")@Parameter(description = "Номер счётчика")
          String serial) {

    if ( serial.isEmpty() ) return ResponseEntity.badRequest().build();
    return ResponseEntity.ok( meterService.pay(serial, null));
  }
}
