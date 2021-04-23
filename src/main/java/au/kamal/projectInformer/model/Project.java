package au.kamal.projectInformer.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project
{
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String summary;
    private int expectedDurationInHours;
    private LocalDateTime auctionEnd;
    @OneToMany(
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Bid> bids;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Customer customer;

    public Project()
    {
        bids = new ArrayList<>();
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String inDescription)
    {
        description = inDescription;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String inSummary)
    {
        summary = inSummary;
    }

    public int getExpectedDurationInHours()
    {
        return expectedDurationInHours;
    }

    public void setExpectedDurationInHours(int inExpectedDurationInHours)
    {
        expectedDurationInHours = inExpectedDurationInHours;
    }

    public LocalDateTime getAuctionEnd()
    {
        return auctionEnd;
    }

    public void setAuctionEnd(LocalDateTime inAuctionEnd)
    {
        auctionEnd = inAuctionEnd;
    }

    public List<Bid> getBids()
    {
        return bids;
    }

    public void setBids(List<Bid> inBids)
    {
        bids = inBids;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer inCustomer)
    {
        customer = inCustomer;
    }

    @Override
    public String toString()
    {
        return new ReflectionToStringBuilder(this).toString();
    }
}
