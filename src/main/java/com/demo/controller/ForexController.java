package com.demo.controller;

import com.demo.request.ApiResponse;
import com.demo.request.ForexRequest;
import com.demo.service.ForexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forex")
public class ForexController {
    @Autowired
    private ForexService forexService;

    @GetMapping("/history")
    public ResponseEntity<ApiResponse> getHistoricalRates(@RequestBody ForexRequest request) {
        return forexService.getRatesHistory(request);
    }
}
