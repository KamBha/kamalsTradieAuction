package au.kamal.projectInformer.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bid")
public class Bid
{
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private TradePerson tradePerson;
    private BigDecimal totalAmount;
    private BigDecimal perHourAmount;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Project project;

    public Bid()
    {
    }

    public TradePerson getTradePerson()
    {
        return tradePerson;
    }

    public void setTradePerson(TradePerson inTradePerson)
    {
        tradePerson = inTradePerson;
    }

    public void setProject(Project inProject)
    {
        project = inProject;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal inTotalAmount)
    {
        totalAmount = inTotalAmount;
    }

    public BigDecimal getPerHourAmount()
    {
        return perHourAmount;
    }

    public void setPerHourRate(BigDecimal inPerHourAmount)
    {
        perHourAmount = inPerHourAmount;
    }

    public BigDecimal calculateTotal(int inNumberOfHours)
    {
        return (totalAmount != null) ? totalAmount : perHourAmount.multiply(new BigDecimal(inNumberOfHours));
    }

    @Override
    public String toString()
    {
        return new ReflectionToStringBuilder(this).toString();
    }
}
