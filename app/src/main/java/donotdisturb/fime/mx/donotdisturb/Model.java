package donotdisturb.fime.mx.donotdisturb;

/**
 * Created by eduardo on 29/02/16.
 */
public class Model {
    private int icon;
    private String label;

    public Model(String label) {
        this(-1,label);
    }
    public Model(int icon, String label) {
        super();
        this.icon = icon;
        this.label = label;
    }
    public int getIcon() {
        return this.icon;
    }
    public String getLabel() {
        return this.label;
    }

}
