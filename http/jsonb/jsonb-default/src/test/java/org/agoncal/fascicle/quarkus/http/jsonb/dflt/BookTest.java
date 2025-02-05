package org.agoncal.fascicle.quarkus.http.jsonb.dflt;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static org.agoncal.fascicle.quarkus.http.jsonb.dflt.UtilTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
public class BookTest {

  private static Jsonb jsonb;

  @BeforeAll
  static void init() {
    JsonbConfig config = new JsonbConfig().withFormatting(true);
    jsonb = JsonbBuilder.create(config);
  }

  static BufferedWriter bw;

  @BeforeAll
  static void initFile() throws FileNotFoundException {
    bw = initBufferedWriter("src/main/java/org/agoncal/fascicle/quarkus/http/jsonb/dflt/Book-dummy.json");
  }

  @AfterAll
  static void closeFile() throws IOException {
    bw.close();
  }

  // ======================================
  // =              Methods               =
  // ======================================

  @Test
  void shouldMarshallADefaultBook() throws IOException {

    Book book = new Book().title("H2G2").price(12.5F).isbn("1-84023-742-2").nbOfPages(354).illustrations(false).description("Best Sci-fi book ever").publicationDate(LocalDate.of(1999, 04, 28));

    String json = jsonb.toJson(book);

    output(bw, json, "shouldMarshallADefaultBook");

    assertEquals("H2G2", jsonPath(json, "$.title"));
    assertEquals(12.5, jsonPath(json, "$.price"));
    assertEquals("1-84023-742-2", jsonPath(json, "$.isbn"));
    assertEquals(354, jsonPath(json, "$.nbOfPages"));
    assertEquals(Boolean.FALSE, jsonPath(json, "$.illustrations"));
    assertEquals("Best Sci-fi book ever", jsonPath(json, "$.description"));
  }

  @Test
  void shouldMarshallAndUnMarshall() throws IOException {

    // tag::adocSnippet[]
    // Creates a book instance
    Book book = new Book().title("H2G2").price(12.5F).isbn("1-84023-742-2");

    // Creates Jsonb using a builder
    Jsonb jsonb = JsonbBuilder.create();

    // Serialises
    String json = jsonb.toJson(book);
    // tag::adocSkip[]
    System.out.println(json);
    // end::adocSkip[]

    // Deserialises back
    book = jsonb.fromJson("{\"isbn\":\"1-84023-742-2\",\"price\":12.5,\"title\":\"H2G2\"}", Book.class);
    // end::adocSnippet[]

    assertEquals("H2G2", book.getTitle());
    assertEquals(12.5F, book.getPrice());
    assertEquals("1-84023-742-2", book.getIsbn());
  }
}
