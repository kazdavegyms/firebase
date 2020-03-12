package mcq.kasun.acer.firebase.model;

/**
 * Created by acer on 6/20/2018.
 */

public class paper {



        private String Name;
        private  String Image;

        public paper() {

        }

        public paper(String name, String image) {
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


