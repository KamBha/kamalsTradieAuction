package au.kamal.projectInformer.websockethandlers;

import au.kamal.projectInformer.dto.WinningBidDto;
import au.kamal.projectInformer.events.WinningBid;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
// Code based on https://programmerfriend.com/spring-boot-websocket/
public class ProjectCompletionHandler extends TextWebSocketHandler implements ApplicationListener<WinningBid>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectCompletionHandler.class);
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public ProjectCompletionHandler(ObjectMapper inObjectMapper)
    {
        objectMapper = inObjectMapper;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception
    {
        LOGGER.error("error occurred at sender " + session, throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
    {
        LOGGER.info(String.format("Session %s closed because of %s", session.getId(), status.getReason()));
        sessions.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception
    {
        LOGGER.info("Connected ... " + session.getId());
        sessions.put(
                session.getId(),
                new ConcurrentWebSocketSessionDecorator(
                        session,
                        100000,
                        Integer.MAX_VALUE));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
    {
        LOGGER.info("Handling message: {}", message);
    }

    @Override
    public void onApplicationEvent(WinningBid inWinningBid)
    {
        sessions.values().forEach(session -> {
            try
            {
                WinningBidDto winningBidDto = createWinningBidDto(inWinningBid);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(winningBidDto)));
            }
            catch (IOException ioe)
            {
                LOGGER.error("Could not send ", ioe);
            }
        });
    }

    private WinningBidDto createWinningBidDto(WinningBid inWinningBid)
    {
        return new WinningBidDto.Builder()
                    .withProjectSummary(inWinningBid.getProject().getSummary())
                    .withTradieName(inWinningBid.getWinningBid().getTradePerson().getName())
                    .withCustomerName(inWinningBid.getProject().getCustomer().getName())
                    .withWinningBidTotal(inWinningBid.getTotal())
                    .build();
    }
}
