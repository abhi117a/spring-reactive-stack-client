package com.springreact.client.springreactclient.controller;

import com.springreact.client.springreactclient.domain.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/** @author a0r00rf */
@RestController
public class ItemClientController {

  WebClient webClient = WebClient.create("http://localhost:8080");

  @GetMapping("/client/retrive")
  public Flux<Item> getAllItemsUsingRetrive() {

    return webClient
        .get()
        .uri("/v1/items")
        .retrieve()
        .bodyToFlux(Item.class)
        .log("Items from Client using retrieve: ");
  }

  @GetMapping("/client/exchange")
  public Flux<Item> getAllItemsUsingExchange() {

    return webClient
        .get()
        .uri("/v1/items")
        .exchange()
        .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Item.class))
        .log("Items from Client Using exchange: ");
  }
}
