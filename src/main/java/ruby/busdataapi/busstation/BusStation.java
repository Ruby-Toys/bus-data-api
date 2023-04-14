package ruby.busdataapi.busstation;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BusStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String stationId;
    private String name;

    private String address;
    private String roadAddress;

    private Double lat;
    private Double lng;

    @Builder
    public BusStation(String stationId, String name, String address, String roadAddress, Double lat, Double lng) {
        this.stationId = stationId;
        this.name = name;
        this.address = address;
        this.roadAddress = roadAddress;
        this.lat = lat;
        this.lng = lng;
    }
}
