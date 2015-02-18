package com.payu.soa.example.client;

import com.payu.ratel.Discover;
import com.payu.order.server.model.Order;
import com.payu.order.server.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClientTestApp.class)
public class ClientVerification {

    @Autowired
    @Discover
    private OrderService testBean;

    @Test
    public void shouldCreateAndGetOrder() throws Exception {

        Order origOrder = new Order(BigDecimal.ZERO, "whatul");
        Long id = testBean.createOrder(origOrder);
        final Order order = testBean.getOrder(id);

        then(order).isNotNull().isEqualToIgnoringNullFields(origOrder);

    }
}
