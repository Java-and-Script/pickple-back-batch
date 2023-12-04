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
public class GameEndedTasklet implements Tasklet {

    private final GameRepository gameRepository;

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) {
        log.info("[Batch Start] - Update Game Status");

        final LocalDateTime nowDateTime = LocalDateTime.now();
        final List<GameStatusUpdate> closedGameStatusUpdates = gameRepository.findGamesByStatusAndPlayDateEndTimeBeforeNow(
                "모집 마감",
                nowDateTime);

        closedGameStatusUpdates.forEach(closedGameStatusUpdate -> {
            gameRepository.updateGameStatus(closedGameStatusUpdate.getId(), "경기 종료");
            log.info("[Batch Processing] - CLOSED -> ENDED updatedGameId: {}", closedGameStatusUpdate.getId());
        });

        log.info("[Batch End] - CLOSED -> ENDED UpdatedGameCount: {}", closedGameStatusUpdates.size());

        return RepeatStatus.FINISHED;
    }
}
