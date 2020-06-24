package testingapp;

import testingapp.workwithdata.IniFiles;

public class Question {
    private String questionText;
    private String A, B, C, D, slug;
    private boolean[] answers;
    private boolean hasMultiple;

    public Question(){
        this.questionText = "";
        this.A = "";
        this.B = "";
        this.C = "";
        this.D = "";
        this.slug = "";
        this.answers = new boolean[]{false, false, false, false};
        this.hasMultiple = false;
    }

    public Question(String questionText, String A, String B, String C, String D, String slug, boolean[] answers, boolean hasMultiple){
        this.questionText = questionText;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.slug = slug;
        this.answers = answers;
        this.hasMultiple = hasMultiple;
    }



    public void writeToIni(){
        IniFiles.addToIni(this.questionText, this.A, this.B, this.C, this.D, this.slug, this.answers);
    }

    public boolean[] getAnswers() {
        return answers;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getSlug() {
        return slug;
    }

    public boolean getHM(){
        return hasMultiple;
    }
}
