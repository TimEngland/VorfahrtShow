package application;

import javafx.scene.Node;
import javafx.scene.Parent;

public class FXNodeFinder {
	public static Node findNodeById(Parent parent, String id) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (id.equals(node.getId())) {
                return node;
            }
            if (node instanceof Parent) {
                Node found = findNodeById((Parent) node, id);
                if (found != null) return found;
            }
        }
        return null; // Not found
    }
}
