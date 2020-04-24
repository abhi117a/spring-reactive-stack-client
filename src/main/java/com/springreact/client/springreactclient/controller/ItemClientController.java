package com.springreact.client.springreactclient.controller;

import com.springreact.client.springreactclient.domain.Item;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

  @GetMapping("/client/retrive/singleitem/{id}")
  public Mono<Item> getItemUsingRetrive(@PathVariable String id) {

    return webClient
        .get()
        .uri("/v1/items/{id}", id)
        .retrieve()
        .bodyToMono(Item.class)
        .log("Items from Client using retrieve SingleItem: ");
  }

  @GetMapping("/client/exchange/singleitem/{id}")
  public Mono<Item> getItemUsingExchange(@PathVariable String id) {

    return webClient
        .get()
        .uri("/v1/items/{id}", id)
        .exchange()
        .flatMap(clientResponse -> clientResponse.bodyToMono(Item.class))
        .log("Items from Client using exchange SingleItem: ");
  }

  @PostMapping("/client/createitem")
  public Mono<Item> createItem(@RequestBody Item item) {

    Mono<Item> itemMono = Mono.just(item);

    return webClient
        .post()
        .uri("/v1/items")
        .contentType(MediaType.APPLICATION_JSON)
        .body(itemMono, Item.class)
        .retrieve()
        .bodyToMono(Item.class)
        .log("Created item is: ");
  }

  @PostMapping("/client/updateitem/{id}")
  public Mono<Item> updateItem(@PathVariable String id, @RequestBody Item item) {

    Mono<Item> itemMono = Mono.just(item);

    return webClient
        .put()
        .uri("/v1/items/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .body(itemMono, Item.class)
        .retrieve()
        .bodyToMono(Item.class)
        .log("Update item is: ");
  }

  @DeleteMapping("/client/deleteitem/{id}")
  public Mono<Void> updateItem(@PathVariable String id) {

    return webClient
        .delete()
        .uri("/v1/items/{id}", id)
        .retrieve()
        .bodyToMono(Void.class)
        .log("Deleted item is: ");
  }
}
