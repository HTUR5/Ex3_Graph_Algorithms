import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.Node;
import api.NodeData;
import api.algo;
import api.geo_location;

public class MainWindow extends JFrame {
    static final int WINDOW_HEIGHT = Window.HEIGHT; //500
    static final int WINDOW_WIDTH = Window.WIDTH; // 800;
    static final int NODE_RADIUS = 6;
    static final int MARGIN = 30;
    static final int ARROW_WIDTH = 4;
    static final int ARROW_HEIGHT = 4;
    private algo graph = new algo();
    private JPanel graphPanel;
    private double minX = 0, minY = 0, maxX = 100, maxY = 100;
    static final int ADD_NODE_MODE = 1;
    static final int REMOVE_NODE_MODE = 2;
    static final int ADD_EDGE_MODE = 3;
    static final int REMOVE_EDGE_MODE = 4;
    static final int SHORTEST_PATH_MODE = 5;
    static final int SHORTEST_PATH_DIST_MODE = 6;
    static final int START_TSP_MODE = 7;
    static final int SHOW_NODE_DETAILS_MODE = 8;
    static final int SHOW_EDGE_DETAILS_MODE = 9;
    private int mode;
    private NodeData srcNodeToAddEdge;
    private NodeData centerNode;
    private List<NodeData> specialNodes;
    public MainWindow() {
        setTitle("Graphs");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(12, 1));
        ButtonGroup rbGroup = new ButtonGroup();

