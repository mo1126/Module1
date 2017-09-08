package Bean;

/**
 * Created by Mo on 2017/9/6.
 */

public class Pindao {

    /**
     * isSelect : true
     * name : 头条
     */
    public String name;
    public boolean isSelect;


    public Pindao(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "Pindao{" +
                "name='" + name + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
