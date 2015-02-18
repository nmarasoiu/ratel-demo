package com.payu.soa.example.client;

import com.payu.ratel.Discover;
import com.payu.order.server.model.Order;
import com.payu.order.server.service.OrderService;
import com.payu.ratel.RetryPolicy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClientTestApp.class)
public class ClientVerification {

    @Autowired
    @Discover
    @RetryPolicy(exception = RemoteConnectFailureException.class)
    private OrderService testBean;

    @Test
    public void shouldCreateAndGetOrder() {

        Order origOrder = new Order(20L, "laptops");
        Long id = testBean.createOrder(origOrder);
        final Order reviewOrder = testBean.getOrder(id);

        then(reviewOrder).isNotNull().isEqualToIgnoringNullFields(origOrder);

    }
}
