package pricecheckerby.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SubscriptionParams {
    private String email;
    private Integer reductionAmount;
    private String url;
}
