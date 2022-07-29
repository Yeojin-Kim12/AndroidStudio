package my.cleanhajo_project;

public class Timer {
    int image;
    private String name;
    private String time;
    private String second;


    //리사이클러뷰에 넣기
    public Timer(int image, String name, String time, String second){
        this.image = image;
        this.name = name;
        this.time = time;
        this.second = second;
    }


    public int getImage() { return image; }
    public void setImage(int image) {this.image = image;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}

    public String getSecond() {return second;}
    public void setSecond(String second) {this.second = second;}

}