package com.example.datasource;

import com.example.datasource.model.EcOrder;
import com.example.datasource.service.EcOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Slf4j
@SpringBootApplication
public class DatasourceApplication implements ApplicationRunner {
	@Autowired
    private EcOrderService ecOrderService;

	public static void main(String[] args) {
		SpringApplication.run(DatasourceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<EcOrder> orders = ecOrderService.findAll();
		log.info("find all orders: {}", orders);

		Instant instant = Instant.now();
		EcOrder order = EcOrder.builder()
				.userId(9999L)
				.totalPrice(BigDecimal.valueOf(9999.99))
                .createTime(instant.getEpochSecond())
				.updateTime(instant.getEpochSecond())
				.build();
		EcOrder newOrder = ecOrderService.create(order);
		log.info("create new order: {}", newOrder);
	}
}
