package au.kamal.projectInformer.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BidTest
{
    @Test
    void calculateUsesTotalWhenTotalSpecified()
    {
        Bid bid = new Bid();
        bid.setTotalAmount(new BigDecimal("100.00"));

        assertThat(bid.calculateTotal(10).toString(), is("100.00"));
    }

    @Test
    void calculateUsesPerHourRateWhenNoTotalValueSpecified()
    {
        Bid bid = new Bid();
        bid.setPerHourRate(new BigDecimal("100.00"));

        assertThat(bid.calculateTotal(10).toString(), is("1000.00"));
    }
}