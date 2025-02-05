package org.agoncal.fascicle;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class InvoiceTest {

  @Inject
  Invoice invoice;

  @Test
  public void shouldCalculateInvoice() {
    invoice.subtotal = 500f;
    invoice.vatAmount = invoice.subtotal * (invoice.vatRate / 100);
    invoice.total = invoice.subtotal + invoice.vatAmount;
    assertEquals(10f, invoice.vatRate);
    assertEquals(50f, invoice.vatAmount);
    assertEquals(550f, invoice.total);
    assertFalse(invoice.allowsDiscount);
    assertTrue(invoice.terms.startsWith("Payment"));
    assertTrue(invoice.penalties.startsWith("Penalty"));
  }
}
