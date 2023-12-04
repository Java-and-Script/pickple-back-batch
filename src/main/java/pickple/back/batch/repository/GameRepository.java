package pickple.back.batch.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import pickple.back.batch.domain.GameStatusUpdate;
import pickple.back.batch.mapper.GameMapper;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private final GameMapper gameMapper;

    public List<GameStatusUpdate> findGamesByStatusAndPlayDateStartTimeBeforeNow(
            final String status,
            final LocalDateTime nowDateTime
    ) {
        return gameMapper.findGamesByStatusAndPlayDateStartTimeBeforeNow(status, nowDateTime);
    }

    public List<GameStatusUpdate> findGamesByStatusAndPlayDateEndTimeBeforeNow(
            final String status,
            final LocalDateTime nowDateTime
    ) {
        return gameMapper.findGamesByStatusAndPlayDateEndTimeBeforeNow(status, nowDateTime);
    }

    public void updateGameStatus(final Long id, final String status) {
        gameMapper.updateGameStatus(id, status);
    }
}
