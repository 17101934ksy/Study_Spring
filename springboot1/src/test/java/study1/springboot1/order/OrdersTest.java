package study1.springboot1.order;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class OrdersTest {

    @Test
    void saveOrder(){
        Orders order = new Orders(1L, "key", 10000, 1000);

        assertThat(order.getItemPrice()).isEqualTo(10000);
    }

}