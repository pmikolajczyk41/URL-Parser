package Database;

public class VisitedLink {
    private String address;

    VisitedLink(String address){
        this.address = address;
    }

    String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return getAddress();
    }
}
