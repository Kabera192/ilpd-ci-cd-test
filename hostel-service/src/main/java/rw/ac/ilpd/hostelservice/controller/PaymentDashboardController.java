package rw.ac.ilpd.hostelservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import rw.ac.ilpd.hostelservice.analytics.payment.*;
import rw.ac.ilpd.hostelservice.service.PaymentDashboardService;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Payment Dashboard", description = "APIs for payment analytics and dashboard data")
public class PaymentDashboardController {

    private final PaymentDashboardService dashboardService;

    @GetMapping("/payments")
    @Operation(
            summary = "Get comprehensive payment dashboard data",
            description = "Retrieves complete analytics including payment overview, document deletion metrics, revenue analytics, and trend data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard data retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaymentDashboardResponse> getPaymentDashboard() {
        log.info("Retrieving payment dashboard data");

        PaymentDashboardResponse dashboardData = dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboardData);
    }

    @GetMapping("/payments/overview")
    @Operation(
            summary = "Get payment overview statistics",
            description = "Retrieves high-level payment statistics and KPIs"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overview data retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaymentOverviewStats> getPaymentOverview() {
        log.info("Retrieving payment overview statistics");

        PaymentDashboardResponse dashboardData = dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboardData.getPaymentOverview());
    }

    @GetMapping("/documents/deletion-metrics")
    @Operation(
            summary = "Get document deletion rate metrics",
            description = "Retrieves detailed metrics about document deletion patterns, rates, and trends"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletion metrics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DocumentDeletionMetrics> getDocumentDeletionMetrics() {
        log.info("Retrieving document deletion metrics");

        PaymentDashboardResponse dashboardData = dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboardData.getDeletionMetrics());
    }

    @GetMapping("/revenue/analytics")
    @Operation(
            summary = "Get revenue analytics",
            description = "Retrieves comprehensive revenue analytics including trends, growth, and breakdown by payment method"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revenue analytics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RevenueAnalytics> getRevenueAnalytics() {
        log.info("Retrieving revenue analytics");

        PaymentDashboardResponse dashboardData = dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboardData.getRevenueAnalytics());
    }

    @GetMapping("/trends")
    @Operation(
            summary = "Get payment trend analytics",
            description = "Retrieves payment trends including daily patterns, growth rates, and peak usage times"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trend analytics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaymentTrendAnalytics> getPaymentTrends() {
        log.info("Retrieving payment trend analytics");

        PaymentDashboardResponse dashboardData = dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboardData.getTrendAnalytics());
    }

    @GetMapping("/risk-metrics")
    @Operation(
            summary = "Get risk and compliance metrics",
            description = "Retrieves risk-related metrics including high-value payments, suspicious activities, and compliance indicators"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Risk metrics retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RiskMetrics> getRiskMetrics() {
        log.info("Retrieving risk metrics");

        PaymentDashboardResponse dashboardData = dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboardData.getRiskMetrics());
    }

    @GetMapping("/recent-activities")
    @Operation(
            summary = "Get recent payment activities",
            description = "Retrieves the most recent payment-related activities and events"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recent activities retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<RecentPaymentActivity>> getRecentActivities() {
        log.info("Retrieving recent payment activities");

        PaymentDashboardResponse dashboardData = dashboardService.getDashboardData();

        return ResponseEntity.ok(dashboardData.getRecentActivities());
    }
}