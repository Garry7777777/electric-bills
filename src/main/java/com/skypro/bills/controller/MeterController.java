package com.skypro.bills.controller;

import com.skypro.bills.dto.MeterDTO;
import com.skypro.bills.service.MeterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//TO DO: Хорошо бы задокументировать АПИ :-(
// -DONE
@Tag(name="Контроллер счётчика", description="работа с данными счётчика")
@RestController
@RequestMapping("/meter")
public class MeterController {

  @Autowired
  MeterService meterService;

  @Operation(summary = "Создание счётчика", description = "Позволяет зарегистрировать счётчик")
  @PostMapping
  public ResponseEntity<?> createMeter(
          @RequestBody@Parameter(description = "Счётчик")
          MeterDTO meterDTO) {

    if (meterDTO == null)  return ResponseEntity.ok().build();
    return  ResponseEntity.ok(meterService.createMeter(meterDTO));
  }

  @Operation(summary = "Получение счётчика",description = "Позволяет получить инфомацию счётчика")
  @GetMapping("/{serial}")
  public ResponseEntity<?> getMeter(
          @PathVariable("serial")@Parameter(description = "Номер счётчика")
          String serial) {

    if (serial.isEmpty())  return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(meterService.getMeter(serial));
  }

  @Operation(summary = "Отправка индикации счётчика",description = "Позволяет отправить показания счётчика")
  @PostMapping("/{serial}/{indication}")
  public ResponseEntity<MeterDTO> newIndication(
          @PathVariable("serial")@Parameter(description = "Номер счётчика")
          String serial,

          @PathVariable("indication")@Parameter(description = "Показания счётчика счётчика")
          int indication) {

    if (indication < 0)  return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(meterService.setIndication(serial, indication));
  }

}
