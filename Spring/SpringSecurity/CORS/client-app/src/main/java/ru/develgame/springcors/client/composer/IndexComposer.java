package ru.develgame.springcors.client.composer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import ru.develgame.springcors.client.dto.HelloRequestDto;
import ru.develgame.springcors.client.dto.HelloResponseDto;

@VariableResolver(DelegatingVariableResolver.class)
public class IndexComposer extends SelectorComposer<Div> {
    @WireVariable
    private RestTemplate restTemplate;

    @Wire
    private Label getResponseLabel;
    @Wire
    private Textbox nameTextBox;
    @Wire
    private Label postResponseLabel;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Listen("onClick = #getButton")
    public void getRequestButtonOnClick() {
        ResponseEntity<HelloResponseDto> response = restTemplate.getForEntity("http://localhost:8080/hello", HelloResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            Messagebox.show("Error " + response.getStatusCode(), "Request error", 0,  Messagebox.ERROR);
            return;
        }

        try {
            getResponseLabel.setValue(objectMapper.writeValueAsString(response.getBody()));
        } catch (JsonProcessingException e) {
            Messagebox.show("Error " + e.getMessage(), "Parse error", 0,  Messagebox.ERROR);
            return;
        }
    }

    @Listen("onClick = #postRequestButton")
    public void postRequestButtonOnClick() {
        HttpEntity<HelloRequestDto> request = new HttpEntity<>(HelloRequestDto.builder().name(nameTextBox.getValue()).build());
        ResponseEntity<HelloResponseDto> response = restTemplate.postForEntity("http://localhost:8080/hello", request, HelloResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            Messagebox.show("Error " + response.getStatusCode(), "Request error", 0,  Messagebox.ERROR);
            return;
        }

        try {
            postResponseLabel.setValue(objectMapper.writeValueAsString(response.getBody()));
        } catch (JsonProcessingException e) {
            Messagebox.show("Error " + e.getMessage(), "Parse error", 0,  Messagebox.ERROR);
            return;
        }
    }
}
