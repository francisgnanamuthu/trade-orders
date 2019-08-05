package com.fcr.trade.orders

import com.fcr.trade.orders.model.GetOrderResponse
import com.fcr.trade.orders.model.Order
import com.fcr.trade.orders.model.OrderResponse
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate

class MakeATradeOrderSpec extends BaseSpec {
    RestTemplate restTemplate = new RestTemplate()

    def 'Make A trade Order Scenario'() {
        given: 'A Valida Trade Order is provided'

        def order = new Order(
                fundName: fundname,
                price: price,
                quantity: quantity,
                security: security,
                side: Order.SideEnum.BUY
        )

        when: 'Trade Order Service is called'

        HttpEntity<Order> request = new HttpEntity<>(order)
        def response = restTemplate.postForObject("http://localhost:8090/trade/orders", request, OrderResponse.class)

        then: 'Order Processed Successfully'

        expect:
        with(response) {
            response.orderId != null
            response.orderId.size() == 36
        }

        where:
        fundname | price | quantity | security | side
        'MAG'    | 700   | 1000     | 'AAPL'   | 'Buy'
    }

    def 'Get Trade Order Summary'() {
        given: 'Retrieve trade Order Summary'

        when: 'Trade Order Service is called'

        def response = restTemplate.getForObject("http://localhost:8090/trade/orders/summary", GetOrderResponse.class)

        then: 'Order Retrieved Successfully'

        expect:
        with(response) {
            response.totalNumberOfOrders == totalNumberOfOrders
            response.totalQuantity == totalQuantity
            response.averagePrice == averagePrice

        }

        where:
        totalNumberOfOrders | totalQuantity | averagePrice | combinableOrder
        10                  | 21000         | 183          | '2(Buy+AAPL+MAG),2(Sell+T+F2)'
    }


}

