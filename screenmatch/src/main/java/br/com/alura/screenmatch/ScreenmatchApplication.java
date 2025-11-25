package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var consumoapi = new ConsumoApi();
        var json = consumoapi.obterDados("https://www.omdbapi.com/?t=Breaking+Bad&apikey=f5547d42");
        System.out.println(json);
        ConverteDados conversor = new ConverteDados();
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);
        json = consumoapi.obterDados("https://www.omdbapi.com/?t=Breaking+Bad&season=1&episode=1&apikey=f5547d42");
        DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
        System.out.println(dadosEpisodio);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totaltemporadas(); i++) {
            json = consumoapi.obterDados("https://www.omdbapi.com/?t=Breaking+Bad&season=" + i + "&apikey=f5547d42");
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

//      json =consumoapi.obterDados("https://coffee.alexflipnote.dev/random.json");
//      System.out.println(json);

    }
}
