package com.skypro.bills.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class ElectricityMeter {

  @Id
  private String serialNumber;
  @OneToMany(mappedBy = "electricityMeter", cascade = CascadeType.ALL)
  private List<Indication> indications;
  private double balance;

}
