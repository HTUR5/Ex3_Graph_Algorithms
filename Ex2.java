import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.WeightedGraph;
import api.algo;


/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        WeightedGraph ans = new WeightedGraph();
        algo object = new algo();
        object.init(ans);
        object.load(json_file);
        return object.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        WeightedGraph ans = new WeightedGraph();
        algo object = new algo();
        object.init(ans);
        object.load(json_file);
        return object;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        WeightedGraph directedWeightedGraphlmpl = (WeightedGraph)getGrapg(json_file);
       // GUI.showGraph(directedWeightedGraphlmpl);

    }

    public static void main(String[] args) {
        String path = args[0];
        runGUI(path);
    }
}