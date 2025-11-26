# Conversor de Monedas

## Descripción
Este proyecto es un **convertidor de monedas** en Java que utiliza la API de ExchangeRate para obtener las tasas de conversión en tiempo real. Permite al usuario seleccionar una moneda de origen y una moneda de destino, e ingresar una cantidad para convertir.

---

## Autor
**Alejandro Ticona**  
Fecha: 19/11/2025

---

## Funcionalidades
- Obtiene tasas de conversión de monedas en tiempo real usando la API de [ExchangeRate](https://www.exchangerate-api.com/).  
- Filtra las monedas permitidas: `ARS`, `BOB`, `BRL`, `CLP`, `COP`, `USD`.  
- Permite al usuario seleccionar la moneda de origen y de destino mediante un menú interactivo.  
- Calcula y muestra la cantidad convertida según la tasa actual.

---

## Tecnologías
- Java 11+  
- Librería **Gson** para manejo de JSON (`com.google.gson`)  
- API HTTP estándar de Java (`java.net.http.HttpClient`)  

---

## Uso
1. Clonar o descargar el repositorio.  
2. Asegurarse de tener Java 11 o superior.  
3. Compilar el proyecto:
```bash
javac Main.java
