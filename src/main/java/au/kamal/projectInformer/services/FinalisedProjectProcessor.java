package au.kamal.projectInformer.services;

import au.kamal.projectInformer.events.WinningBid;
import au.kamal.projectInformer.model.Bid;
import au.kamal.projectInformer.model.Project;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class FinalisedProjectProcessor
{
    private final ApplicationEventPublisher applicationEventPublisher;

    public FinalisedProjectProcessor(ApplicationEventPublisher inApplicationEventPublisher)
    {
        applicationEventPublisher = inApplicationEventPublisher;
    }

    @Async
    public void process(Project inProject)
    {
        Bid lowestBid = null;
        if (inProject.getBids() == null || inProject.getBids().size() == 0)
            return;
        for (Bid bid : inProject.getBids())
        {
            if (lowestBid == null || isLowerBid(inProject, lowestBid, bid))
            {
                lowestBid = bid;
            }
        }
        WinningBid winningBid = new WinningBid(this, inProject, lowestBid);
        applicationEventPublisher.publishEvent(winningBid);
    }

    private boolean isLowerBid(Project inProject, Bid inLowestBid, Bid inCurrentBid)
    {
        return inCurrentBid.calculateTotal(inProject.getExpectedDurationInHours())
                    .compareTo(inLowestBid.calculateTotal(inProject.getExpectedDurationInHours())) < 0;
    }
}
