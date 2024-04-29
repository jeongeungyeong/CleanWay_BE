package com.example.cleanway.domain.dto.route;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CleanRouteDto {
    private Long routeNumber;
    private Long userNumber;
    private Long courseNumber;
    private String routeName;
    private Double routeSlat;
    private Double routeSLng;
    private String routeSName;
    private Double routeDLat;
    private Double routeDLng;
    private String routeDName;
    private Double routeV1Lat;
    private Double routeV1Lng;
    private String routeV1Name;
    private Double routeV2Lat;
    private Double routeV2Lng;
    private String routeV2Name;
    private Double routeV3Lat;
    private Double routeV3Lng;
    private String routeV3Name;
}
