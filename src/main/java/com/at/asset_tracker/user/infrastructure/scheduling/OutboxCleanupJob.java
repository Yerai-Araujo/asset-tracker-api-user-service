package com.at.asset_tracker.user.infrastructure.scheduling;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxCleanupJob {

    private final JdbcTemplate jdbcTemplate;

    public OutboxCleanupJob(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(cron = "0 0 3 * * *") // cada día a las 3am
    public void cleanup() {

        jdbcTemplate.update("""
            DELETE FROM outbox_events
            WHERE created_at < now() - interval '7 days'
        """);
    }
}