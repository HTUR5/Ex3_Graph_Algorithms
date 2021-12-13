package api;

public class geo_location implements GeoLocation{

    private double x;
    private double y;
    private double z;

    public geo_location(double x,double y,double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public double distance(GeoLocation g) {
        double dis = Math.sqrt(Math.pow(this.x - g.x(),2) + Math.pow(this.y - g.y(),2) + Math.pow(this.z - g.z(),2));
        return dis;
    }
}
