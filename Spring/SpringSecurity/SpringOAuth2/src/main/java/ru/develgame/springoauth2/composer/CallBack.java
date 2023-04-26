package ru.develgame.springoauth2.composer;

import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.client.RestTemplate;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import ru.develgame.springoauth2.domain.JwtResponse;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CallBack extends SelectorComposer<Div> {
    @Wire
    private Label accessToken;

    @Override
    public void doAfterCompose(Div comp) throws Exception {
        super.doAfterCompose(comp);

        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal.getIdToken().getAccessTokenHash();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + principal.getIdToken().getTokenValue());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JwtResponse> exchange = new RestTemplate().exchange("http://localhost:7778/api/auth/google", HttpMethod.GET, entity, JwtResponse.class);
        accessToken.setValue(exchange.getBody().getAccessToken());
    }
}
