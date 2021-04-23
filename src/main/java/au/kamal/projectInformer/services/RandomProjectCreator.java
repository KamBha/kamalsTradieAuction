package au.kamal.projectInformer.services;

import au.kamal.projectInformer.model.Bid;
import au.kamal.projectInformer.model.Customer;
import au.kamal.projectInformer.model.Project;
import au.kamal.projectInformer.model.TradePerson;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RandomProjectCreator
{
    private static final int MAX_CUSTOMERS = 50000;
    private static final int MAX_TRADIES = 60000;
    private final Random random = new Random();

    public List<Customer> createCustomers()
    {
        return IntStream.range(0, MAX_CUSTOMERS)
                        .mapToObj(idx -> new Customer("Sam Customer" + idx))
                        .collect(Collectors.toList());
    }

    public List<TradePerson> createTradies()
    {
        return IntStream.range(0, MAX_TRADIES)
                .mapToObj(idx -> new TradePerson("Joe Tradie" + idx))
                .collect(Collectors.toList());
    }

    public List<Project> createRandomNumberOfProjectsWithExpiry(
                                LocalDateTime inExpiryDate,
                                List<Customer> inCustomers,
                                List<TradePerson> inTradies)
    {
        List<Project> projects = new ArrayList<>();
        for (int projectId = 0; projectId < random.nextInt(100); projectId++)
        {
            Project newProject = new Project();
            Customer customer = inCustomers.get(random.nextInt(MAX_CUSTOMERS));

            newProject.setAuctionEnd(inExpiryDate);
            newProject.setExpectedDurationInHours(10);
            newProject.setSummary("Some Project " + projectId);
            newProject.setDescription("This is the description of the job " + projectId);
            newProject.setCustomer(customer);
            newProject.setBids(createBids(newProject, inTradies.get(random.nextInt(MAX_TRADIES))));
            projects.add(newProject);
        }
        return projects;
    }

    private List<Bid> createBids(Project inProject, TradePerson inTradePerson)
    {
        List<Bid> bids = new ArrayList<>();
        for (int bidId = 0; bidId < random.nextInt(50); bidId++)
        {
            Bid bid = new Bid();
            if (random.nextBoolean())
            {
                bid.setPerHourRate(new BigDecimal(random.nextInt(100)));
            }
            else
            {
                bid.setTotalAmount(new BigDecimal(random.nextInt(1000)));
            }
            bid.setTradePerson(inTradePerson);
            bid.setProject(inProject);
            bids.add(bid);
        }
        return bids;
    }
}
