package fund.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class PaymentJSON extends Payment {

    List<PaymentInKind> items;

}
