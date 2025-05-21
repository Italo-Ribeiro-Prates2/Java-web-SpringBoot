package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporadas;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String ENDERECO= "https://www.omdbapi.com/?t=";
    private final String API_KEY="&apikey=6585022c";
    private ConsumoApi consumo = new ConsumoApi();
        private ConverteDados conversor = new ConverteDados();



    Scanner leitura = new Scanner(System.in);
    public void exibirMenu(){
        System.out.println("Digite o nome da série");
        String nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporadas> temporadas = new ArrayList<>();

        for(int i = 1; i<=dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace("", "+") +"&season=" + i + API_KEY);
            DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
            temporadas.add(dadosTemporadas);

        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
