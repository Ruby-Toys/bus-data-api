package ruby.busdataapi.busstation.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TestDTO {
    private String stationId;
    private String name;

    private String address;
    private String roadAddress;

    private String lat;
    private String lng;
    private String bcode;
}
