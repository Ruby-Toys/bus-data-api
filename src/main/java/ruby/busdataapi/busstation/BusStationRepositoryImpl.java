package ruby.busdataapi.busstation;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import ruby.busdataapi.busstation.dto.BusSearchConditionDTO;

import java.util.List;

import static ruby.busdataapi.busstation.QBusStation.busStation;

@RequiredArgsConstructor
public class BusStationRepositoryImpl implements BusStationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BusStation> findBySearchCondition(BusSearchConditionDTO searchCondition) {
        int page = searchCondition.getPage() - 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        String search = searchCondition.getSearch();

        List<BusStation> busStations = jpaQueryFactory
                .selectFrom(busStation)
                .where(
                        busStation.name.contains(search)
                                .or(busStation.address.contains(search))
                                .or(busStation.roadAddress.contains(search))
                )
                .orderBy(busStation.id.desc())
                .limit(pageSize)
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<BusStation> query = jpaQueryFactory
                .selectFrom(busStation)
                .where(
                        busStation.name.contains(search)
                                .or(busStation.address.contains(search))
                                .or(busStation.roadAddress.contains(search))
                );

        return PageableExecutionUtils.getPage(busStations, pageable, () -> query.fetch().size());
    }
}
