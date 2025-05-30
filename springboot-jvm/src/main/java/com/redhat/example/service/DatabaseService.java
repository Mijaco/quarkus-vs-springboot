package com.redhat.example.service;

import com.redhat.example.entity.Calculator;
import com.redhat.example.repository.CalculatorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DatabaseService {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseService.class);

    private final CalculatorRepository calculatorRepository;

    public DatabaseService(CalculatorRepository calculatorRepository) {
        this.calculatorRepository = calculatorRepository;
    }

    public void persist(Long value1, String method, Long value2, Long total) {
        LOG.debug("Saving to DB value1 = {}, method = {}, value2 = {}", value1, method, value2);

        // ⏱️ Iniciar tiempo
        long start = System.nanoTime();

        // 🧮 Calcular suma de los 10000 primeros números primos
        long primeSum = sumFirstNPrimes(500_000);

        // ⏱️ Fin tiempo
        long end = System.nanoTime();
        double durationInSeconds = (end - start) / 1_000_000_000.0;

        // 🖨️ Imprimir tiempo de procesamiento
        LOG.info("Time to sum first 10000 primes: {} seconds", durationInSeconds);

        // Guardar en base de datos, incluyendo el tiempo en el método como texto
        String methodWithDuration = method + " # duration: [ " + durationInSeconds + " s ]";
        Calculator entity = new Calculator(value1, methodWithDuration, value2, total);
        calculatorRepository.save(entity);
    }
    public List<Calculator> getResult() {
        LOG.debug("Getting last 5 results");
        return calculatorRepository.findTop5ByOrderByIdDesc();
    }

    // Método auxiliar para calcular suma de primos
    private long sumFirstNPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        int num = 2;
        while (primes.size() < n) {
            if (isPrime(num)) {
                primes.add(num);
            }
            num++;
        }
        return primes.stream().mapToLong(Integer::longValue).sum();
    }

    private boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
