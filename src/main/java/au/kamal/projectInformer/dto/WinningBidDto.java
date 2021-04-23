package au.kamal.projectInformer.dto;

import java.math.BigDecimal;

public class WinningBidDto
{
    private final String projectSummary;
    private final String customerName;
    private final String tradieName;
    private final BigDecimal winningBidTotal;

    private WinningBidDto(Builder inBuilder)
    {
        projectSummary = inBuilder.getProjectSummary();
        customerName = inBuilder.getCustomerName();
        tradieName = inBuilder.getTradieName();
        winningBidTotal = inBuilder.getWinningBidTotal();
    }

    public String getProjectSummary()
    {
        return projectSummary;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getTradieName()
    {
        return tradieName;
    }

    public BigDecimal getWinningBidTotal()
    {
        return winningBidTotal;
    }

    public static class Builder
    {
        private String projectSummary;
        private String customerName;
        private String tradieName;
        private BigDecimal winningBidTotal;

        public WinningBidDto build()
        {
            return new WinningBidDto(this);
        }

        public String getProjectSummary()
        {
            return projectSummary;
        }

        public Builder withProjectSummary(String inProjectSummary)
        {
            projectSummary = inProjectSummary;
            return this;
        }

        public String getCustomerName()
        {
            return customerName;
        }

        public Builder withCustomerName(String inCustomerName)
        {
            customerName = inCustomerName;
            return this;
        }

        public String getTradieName()
        {
            return tradieName;
        }

        public Builder withTradieName(String inTradieName)
        {
            tradieName = inTradieName;
            return this;
        }

        public BigDecimal getWinningBidTotal()
        {
            return winningBidTotal;
        }

        public Builder withWinningBidTotal(BigDecimal inWinningBidTotal)
        {
            winningBidTotal = inWinningBidTotal;
            return this;
        }
    }
}
