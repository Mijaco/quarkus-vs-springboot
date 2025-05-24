package com.redhat.example.service;

import com.redhat.example.entity.Calculator;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     com.redhat.example.service.DatabaseService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 28 Nov 2022 15:17
 */
@ApplicationScoped
@Transactional
public class DatabaseService {
    private static final Logger LOG = Logger.getLogger(DatabaseService.class);

    public void persist(Long value1, String method, Long value2, Long total ) {
        LOG.debug(String.format("saving to DB value1 = %s, method %s, value2 = %s ", value1, method, value2));

        // ⏱️ Iniciar tiempo
        long start = System.nanoTime();

        // 🧮 Calcular suma de los 10000 primeros números primos
        long primeSum = sumFirstNPrimes(500_000);

        // ⏱️ Fin tiempo
        long end = System.nanoTime();
        double durationInSeconds = (end - start) / 1_000_000_000.0;

        // 🖨️ Imprimir tiempo de procesamiento

        String methodWithDuration = method + " # duration: [ " + durationInSeconds + " s ]";

        Calculator.persist(new Calculator(value1, methodWithDuration, value2, total));
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

    public List<Calculator> getResult() {
        LOG.debug(String.format("getting all table result"));
        return Calculator.findAll(Sort.by("id", Sort.Direction.Descending))
                .page(Page.ofSize(5))
                .list();
    }
}
