package com.imaee.propinq.reports.services.implementations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.imaee.propinq.reports.services.interfaces.IMetabaseEmbedService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MetabaseEmbedService implements IMetabaseEmbedService {
    @Value("${metabase.site-url}")
    private String metabaseSiteUrl;

    @Value("${metabase.secret-key}")
    private String metabaseSecretKey;

    @Override
    public String getEmbedUrl(String type, Long resourceId, Map<String, Object> params) {
        System.out.println("Embed: generando JWT :: type=" + type + " resourceId=" + resourceId + " params=" + params);

        Map<String, Object> resource = new HashMap<>();
        resource.put(type, resourceId); // type = "question" o "dashboard"

        long expMillis = System.currentTimeMillis() + (10 * 60 * 1000); // 10 minutos

        String token = JWT.create()
                .withClaim("resource", resource)
                .withClaim("params", params)
                .withClaim("exp", expMillis / 1000) // en segundos UNIX
                .sign(Algorithm.HMAC256(metabaseSecretKey));
        System.out.println(token);
        return metabaseSiteUrl + "/embed/" + type + "/" + token + "#bordered=true&titled=true";
    }

}