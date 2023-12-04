package pickple.back.batch;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pickple.back.batch.domain.GameStatusUpdate;
import pickple.back.batch.repository.GameRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameClosedTasklet implements Tasklet {

    private final GameRepository gameRepository;

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) {
        log.info("[Batch Start] - Update Game Status");

        final LocalDateTime nowDateTime = LocalDateTime.now();
        final List<GameStatusUpdate> openGameStatusUpdates = gameRepository.findGamesByStatusAndPlayDateStartTimeBeforeNow(
                "모집 중",
                nowDateTime);

        openGameStatusUpdates.forEach(openGameStatusUpdate -> {
            gameRepository.updateGameStatus(openGameStatusUpdate.getId(), "모집 마감");
            log.info("[Batch Processing] - OPEN -> CLOSED updatedGameId: {}", openGameStatusUpdate.getId());
        });

        log.info("[Batch End] - OPEN -> CLOSED UpdatedGameCount: {}", openGameStatusUpdates.size());

        return RepeatStatus.FINISHED;
    }
}
