package com.springreact.client.springreactclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author a0r00rf */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
  private String id;
  private String description;
  private Double price;
}
