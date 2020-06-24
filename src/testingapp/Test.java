package testingapp;

public class Test {
    private String name;
    private int id;
    private int[] questionsIncluded;
    private int questionsNum;

    public Test(){
        name = "";
        id = 0;
        int[] questionsIncluded = {};
        questionsNum = 0;
    }

    public int getQuestionsNum() {
        return questionsNum;
    }

    public void setQuestionsNum(int questionsNum) {
        this.questionsNum = questionsNum;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int[] getQuestionsIncluded() {
        return questionsIncluded;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestionsIncluded(int[] questionsIncluded) {
        this.questionsIncluded = questionsIncluded;
    }

    public Test(String name, String id, String questionsIncluded){
        this.name = name;
        String[] temp = questionsIncluded.split(",");
        this.id = Integer.parseInt(id);
        this.questionsIncluded = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 0; i <= temp.length-1; i++){
            this.questionsIncluded[i] = Integer.parseInt(temp[i]);
            questionsNum++;
        }
    }



}
