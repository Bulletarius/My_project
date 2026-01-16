public class Location {
    private String name;
    private String dialogueFileBefore;
    private String dialogueFileAfter;

    public Location(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialogueFileBefore() {
        return dialogueFileBefore;
    }

    public void setDialogueFileBefore(String dialogueFileBefore) {
        this.dialogueFileBefore = dialogueFileBefore;
    }

    public String getDialogueFileAfter() {
        return dialogueFileAfter;
    }

    public void setDialogueFileAfter(String dialogueFileAfter) {
        this.dialogueFileAfter = dialogueFileAfter;
    }
}
