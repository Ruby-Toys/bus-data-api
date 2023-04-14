package ruby.busdataapi.busstation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusSearchConditionDTO {
    private String search = "";
    private int page = 1;
}
