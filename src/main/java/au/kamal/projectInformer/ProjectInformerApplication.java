package au.kamal.projectInformer;

import au.kamal.projectInformer.model.Customer;
import au.kamal.projectInformer.model.Project;
import au.kamal.projectInformer.model.TradePerson;
import au.kamal.projectInformer.repository.CustomerRepository;
import au.kamal.projectInformer.repository.ProjectRepository;
import au.kamal.projectInformer.repository.TradePersonRepository;
import au.kamal.projectInformer.services.RandomProjectCreator;
import au.kamal.projectInformer.websockethandlers.ProjectCompletionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootApplication
@EnableScheduling
@EnableWebSocket
@EnableAsync
public class ProjectInformerApplication implements WebSocketConfigurer
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInformerApplication.class);
	private final ProjectCompletionHandler projectCompletionHandler;

	@Autowired
	public ProjectInformerApplication(ProjectCompletionHandler inProjectCompletionHandler)
	{
		projectCompletionHandler = inProjectCompletionHandler;
	}

	@Bean
	@Transactional
	public CommandLineRunner setupData(
								ProjectRepository inProjectRepository,
								CustomerRepository inCustomerRepository,
								TradePersonRepository inTradePersonRepository,
								RandomProjectCreator inDataLoader)
	{
		return (args) -> {
			LOGGER.info("Loading customers");
			List<Customer> customers = StreamSupport.stream(
											inCustomerRepository.saveAll(inDataLoader.createCustomers()).spliterator(),
											false)
										.collect(Collectors.toList());
			LOGGER.info("Loading tradies");
			List<TradePerson> tradies = StreamSupport.stream(
					inTradePersonRepository.saveAll(inDataLoader.createTradies()).spliterator(),
					false)
					.collect(Collectors.toList());
			LocalDateTime startOfAuctions = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
			List<Project> projectsToAdd = new ArrayList<>();
			LOGGER.info("Loading random projects for the next hour");
			for (int i = 0; i < 60; i++)
			{
				projectsToAdd.addAll(
						inDataLoader.createRandomNumberOfProjectsWithExpiry(
								startOfAuctions.plusMinutes(i),
								customers,
								tradies
						));
			}
			inProjectRepository.saveAll(projectsToAdd);
			LOGGER.info("Demo database setup complete");
		};
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry inWebSocketHandlerRegistry)
	{
		inWebSocketHandlerRegistry
				.addHandler(projectCompletionHandler, "/winningBids")
				.setAllowedOrigins("*");
	}

	public static void main(String[] args)
	{
		SpringApplication.run(ProjectInformerApplication.class, args);
	}
}
