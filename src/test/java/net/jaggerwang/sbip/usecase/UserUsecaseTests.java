package net.jaggerwang.sbip.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import net.jaggerwang.sbip.api.config.CommonConfig;
import net.jaggerwang.sbip.api.config.SpringDataConfig;
import net.jaggerwang.sbip.api.config.UsecaseConfig;

@DataJpaTest
@Import({CommonConfig.class, UsecaseConfig.class, SpringDataConfig.class})
@ActiveProfiles("test")
public class UserUsecaseTests {
    @Autowired
    private UserUsecases userUsecases;
}
