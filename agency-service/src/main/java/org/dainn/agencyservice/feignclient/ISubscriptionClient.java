package org.dainn.agencyservice.feignclient;

import org.dainn.agencyservice.dto.SubscriptionResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "subscription-service", path = "/api/subscriptions")
public interface ISubscriptionClient {
    @GetMapping("/agency/{id}")
    List<SubscriptionResp> getByAgencyId(@PathVariable String id);
}
