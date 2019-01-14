package fund.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JSONResult {
    boolean success;
    String message;
    String successUrl;
}
