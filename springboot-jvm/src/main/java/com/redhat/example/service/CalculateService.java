package com.redhat.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    private static final Logger LOG = LoggerFactory.getLogger(CalculateService.class);

    private final DatabaseService databaseService;

    // Inyección por constructor (recomendada en Spring)
    public CalculateService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Long calculate(Long value1, String method, Long value2) {
        LOG.debug("Calculating {} {} {}", value1, method, value2);

        Long result = 0L;
        switch (method) {
            case "addition":
                result = Math.addExact(value1, value2);
                break;
            case "subtraction":
                result = Math.subtractExact(value1, value2);
                break;
            case "multiplication":
                result = Math.multiplyExact(value1, value2);
                break;
            case "division":
                result = Math.floorDiv(value1, value2);
                break;
            default:
                LOG.warn("Unknown method: {}", method);
        }

        databaseService.persist(value1, method, value2, result);
        return result;
    }

    public long findNthPrime(int n) {
        int count = 0;
        long number = 1;
        while (count < n) {
            number++;
            if (isPrime(number)) {
                count++;
            }
        }
        return number;
    }

    private boolean isPrime(long num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;

        for (long i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return true;
    }
}
