package com.skypro.bills.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность платежа")
public class PaymentDTO {
  @Schema(description = "платёж")
  private double amount;

}
