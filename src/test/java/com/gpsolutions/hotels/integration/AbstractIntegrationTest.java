package com.gpsolutions.hotels.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;

@SqlMergeMode(MergeMode.OVERRIDE)
@Sql(scripts = "classpath:integration-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@Tag("integration")
public abstract class AbstractIntegrationTest {
}
