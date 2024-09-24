package LucasGodoy1.com.github.model;

import java.util.ArrayList;
import java.util.List;

public class Vaga {
    private String site;
    private List<String> titulo;

    public Vaga(String site) {
        this.site = site;
        titulo = new ArrayList<>();
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<String> getTitulo() {
        return titulo;
    }

    public void adcTitulo(String titulo) {
        this.titulo.add(titulo);
    }


    @Override
    public String toString() {
        return "Vagas:\n\n " + String.join("\n\n ", titulo) + "\n" + "\n" +
                    "Site: " + site;
    }
}
