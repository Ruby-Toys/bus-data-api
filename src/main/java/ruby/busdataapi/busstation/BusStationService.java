package ruby.busdataapi.busstation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruby.busdataapi.busstation.dto.BusSearchConditionDTO;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusStationService {

    private final BusStationRepository busStationRepository;

    @Transactional
    public void renewBusStations(List<BusStation> busStations) {
        busStationRepository.deleteAll();
        busStationRepository.saveAll(busStations);

        log.info("{} 건 갱신 완료!", busStations.size());
    }

    public Page<BusStation> getBusStationsBySearchCondition(BusSearchConditionDTO searchCondition) {
        return busStationRepository.findBySearchCondition(searchCondition);
    }
}
