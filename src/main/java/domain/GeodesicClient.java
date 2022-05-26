package domain;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeodesicClient {
    public BigDecimal getDistanceInMilesBetweenTwoCoordinates(DecimalCoordinates startCoordinates, DecimalCoordinates endCoordinates) {
        GeodesicData result = Geodesic.WGS84.Inverse(
                startCoordinates.getLatitudeAsDouble(),
                startCoordinates.getLongitudeAsDouble(),
                endCoordinates.getLatitudeAsDouble(),
                endCoordinates.getLongitudeAsDouble());
        double oneMileInMeters = 1609.34;
        double distanceInMiles = result.s12 / oneMileInMeters;
        return BigDecimal.valueOf(distanceInMiles).setScale(2, RoundingMode.HALF_UP);
    }
}
