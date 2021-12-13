package api;

public class Edge implements EdgeData{

    private Node src;
    private Node dest;
    private String info;
    private int tag;
    private double weight;

    public Edge(Node src, Node dest, double weight, String info, int tag) {
        this.src = new Node(src);
        this.dest = new Node(dest);
        this.info = info;
        this.tag = tag;
        this.weight = weight;
    }

    @Override
    public int getSrc() {
        return this.src.getKey();
    }

    @Override
    public int getDest() {
        return this.dest.getKey();
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) { this.info = s;}

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
		String answer = "  {\n  \"src\": " + getSrc() + ",\n  \"w\": " + getWeight() +
				",\n  \"dest\": " + getDest() + "\n  }\n";
		return answer;
	}
}
