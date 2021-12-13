package api;

public class Node implements NodeData{

    private int key;
    private geo_location location;
    private double weight;
    private String info;
    private int tag;

    public Node(int key, geo_location location, double weight, String info, int tag) {
        this.key = key;
        if(location == null) {
            this.location = null;
        } else {
            this.location = new geo_location(location.x(), location.y(), location.z());
        }
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    public Node(Node n){
        this.key = n.key;
        if(n.location == null) {
            this.location = null;
        } else {
            this.location = new geo_location(n.location.x(), n.location.y(), n.location.z());
        }
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
        if(this.location != null) {return this.location;}
        return null;
    }

    @Override
    public void setLocation(GeoLocation p) {
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
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

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
