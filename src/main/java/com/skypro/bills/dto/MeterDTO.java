package com.skypro.bills.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.skypro.bills.model.ElectricityMeter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность счётчика")
public class MeterDTO {
    @Schema(description = "серийный номер")
    private String serialNumber;
    @Schema(description = "показаниe счётчика")
    @JsonProperty(access = Access.READ_ONLY)
    private int lastIndication;


    public ElectricityMeter toMeter(){
      ElectricityMeter electricityMeter = new ElectricityMeter();
      electricityMeter.setSerialNumber(this.getSerialNumber());
      return electricityMeter;
    }
}
