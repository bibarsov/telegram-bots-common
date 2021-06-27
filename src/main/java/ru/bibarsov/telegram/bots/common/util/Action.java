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

    static void retryableAction(Action action, int retryCount, Logger logger) {
        while (--retryCount >= 0) {
            try {
                action.execute();
                break;
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("retryableAction interrupted");
            } catch (Throwable t) {
                if (retryCount > 0) {
                    logger.warn("Couldn't persist data, remaining retries: " + retryCount, t);
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("sleep interrupted");
                    }
                } else {
                    logger.error("Failed to persist data.", t);
                }
            }
        }
    }
}
