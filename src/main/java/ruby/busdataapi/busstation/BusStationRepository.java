package ruby.busdataapi.busstation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStationRepository extends JpaRepository<BusStation, Long>, BusStationRepositoryCustom { }
