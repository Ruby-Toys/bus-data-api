package ruby.busdataapi.busstation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BusDataDTO {
    private String stationId;
    private String name;

    private String address;
    private String roadAddress;

    private String lat;
    private String lng;

    @Builder
    public BusDataDTO(String stationId, String name, String address, String roadAddress, String lat, String lng) {
        this.stationId = stationId;
        this.name = name;
        this.address = address;
        this.roadAddress = roadAddress;
        this.lat = lat;
        this.lng = lng;
    }
}
