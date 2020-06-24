package testingapp.workwithdata;
import javafx.collections.ObservableList;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile;
import org.ini4j.Wini;
import testingapp.Question;
import testingapp.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class IniFiles {

    public static void initMetadata(){
        File file = new File("quiz.ini");
        try{
            Wini ini = new Wini(file);
            ini.put("METADATA", "QUESTIONS_NUMBER", 0);
            ini.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateMetadata(){
        File file = new File("quiz.ini");
        try{
            Wini ini = new Wini(file);
            int i = ini.get("METADATA", "QUESTIONS_NUMBER", int.class);
            i++;
            ini.put("METADATA", "QUESTIONS_NUMBER", i);
            ini.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToIni(String question, String A, String B, String C, String D, String slug, boolean[] answers) {
        File file = new File("quiz.ini");
        try {
            Wini ini = new Wini(file);
            int numRights = 0, i, counter = 1;
            boolean hasMultiple = false;
            String[] letters = {"A", "B", "C", "D"};
            int qid = ini.get("METADATA", "QUESTIONS_NUMBER", int.class);
            qid++;
            ini.put(question, "QUEST_ID", qid);
            ini.put(question, "slug", slug);
            ini.put(question, "VariantA", A);
            ini.put(question, "VariantB", B);
            ini.put(question, "VariantC", C);
            ini.put(question, "VariantD", D);
            for (i = 0; i <= 3; i++) {
                if (answers[i]){
                    numRights++;
                    ini.put(question, "Right"+counter, letters[i]);
                    counter++;
                }
            }
            if(numRights>1) hasMultiple = true;
            ini.put(question, "numberOfRights", numRights);
            ini.put(question, "hasMultiple", hasMultiple);
            ini.store();
            updateMetadata();
            System.out.printf("Success!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void parseTests(ObservableList<Test> tests){
        File file = new File("tests.ini");
        try {
            Wini ini = new Wini(file);
            Collection<Profile.Section> list = ini.values();
            for(Profile.Section section : list){

                Test t = new Test(section.getName(), section.get("TEST_ID"), section.get("QUESTIONS"));
                tests.add(t);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void parseQuestions(ObservableList<Question> q, int[] arr, int questionNum){
        File file = new File("quiz.ini");
        try {
            Wini ini = new Wini(file);
            Collection<Profile.Section> list = ini.values();
            int j;
            boolean hm;
            String temp = "";
            boolean[] answers = new boolean[]{false, false, false, false};
        for (int i = 0; i <= questionNum; i++) {
                for (Profile.Section s : list) {
                    if (arr[i] != 0 && s.get("QUEST_ID") != null && !"".equals(s.get("QUEST_ID")) && Integer.parseInt(s.get("QUEST_ID")) == arr[i]) {
                        j = Integer.parseInt(s.get("numberOfRights"));
                        for (int k = 1; i <= j; i++) {

                            if (s.get("Right" + k) != null){
                                temp = s.get("Right" + k);
                                if (temp.equals("A")){
                                    answers[0] = true;
                                }
                                else if (temp.equals("B")) {
                                    answers[1] = true;
                                }
                                else if (temp.equals("C")){
                                    answers[2] = true;
                                }
                                else if (temp.equals("D")) {
                                    answers[3] = true;
                                }
                            }
                        }
                        hm = Boolean.parseBoolean(s.get("hasMultiple"));
                        Question question = new Question(s.getName(), s.get("VariantA"), s.get("VariantB"), s.get("VariantC"), s.get("VariantD"), s.get("slug"), answers, hm);
                        q.add(question);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
