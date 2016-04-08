package donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.contacts;

/**
 * Created by sergio on 3/29/16.
 */
public class Contact {

    private String name;
    private String phone;
    private long _id;
    private boolean selected;


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }








}
