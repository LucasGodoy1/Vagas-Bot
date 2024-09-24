package LucasGodoy1.com.github.service;

import LucasGodoy1.com.github.model.Vaga;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Encontre {
    private List<Integer> indiceDoErro;
    private Integer indice;
    private List<String> listaConvertida;
    private List<String> resultados;
    private Integer totalDeVagas = 0;

    public Encontre() {
        this.indiceDoErro = new ArrayList<>();
        resultados = new ArrayList<>();
        this.indice = 0;
    }

    public List<String> iniciarBusca(String suaBusca)  {
        try {
        listaConvertida = Conversor.converteArquivoEmLista("D://teste.txt");
        }catch (IOException ex ){
            System.out.println("Erro ao tentar converter o txt em uma lista de links " + ex);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<String>> futures = new ArrayList<>();

        for (String link : listaConvertida) {
            indice++;
            if (link.isEmpty()) {
                indiceDoErro.add(indice);
            }
            if (!link.isEmpty()) {
                futures.add(executorService.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return encontreSuaVaga(link, suaBusca);
                    }
                }));
            }
        }

        for (Future<String> future : futures) {
            try {
                String resultado = future.get();
                if (!resultado.isEmpty()) {
                    resultados.add(resultado);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return resultados;
    }
    public String encontreSuaVaga(String site, String palavra){

        var resultado = ConsultaHttp.coletaCorpoDoSite(site);

        if (resultado != null) {
            Document doc = Jsoup.parse(resultado);
            Elements elements = doc.select("ul[data-testid='job-list__list'] li[data-testid='job-list__listitem']");

            List<String> listaDeVagas = elements.stream()
                    .map(element -> element.select("div.sc-f5007364-4").text())
                    .filter(text -> !text.isEmpty() && text.toLowerCase().contains(palavra))
                    .toList();

            if (!listaDeVagas.isEmpty()) {
                var vagaEncontrada = new Vaga(site);
                listaDeVagas.forEach(vaga -> {
                    vagaEncontrada.adcTitulo(vaga);
                    totalDeVagas += 1;
                });

                return vagaEncontrada.toString();

            } else {
                Elements elementsContainingWord = doc.body().select("*:contains(" + palavra + ")");
                if (!elementsContainingWord.isEmpty()) {
                    var vagaEncontrada = new Vaga(site);
                    String vaga = "Foi encontrado a vaga " + "'" + palavra +"'" + " no site abaixo, mas\n não foi possível coletar o nome️ da vaga" + "\nacesse o site abaixo e verifique";
                    vagaEncontrada.adcTitulo(vaga.toUpperCase());
                    totalDeVagas += 1;
                    return vagaEncontrada.toString();
                }
            }
        }
        return "";
    }

    public List<Integer> getIndiceDoErro() {
        return indiceDoErro;
    }

    public Integer getTotalDeVagas() {
        return totalDeVagas;
    }
}
