package pricecheckerby.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PriceData {
    private Float price;
    private LocalDate date;
}
