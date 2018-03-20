import java.util.*;
public class KeyWord implements Comparable<KeyWord>,Prioritizable {
    private String key;
    private int occur;
    public KeyWord(String word){
        occur=0;
        this.key=word;
    }
    @Override
    public int compareTo(KeyWord keyWord) {
        return this.key.compareTo(keyWord.getWord());
    }

    @Override
    public boolean equals(Object o){
        KeyWord comp=(KeyWord) o;
        if(key.equals(comp.getWord()))
            return true;
        return false;
    }

    @Override
    public int getPriority() {
        return this.occur;
    }


    public String getWord(){
        return this.key;
    }

    public void increment(){
        this.occur++;
    }
}
