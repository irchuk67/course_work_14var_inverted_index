import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
    private final Map<String, List<String>> vocabulary = new HashMap<>();

    public void pushToVocabulary(String word, String file){
        if(vocabulary.containsKey(word)){
            vocabulary.get(word).add(file);
        }else{
            List<String> files = new ArrayList<>();
            files.add(file);
            vocabulary.put(word, files);
        }
    }

    public List<String> getListOfFilesByKey(String word){
        return vocabulary.get(word);
    }
}
