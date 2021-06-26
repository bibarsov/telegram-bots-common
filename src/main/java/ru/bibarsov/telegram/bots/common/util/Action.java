package ru.bibarsov.telegram.bots.common.util;

import org.slf4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.TimeUnit;

@ParametersAreNonnullByDefault
public interface Action {

    Action IDENTITY = () -> {

    };

    static Action identity() {
        return IDENTITY;
    }

    void execute() throws Exception;

    static void executeQuietly(Action action) {
        try {
            action.execute();
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        } catch (Throwable ignored) {
        }
    }

    static void retryableAction(Action action, int retryCount, Logger logger) throws InterruptedException {
        while (--retryCount >= 0) {
            try {
                action.execute();
                break;
            } catch (Throwable t) {
                if (retryCount > 0) {
                    logger.warn("Couldn't persist data, remaining retries: " + retryCount, t);
                    TimeUnit.SECONDS.sleep(1L);
                } else {
                    logger.error("Failed to persist data.", t);
                }
            }
        }
    }
}
