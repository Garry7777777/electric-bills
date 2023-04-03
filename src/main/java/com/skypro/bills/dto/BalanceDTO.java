package com.skypro.bills.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность баланса")
public class BalanceDTO {
  @Schema(description = "серийный номер")
  private String serialNumber;
  @Schema(description = "баланс")
  private double currentBalance;

}
