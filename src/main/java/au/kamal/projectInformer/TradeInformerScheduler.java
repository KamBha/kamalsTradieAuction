package au.kamal.projectInformer;

import au.kamal.projectInformer.model.Project;
import au.kamal.projectInformer.repository.ProjectRepository;
import au.kamal.projectInformer.services.FinalisedProjectProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class TradeInformerScheduler
{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeInformerScheduler.class);
    private final ProjectRepository projectsRepository;
    private final FinalisedProjectProcessor processor;

    @Autowired
    public TradeInformerScheduler(ProjectRepository inProjectsRepository, FinalisedProjectProcessor inProcessor)
    {
        projectsRepository = inProjectsRepository;
        processor = inProcessor;
    }

    @Scheduled(cron = "0 * * * * *")
    public void retrieveCompletedProjects()
    {
        LocalDateTime auctionEnd = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Project> projects = projectsRepository.findByAuctionEnd(auctionEnd);
        LOGGER.info("Projects expired for " + DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        projects.forEach(processor::process);
    }
}
