package pickple.back.batch.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import pickple.back.batch.domain.GameStatusUpdate;

@Mapper
public interface GameMapper {

    List<GameStatusUpdate> findGamesByStatusAndPlayDateStartTimeBeforeNow(
            final String status,
            final LocalDateTime nowDateTime
    );

    List<GameStatusUpdate> findGamesByStatusAndPlayDateEndTimeBeforeNow(
            final String status,
            final LocalDateTime nowDateTime
    );

    void updateGameStatus(final Long id, final String status);
}
