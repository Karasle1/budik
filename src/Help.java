import java.awt.*;

import static java.awt.Color.white;

public class Help {

    private final String text;
    private final Color backroundColor;
    private final String title;


    public Help() {

        text = " Předmět studia: PGRF II \t"
                + " \n"
                + " Autor: Leoš Karásek\n"
                + "Odevzdáno: 25.6.2019\n"
                + "\n\n\n"
                + " Projekt OpenGL zobrazuje budík. Budík ukazuje aktuální čas, který je navázaný na sytémový čas počítače. \n"
                + " Ciferník, tělo budíku a ručičky je řešeno mapováním textur. Dále je možné nastavit buzení. Stiskem levého tlačitjka a tahem myši\n"
                + " v prostoru ciferníku se uvede do pohybu červená ručička, kterou se nastavuje čas buzení\n"
                + " V okamžiku, kdy hodinová ručička dosáhne pozice ručičky červené, jsou do pohybu uvedeny paličky a je přehráno zvonění po dobu 10s.\n\n"
                + " Ovládání pomocí myši: \n"
                + " Stiskem levého tlačitka a takem myši mimo ciferník budíku se kamera rozhlíží \n"
                + " Ovládání pomocí kláves: \n"
                + " FORWARD: \t w \n"
                + " BACKWARD: \t s \n"
                + " LEFT: \t a \n"
                + " RIGHT: \t d \n"
                + " HELP: \t alt + F1 \n"
                + "\n"
                + "\n";
        backroundColor = white;
        title = "Help";
    }

    public String getText() {
        return text;
    }
    public String getTitle() {
        return title;
    }
    public Color getBackround() {
        return backroundColor;
    }

}






