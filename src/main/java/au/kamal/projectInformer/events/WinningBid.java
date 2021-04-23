package au.kamal.projectInformer.events;

import au.kamal.projectInformer.model.Bid;
import au.kamal.projectInformer.model.Project;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

public class WinningBid extends ApplicationEvent
{
    private final Project project;
    private final Bid winningBid;

    public WinningBid(Object inSource, Project inProject, Bid inWinningBid)
    {
        super(inSource);
        project = inProject;
        winningBid = inWinningBid;
    }

    public Project getProject()
    {
        return project;
    }

    public Bid getWinningBid()
    {
        return winningBid;
    }

    public BigDecimal getTotal()
    {
        return winningBid.calculateTotal(project.getExpectedDurationInHours());
    }
}
