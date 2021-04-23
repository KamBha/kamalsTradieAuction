package au.kamal.projectInformer.services;

import static org.mockito.Mockito.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import au.kamal.projectInformer.events.WinningBid;
import au.kamal.projectInformer.model.Bid;
import au.kamal.projectInformer.model.Customer;
import au.kamal.projectInformer.model.Project;
import au.kamal.projectInformer.model.TradePerson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class FinalisedProjectProcessorTest
{
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void processShouldFireWinningBidWithTotalCalculatedFromHours()
    {
        Project project = createProjectShell( 20);
        Bid bid = createBid(null, "1000.00");
        project.setBids(Arrays.asList(bid));

        create().process(project);

        ArgumentCaptor<WinningBid> captor = ArgumentCaptor.forClass(WinningBid.class);
        verify(applicationEventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().getTotal().toString(), is("20000.00"));
    }

    @Test
    void processShouldFireWinningBidWithTotal()
    {
        Project project = createProjectShell( 20);
        Bid bid = createBid("20000.00", null);
        project.setBids(Arrays.asList(bid));

        create().process(project);

        ArgumentCaptor<WinningBid> captor = ArgumentCaptor.forClass(WinningBid.class);
        verify(applicationEventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().getTotal().toString(), is("20000.00"));
    }

    @Test
    void processShouldFireWinningBidWithMultipleBids()
    {
        Project project = createProjectShell( 20);
        project.setBids(
                Arrays.asList(
                        createBid("20000.00", null),
                        createBid("12000.00", null),
                        createBid(null, "500.00")
                             ));

        create().process(project);

        ArgumentCaptor<WinningBid> captor = ArgumentCaptor.forClass(WinningBid.class);
        verify(applicationEventPublisher).publishEvent(captor.capture());
        assertThat(captor.getValue().getTotal().toString(), is("10000.00"));
    }

    private Project createProjectShell(int inHours)
    {
        Project project = new Project();
        project.setCustomer(new Customer("Name"));
        project.setSummary("Summary");
        project.setExpectedDurationInHours(inHours);
        return project;
    }

    private Bid createBid(String inTotal, String inHourlyRate)
    {
        Bid bid = new Bid();
        bid.setTradePerson(new TradePerson("Tradie"));
        bid.setPerHourRate(inHourlyRate != null ? new BigDecimal(inHourlyRate) : null);
        bid.setTotalAmount(inTotal != null ? new BigDecimal(inTotal) : null);

        return bid;
    }

    private FinalisedProjectProcessor create()
    {
        return new FinalisedProjectProcessor(applicationEventPublisher);
    }
}