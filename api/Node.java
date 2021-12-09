package api;

public class Node implements NodeData{

    private int key;
    private geo_location location;
    private double weight;
    private String info;
    private int tag;

    public Node(int key, geo_location location, double weight, String info, int tag) {
        this.key = key;
        this.location = new geo_location(location.x(), location.y(), location.z()); //does i need here deep copy
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    public Node(Node n){
        this.key = n.key;
        this.location = new geo_location(n.location.x(), n.location.y(), n.location.z());
        this.weight = n.weight;
        this.info = n.info;
        this.tag = n.tag;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        //this.location = (geo_location) p;
        this.location.setX(p.x());
        this.location.setY(p.y());
        this.location.setZ(p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {}

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

	@Override
	public String toString() {
		String answer = "{\n  \"pos\": \"" + getLocation().x() + "," +
				getLocation().y() + "," +
				getLocation().z() + "\",\n  \"id\": " + getKey() + "\n  }\n";
		return answer;
	}
    
}
