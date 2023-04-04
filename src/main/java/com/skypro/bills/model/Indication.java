package com.skypro.bills.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Indication {

  @Id
  private String id;
  private Integer indication;
  private Instant sendingDate;
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private ElectricityMeter electricityMeter;

}
