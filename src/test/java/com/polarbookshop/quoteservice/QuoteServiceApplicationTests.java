package com.polarbookshop.quoteservice;

import com.polarbookshop.quoteservice.domain.Genre;
import com.polarbookshop.quoteservice.domain.Quote;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class QuoteServiceApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void whenAllQuotesThenReturn() {
    webTestClient.get().uri("/quotes")
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBodyList(Quote.class);
  }

  @Test
  void whenRandomQuoteThenReturn() {
    webTestClient.get().uri("/quotes/random")
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(Quote.class);
  }

  @Test
  void whenRandomQuoteByGenreThenReturn() {
    Genre fantasy = Genre.FANTASY;
    webTestClient.get().uri("/quotes/random/" + fantasy)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(Quote.class)
        .value(quote -> Assertions.assertThat(quote.genre()).isEqualTo(fantasy));
  }

}
