package com.plantcare.controller;

import com.plantcare.dto.PlantResponse;
import com.plantcare.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "Reporting and statistics")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/needs-watering")
    @Operation(summary = "Plants whose last watering exceeded the recommended interval")
    public Page<PlantResponse> needsWatering(Pageable pageable) {
        return reportService.getNeedsWatering(pageable);
    }

    @GetMapping("/summary")
    @Operation(summary = "Summary: total plants and watering events in last 7 days")
    public Map<String, Object> summary() {
        return reportService.getSummary();
    }
}