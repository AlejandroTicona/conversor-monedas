import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * Autor: alejandro.ticona
 * Fecha: 11/19/2025
 */
//apiKey = fa45163b221599f1ef3b5f88
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/fa45163b221599f1ef3b5f88/latest/USD";
        Map<String, Double> monedasFiltradas = obtenerMonedasFiltradas(url);
        System.out.println("MONEDA ORIGEN");
        String monedaOrigen = mostrarMenuMonedas(monedasFiltradas);
        System.out.println("MONEDA DESTINO");
        String monedaDestino = mostrarMenuMonedas(monedasFiltradas);
        double tasa = obtenerTasa(monedaOrigen, monedaDestino);
        System.out.println("CONVIRTIENDO!!!");
        System.out.println("La conversion de " + monedaOrigen + " a " + monedaDestino);
        System.out.println("es de " + tasa + " " + monedaDestino);
    }

    public static String mostrarMenuMonedas(Map<String, Double> monedas) {
        Map<String, String> nombres = Map.of(
                "ARS", "Peso argentino",
                "BOB", "Boliviano",
                "BRL", "Real brasileño",
                "CLP", "Peso chileno",
                "COP", "Peso colombiano",
                "USD", "Dólar estadounidense"
        );

        List<String> keys = new ArrayList<>(monedas.keySet());

        System.out.println("Seleccione una moneda:");
        for (int i = 0; i < keys.size(); i++) {
            String codigo = keys.get(i);
            System.out.println((i + 1) + " - " + codigo + " - " + nombres.get(codigo));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese una opción: ");
        int opcion = scanner.nextInt();

        // Validación
        while (opcion < 1 || opcion > keys.size()) {
            System.out.print("Opción inválida. Intente de nuevo: ");
            opcion = scanner.nextInt();
        }

        return keys.get(opcion - 1);
    }

    public static Map<String, Double> obtenerMonedasFiltradas(String urlFinal) throws IOException, InterruptedException {
        Set<String> monedasPermitidas = Set.of("ARS", "BOB", "BRL", "CLP", "COP", "USD");

        Map<String, Double> monedasFiltradas = new HashMap<>();

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(urlFinal))
                .GET()
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            JsonElement elemento = JsonParser.parseString(respuesta.body());
            JsonObject objectRoot = elemento.getAsJsonObject();

            JsonObject ratesObject = objectRoot.getAsJsonObject("conversion_rates");

            for (String code : monedasPermitidas) {
                if (ratesObject.has(code)) {
                    monedasFiltradas.put(code, ratesObject.get(code).getAsDouble());
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return monedasFiltradas;
    }

    public static double obtenerTasa(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String urlTasa = "https://v6.exchangerate-api.com/v6/fa45163b221599f1ef3b5f88/latest/" + monedaOrigen;
        double tasa = 0;
        System.out.println("Ingrese la cantidad de " + monedaOrigen);
        double cantidad = sc.nextDouble();
        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(urlTasa))
                .GET()
                .build();
        try {
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            JsonElement elemento = JsonParser.parseString(respuesta.body());
            JsonObject objectRoot = elemento.getAsJsonObject();
            JsonObject ratesObject = objectRoot.getAsJsonObject("conversion_rates");
            tasa = ratesObject.get(monedaDestino).getAsDouble();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tasa * cantidad;
    }
}