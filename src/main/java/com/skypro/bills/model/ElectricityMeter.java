package com.skypro.bills.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ElectricityMeter {

  @Id
  private String serialNumber;
  @OneToMany(mappedBy = "electricityMeter", cascade = CascadeType.ALL)
  private List<Indication> indications;
  private double balance;

  public ElectricityMeter(String serialNumber, double balance) {
    this.serialNumber = serialNumber;
    this.balance = balance;
  }
}
