package ruby.busdataapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BusStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String stationId;

    private String address;
    private String roadAddress;

    private Double lat;
    private Double lng;
    private String bcode;
}
