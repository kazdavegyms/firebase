package mcq.kasun.acer.firebase.model;

/**
 * Created by acer on 3/26/2018.
 */

public class cat {

    private String Name;
    private  String Image;

    public cat() {

    }

    public cat(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
