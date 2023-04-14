package ruby.busdataapi.busstation.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import ruby.busdataapi.busstation.BusStation;

import java.util.List;

@Getter
public class BusStationListDTO {

    private final List<BusDataDTO> busStations;
    private final int page;
    private final int totalPage;

    @Builder
    public BusStationListDTO(Page<BusStation> busStationPage) {
        List<BusStation> pageContent = busStationPage.getContent();
        this.busStations = pageContent.stream()
                .map(busStation -> BusDataDTO.builder()
                        .stationId(busStation.getStationId())
                        .name(busStation.getName())
                        .address(busStation.getAddress())
                        .roadAddress(busStation.getRoadAddress())
                        .lat(busStation.getLat().toString())
                        .lng(busStation.getLng().toString())
                        .build()
                )
                .toList();

        this.page = busStationPage.getNumber() + 1;
        this.totalPage = busStationPage.getTotalPages();
    }
}
