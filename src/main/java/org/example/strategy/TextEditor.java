package org.example.strategy;

public class TextEditor {

    TextFormatter formatter;

    public TextEditor(TextFormatter formatter) {
        this.formatter = formatter;
    }

    public void printFormattedText(String text){
        System.out.println(formatter.format(text));
    }

    static void main() {
        String text = """
                Strategy (from Greek στρατηγία stratēgia, "troop leadership; office of general,
                command, generalship" ) is a general plan to achieve one or more
                long-term or overall goals under conditions of uncertainty
                """;
     //   TextEditor te = new TextEditor(new EveryWordUpperCaseFormatter());
     //   TextEditor te = new TextEditor(s->s.toUpperCase());
        TextEditor te = new TextEditor(String::toUpperCase);
        te.printFormattedText(text);
    }
}
