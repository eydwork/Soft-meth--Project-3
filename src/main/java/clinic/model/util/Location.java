package clinic.model.util;
/*
This enumerator class stores the locations by city, county, and zip code.
@author Erika Dong, Emily Wong
*/
public enum Location { //enum for locations
    BRIDGEWATER ("Bridgewater","Somerset", "08807"),
    EDISON ("Edison", "Middlesex", "08817"),
    PISCATAWAY ("Piscataway", "Middlesex", "08854"),
    PRINCETON ("Princeton", "Mercer", "08542"),
    MORRISTOWN ("Morristown", "Morris", "07960"),
    CLARK ("Clark","Union", "07066");

    private final String county;
    private final String zip;

    Location(String city, String county, String zip){

        this.county=county;
        this.zip=zip;
    }


    public String getCity() {
        return this.name();
    }

    public String getCounty() {
        return county;
    }

    public String getZip() {
        return zip;
    }

    public int getCountyPriority() {
        switch (this.county) {
            case "Mercer":
                return 1;
            case "Middlesex":
                return 2;
            case "Morris":
                return 3;
            case "Somerset":
                return 4;
            case "Union":
                return 5;
            default:
                return Integer.MAX_VALUE; // If the county isn't listed, assign it the lowest priority
        }
    }
}

