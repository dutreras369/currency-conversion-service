package cl.paseodelasdelicias.microservices.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CurrencyConversion {

		private Long Id;
		private String from;
		private String to;
		private BigDecimal conversionMultiple;
		private BigDecimal quantity;
		private BigDecimal totalAmount;
		private int port;
}
