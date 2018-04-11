import java.util.ArrayList;

public class Node {
    private Player current;
    private Integer value;
    private Fraction fraction;
    private ArrayList<Node> childs = new ArrayList<>();
    Node(Player current, Integer value) {
        this.current = current;
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }



    public void setChild(Node child,Node c) {
        childs.add(child);
        childs.add(c);
    }

    public ArrayList<Node> getChilds() {
        return childs;
    }

    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    public Fraction getFraction() {
        return fraction;
    }
}