        JRadioButton addNodeButton = new JRadioButton("Add Node");
        rbGroup.add(addNodeButton);
        sidePanel.add(addNodeButton);
        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = ADD_NODE_MODE;
            }
        });

        JRadioButton removeNodeButton = new JRadioButton("Remove Node");
        rbGroup.add(removeNodeButton);
        sidePanel.add(removeNodeButton);
        removeNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = REMOVE_NODE_MODE;
            }
        });

        JRadioButton addEdgeButton = new JRadioButton("Add Edge");
        rbGroup.add(addEdgeButton);
        sidePanel.add(addEdgeButton);
        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = ADD_EDGE_MODE;
                srcNodeToAddEdge = null;
            }
        });

        JRadioButton removeEdgeButton = new JRadioButton("Remove Edge");
        rbGroup.add(removeEdgeButton);
        sidePanel.add(removeEdgeButton);
        removeEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = REMOVE_EDGE_MODE;
                srcNodeToAddEdge = null;
            }
        });

        JRadioButton shortestPathButton = new JRadioButton("Shortest Path");
        rbGroup.add(shortestPathButton);
        sidePanel.add(shortestPathButton);
        shortestPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = SHORTEST_PATH_MODE;
                srcNodeToAddEdge = null;
            }
        });

        JRadioButton shortestPathDistButton = new JRadioButton("Shortest Path Dist");
        rbGroup.add(shortestPathDistButton);
        sidePanel.add(shortestPathDistButton);
        shortestPathDistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = SHORTEST_PATH_DIST_MODE;
                srcNodeToAddEdge = null;
            }
        });

        JRadioButton showNodeDetailsButton = new JRadioButton("Show Node Details");
        rbGroup.add(showNodeDetailsButton);
        sidePanel.add(showNodeDetailsButton);
        showNodeDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = SHOW_NODE_DETAILS_MODE;
            }
        });

        JRadioButton showEdgeDetailsButton = new JRadioButton("Show Edge Details");
        rbGroup.add(showEdgeDetailsButton);
        sidePanel.add(showEdgeDetailsButton);
        showEdgeDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = SHOW_EDGE_DETAILS_MODE;
                srcNodeToAddEdge = null;
            }
        });

        JButton centerButton = new JButton("Center");
        sidePanel.add(centerButton);
        centerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                center();
            }
        });

        JButton isConnectedButton = new JButton("Is Connected");
        sidePanel.add(isConnectedButton);
        isConnectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConnected();
            }
        });

        JButton startTspButton = new JButton("Start TSP");
        sidePanel.add(startTspButton);
        startTspButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTsp();
            }
        });
        JButton tspButton = new JButton("End TSP");
        sidePanel.add(tspButton);
        tspButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tsp();
            }
        });

        graphPanel = new JPanel() {

        @Override
        protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());
        DirectedWeightedGraph dg = graph.getGraph();
        double width = getSize().getWidth() - 2 * MARGIN;
        double height = getSize().getHeight() - 2 * MARGIN;
        EdgeData edge;
        NodeData srcNode, dstNode;
        Iterator<EdgeData> edgesIter = dg.edgeIter();
        double srcX, srcY, dstX, dstY;
        g.setColor(Color.BLACK);
        while (edgesIter.hasNext()) {
                    edge = edgesIter.next();
                    srcNode = dg.getNode(edge.getSrc());
                    dstNode = dg.getNode(edge.getDest());
                    srcX = srcNode.getLocation().x();
                    srcX = MARGIN + (srcX - minX) * width / (maxX - minX);
                    srcY = srcNode.getLocation().y();
                    srcY = MARGIN + (srcY - minY) * height / (maxY - minY);
                    dstX = dstNode.getLocation().x();
                    dstX = MARGIN + (dstX - minX) * width / (maxX - minX);
                    dstY = dstNode.getLocation().y();
                    dstY = MARGIN + (dstY - minY) * height / (maxY - minY);
                    g.drawLine((int)srcX, (int)srcY, (int)dstX, (int)dstY);
                    // draw the head of the arrow
                    double dx = dstX - srcX, dy = dstY - srcY;
                    double D = Math.sqrt(dx*dx + dy*dy);
                    double xm = D - ARROW_WIDTH, xn = xm, ym = ARROW_HEIGHT, yn = -ARROW_HEIGHT, x;
                    double sin = dy / D, cos = dx / D;
                    x = xm*cos - ym*sin + srcX;
                    ym = xm*sin + ym*cos + srcY;
                    xm = x;
                    x = xn*cos - yn*sin + srcX;
                    yn = xn*sin + yn*cos + srcY;
                    xn = x;

                    int[] xpoints = {(int) dstX, (int) xm, (int) xn};
                    int[] ypoints = {(int) dstY, (int) ym, (int) yn};

                    g.fillPolygon(xpoints, ypoints, 3);
                }
        g.setColor(Color.RED);
        NodeData node;
        double x, y;
        Iterator<NodeData> nodesIter = dg.nodeIter();
        while (nodesIter.hasNext()) {
                    node = nodesIter.next();
                    x = node.getLocation().x();
                    y = node.getLocation().y();
                    x = MARGIN + (x - minX) * width / (maxX - minX) - NODE_RADIUS / 2;
                    y = MARGIN + (y - minY) * height / (maxY - minY) - NODE_RADIUS / 2;
                    if (node == centerNode)
                        g.setColor(Color.BLUE);
                    if ((specialNodes != null) && (specialNodes.contains(node))) {
                        g.setColor(Color.BLUE);
                        g.drawString("" + (specialNodes.indexOf(node) + 1), (int)(x + NODE_RADIUS + 1), (int)y);
                    }
                    g.fillOval((int)x, (int)y, NODE_RADIUS, NODE_RADIUS);
                    g.setColor(Color.RED);
                }
            }
        };

        graphPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (mode == ADD_NODE_MODE) {
                    addNode(e);
                }
                else if (mode == REMOVE_NODE_MODE) {
                    removeNode(e);
                }
                else if (mode == ADD_EDGE_MODE) {
                    addEdge(e);
                }
                else if (mode == REMOVE_EDGE_MODE) {
                    removeEdge(e);
                }
                else if (mode == SHORTEST_PATH_MODE) {
                    shortestPath(e);
                }
                else if (mode == SHORTEST_PATH_DIST_MODE) {
                    shortestPathDist(e);
                }
                else if (mode == START_TSP_MODE) {
                    collectForTSP(e);
                }
                else if (mode == SHOW_NODE_DETAILS_MODE) {
                    showNodeDetails(e);
                }
                else if (mode == SHOW_EDGE_DETAILS_MODE) {
                    showEdgeDetails(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
        add(BorderLayout.WEST, sidePanel);
        add(BorderLayout.CENTER, graphPanel);
        sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("Open");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(menuItem);
        setJMenuBar(menuBar);
    }
    private int getNextID() {
        int id = 0;
        DirectedWeightedGraph dg = graph.getGraph();
        NodeData node;
        Iterator<NodeData> nodesIter = dg.nodeIter();
        while (nodesIter.hasNext()) {
            node = nodesIter.next();
            if (node.getKey() > id)
                id = node.getKey();
        }
        return id + 1;
    }
    private void addNode(MouseEvent e) {
        double width = graphPanel.getSize().getWidth() - 2 * MARGIN;
        double height = graphPanel.getSize().getHeight() - 2 * MARGIN;
        double x = e.getPoint().x;
        double y = e.getPoint().y;
        x = (x - MARGIN + NODE_RADIUS / 2) * (maxX - minX) / width + minX;
        y = (y - MARGIN + NODE_RADIUS / 2) * (maxY - minY) / height + minY;
        int id = getNextID();
        geo_location p = new geo_location(x, y, 0);
        Node node = new Node(id, p, 0, "", 0);
        graph.getGraph().addNode(node);
        graphPanel.repaint();
    }
    private NodeData select(MouseEvent e) {
        double width = graphPanel.getSize().getWidth() - 2 * MARGIN;
        double height = graphPanel.getSize().getHeight() - 2 * MARGIN;
        double x = e.getPoint().x;
        double y = e.getPoint().y;

        NodeData node;
        DirectedWeightedGraph dg = graph.getGraph();
        Iterator<NodeData> nodesIter = dg.nodeIter();
        while (nodesIter.hasNext()) {
            node = nodesIter.next();
            double nodeX = node.getLocation().x();
            double nodeY = node.getLocation().y();
            nodeX = MARGIN + (nodeX - minX) * width / (maxX - minX) - NODE_RADIUS / 2;
            nodeY = MARGIN + (nodeY - minY) * height / (maxY - minY) - NODE_RADIUS / 2;
            if (Math.sqrt((x - nodeX) * (x - nodeX) + (y - nodeY) * (y - nodeY)) < NODE_RADIUS) {
                return node;
            }
        }
        return null;
    }
    private void removeNode(MouseEvent e) {
        NodeData node = select(e);
        if (node != null) {
            DirectedWeightedGraph dg = graph.getGraph();
            dg.removeNode(node.getKey());
            graphPanel.repaint();
        }
    }
    private void addEdge(MouseEvent e) {
        NodeData node = select(e);
        if (srcNodeToAddEdge == null) {
            srcNodeToAddEdge = node;
            return;
        }
        if (node != null) {
            DirectedWeightedGraph dg = graph.getGraph();
            try {
                double w = Double.parseDouble(JOptionPane.showInputDialog(null," Enter Weight: "));
                dg.connect(srcNodeToAddEdge.getKey(), node.getKey(), w);
                srcNodeToAddEdge = null;
                graphPanel.repaint();
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        null, "Bad number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void showNodeDetails(MouseEvent e) {
        NodeData node = select(e);
        if (node != null) {
            String info = "Node key: " + node.getKey() + " In Position: " +
                    node.getLocation().x() + "," + node.getLocation().y();
            JOptionPane.showMessageDialog(
                    null,
                    info,
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void showEdgeDetails(MouseEvent e) {
        NodeData node = select(e);
        if (srcNodeToAddEdge == null) {
            srcNodeToAddEdge = node;
            return;
        }
        if (node != null) {
            DirectedWeightedGraph dg = graph.getGraph();
            EdgeData edge = dg.getEdge(srcNodeToAddEdge.getKey(), node.getKey());
            if (edge != null) {
                String info = "Edge from: " + edge.getSrc() + " To: " + edge.getDest() + " Weight: " + edge.getWeight();
                JOptionPane.showMessageDialog(
                        null,
                        info,
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            srcNodeToAddEdge = null;
        }
    }
    private void removeEdge(MouseEvent e) {
        NodeData node = select(e);
        if (srcNodeToAddEdge == null) {
            srcNodeToAddEdge = node;
            return;
        }
        if (node != null) {
            DirectedWeightedGraph dg = graph.getGraph();
            dg.removeEdge(srcNodeToAddEdge.getKey(), node.getKey());
            srcNodeToAddEdge = null;
            graphPanel.repaint();
        }
    }
    private void saveFile() {
        FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.SAVE);
        fd.setFile("*.json");
        fd.setVisible(true);
        String filename = fd.getFile();
        if (filename == null) {
            return;
        }
        graph.save(filename);
    }
    private void openFile() {
        FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
        fd.setFile("*.json");
        fd.setVisible(true);
        String filename = fd.getFile();;
        if (filename == null) {
            return;
        }
        filename = fd.getDirectory()+"\\"+fd.getFile();
        graph.load(filename);
        DirectedWeightedGraph dg = graph.getGraph();
        NodeData node;
        minX = -1;
        minY = -1;
        maxX = -1;
        maxY = -1;
        double x, y;
        for (int i = 0; i < dg.nodeSize(); i++) {
            node = dg.getNode(i);
            x = node.getLocation().x();
            y = node.getLocation().y();
            if (minX == -1)
                minX = x;
            else if (x < minX)
                minX = x;
            if (minY == -1)
                minY = y;
            else if (y < minY)
                minY = y;
            if (maxX == -1)
                maxX = x;
            else if (x > maxX)
                maxX = x;
            if (maxY == -1)
                maxY = y;
            else if (y > maxY)
                maxY = y;
        }
        graphPanel.repaint();
    }
    private void center() {
        centerNode = graph.center();
        if (centerNode != null) {
            Thread thread = new Thread() {

                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    centerNode = null;
                    graphPanel.repaint();
                }
            };
            thread.start();
        }
        graphPanel.repaint();
    }
    private void isConnected() {
        if (graph.isConnected()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Connected",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(
                    null,
                    "Not Connected",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void shortestPath(MouseEvent e) {
        NodeData node = select(e);
        if (srcNodeToAddEdge == null) {
            srcNodeToAddEdge = node;
            return;
        }
        if (node != null) {
            List<NodeData> lst = graph.shortestPath(srcNodeToAddEdge.getKey(), node.getKey());
            if (lst != null) {
                specialNodes = lst;
                Thread thread = new Thread() {

                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                        specialNodes = null;
                        graphPanel.repaint();
                    }
                };
                thread.start();
            }
            srcNodeToAddEdge = null;
            graphPanel.repaint();
        }
    }
    private void shortestPathDist(MouseEvent e) {
        NodeData node = select(e);
        if (srcNodeToAddEdge == null) {
            srcNodeToAddEdge = node;
            return;
        }
        if (node != null) {
            double dist = graph.shortestPathDist(srcNodeToAddEdge.getKey(), node.getKey());
            srcNodeToAddEdge = null;
            JOptionPane.showMessageDialog(
                    null,
                    "The Shortest Path Dist is: " + dist,
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void collectForTSP(MouseEvent e) {
        NodeData node = select(e);
        if ((node != null) && (!specialNodes.contains(node))) {
            specialNodes.add(node);
            graphPanel.repaint();
        }
    }
    private void startTsp() {
        specialNodes = new ArrayList<NodeData>();
        mode = START_TSP_MODE;
    }
    private void tsp() {
        List<NodeData> tspResult = graph.tsp(specialNodes);
        if (tspResult != null) {
            specialNodes = tspResult;
            Thread thread = new Thread() {

                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                    specialNodes = null;
                    graphPanel.repaint();
                }
            };
            thread.start();
        }
        else {
            specialNodes = null;
        }
    }
}
