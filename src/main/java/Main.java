

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.SceneObject;

import java.util.ArrayList;

@ScriptManifest(author = "Blade", category = Category.WOODCUTTING, description = "Woodcutting Willows and drops", name = "Woodcutting Willows", servers = { "2006Scape" }, version = 1)
public class Main extends Script {
    public ArrayList<Strategy> strategies = new ArrayList<Strategy>();
    private static final int[] LOG_ID = {1519,1520};
    private static final int[] TREE_IDS = {5553,5552,1308};
    @Override
    public boolean onExecute() {
        strategies.add(new Chopping());
        strategies.add(new Drop());
        provide(strategies);
        return true;
    }

    @Override
    public void onFinish() {

    }
    private class Chopping implements Strategy {

        SceneObject tree; // local variable to store the tree.

        @Override
        public boolean activate() {
            tree = tree(); // set the local Variable
            //Check if we need to chop the tree
                return !Inventory.isFull() && Players.getMyPlayer().getAnimation() == -1 && tree != null;
        }
        @Override
        public void execute() {
            //Chop the tree
            if(Players.getMyPlayer().getAnimation() != 875){
                SceneObjects.getNearest(1308);
                tree.interact(646);
                Time.sleep(15000);

            }else{
            if(Players.getMyPlayer().getAnimation() == 875)
                Time.sleep(5000);
                }

            //tree.interact(Menu.);
            //Wait for the Player to chop the Tree
            Time.sleep(() -> Players.getMyPlayer().getAnimation() == -1, 3000);
        }
    }
    private SceneObject tree() {
        for (SceneObject tree : SceneObjects.getNearest(TREE_IDS)) {
            //Check if the Object is around.
            if (tree != null) {
                //Return the Tree Object.
                return tree;
            }
        }
        return null;
    }
    private static class Drop implements Strategy {


        @Override
        public boolean activate() {

            return Inventory.isFull()&&Players.getMyPlayer().getAnimation()==-1;
        }

        @Override
        public void execute() {
            // Loop through all Inventory Items and Drop the once with Log ID.
            for (Item log : Inventory.getItems(LOG_ID)) {
                //Check if Log Exists
                if (log != null) {
                    //Drop the Log.
                    log.drop();
                    //Using a Static Sleep here for Tutorial sake, You can use a Dynamic one.
                    Time.sleep(1000);
                }
            }
        }
    }

}
