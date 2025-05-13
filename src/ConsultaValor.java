import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsultaValor {
    public static void main(String[] args) throws IOException, InterruptedException {
        //constantes
        String moneda1 = "";
        String moneda2 = "";
        double valormonedaCambio = 0;
        String consulta = "ingrese el valor que desea convertir";
        double ratioCambio;

        List<formatoConversion> listaConsulta = new ArrayList<>();

        //llamada de scanner
        Scanner teclado = new Scanner(System.in);
        Scanner teclado2 = new Scanner(System.in);
        Scanner teclado3 = new Scanner(System.in);

        //genera una direccion de API con la APIkey


        //sistema de menu, debe conetener opciones predeterminadas y
        // una opcion para ingresar de forma manual dos monedas
        System.out.println("""
                ******************************************
                
                    \033[4mBienvenidos al sistema de conversion de monedas\033[0m
                
                *******************************************
                """);

        while (true) {
            System.out.println("""
                    *****************************
                    
                    Porfavor Elija una opcion
                    1. Dolar - Peso chileno
                    2. Dolar - Peso argentino
                    3. Dolar - Real Brasileño
                    4. Dolar - Euro
                    5. Euro - Peso chileno
                    6. Eleccion libre
                    7. salir
                    
                    ******************************
                    
                    """);

            //interaccion con el usuario
            int mensaje = teclado.nextInt();

            //asigna los valores a las opciones ingresadas
            switch (mensaje) {
                case 1:
                    moneda1 = "USD";
                    moneda2 = "CLP";

                    System.out.println(consulta);
                    valormonedaCambio = teclado.nextDouble();

                    break;
                case 2:
                    moneda1 = "USD";
                    moneda2 = "ARS";

                    System.out.println(consulta);
                    valormonedaCambio = teclado.nextDouble();
                    break;
                case 3:
                    moneda1 = "USD";
                    moneda2 = "BRL";

                    System.out.println(consulta);
                    valormonedaCambio = teclado.nextDouble();
                    break;
                case 4:
                    moneda1 = "USD";
                    moneda2 = "EUR";

                    System.out.println(consulta);
                    valormonedaCambio = teclado.nextDouble();
                    break;
                case 5:
                    moneda1 = "EUR";
                    moneda2 = "CLP";
                    System.out.println(consulta);
                    valormonedaCambio = teclado.nextDouble();
                    break;
                case 6:
                    System.out.println("selecciona la primera moneda");
                    moneda1 = teclado2.nextLine();
                    teclado.nextLine();

                    System.out.println("selecciona la segunda moneda");
                    moneda2 = teclado3.nextLine();

                    System.out.println(consulta);
                    valormonedaCambio = teclado.nextDouble();
                    break;

            }

            //condicion de salida
            if (mensaje == 7) {
                break;
            }

            String direccion = "https://v6.exchangerate-api.com/v6//pair/" + moneda1 + "/" + moneda2;

            try {
                //sistema http client, request y response
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();
                //codigo que recibe la respuesta y genera el cuerpo
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                ratioCambio = jsonObject.get("conversion_rate").getAsDouble();

                double monedaConversion = valormonedaCambio * ratioCambio;

                valoresJson misvalores = gson.fromJson(json, valoresJson.class);
                formatoConversion miconsulta = new formatoConversion(misvalores);


                System.out.println("el valor de " + valormonedaCambio + "[" + moneda1 + "]" + "corresponde al valor final de: " +
                        monedaConversion + "[" + moneda2 + "]");

                listaConsulta.add(miconsulta);

                FileWriter documento = new FileWriter("lista de conversiones.txt");
                documento.write(gson.toJson(listaConsulta));
                documento.close();

            } catch (RuntimeException e) {
                System.out.println("Se genero un error logico");
            }
        }

        //mensaje de despedida
        System.out.println("""
                        **********************************************
                        
                            \033[4mGracias por utilizar el sistema\033[0m
                                \033[4mde conversion de monedas\033[0m
                        
                        ***********************************************
                        """);



    }
}
