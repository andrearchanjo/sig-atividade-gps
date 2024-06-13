package com.example.atividade_gps

class Ponto {
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var altitude: Double = 0.0

    constructor()

    constructor(latitude: Double, longitude: Double, altitude: Double): this() {
        this.latitude = latitude
        this.longitude = longitude
        this.altitude = altitude
    }

    fun imprimir(): String {
        return "Lat: $latitude, Long: $longitude, Alt: $altitude"
    }

    fun imprimir2(): String {
        return """
            ***********************
            Latitude: $latitude
            Longitude: $longitude
            Altitude: $altitude
            ***********************
        """.trimIndent()
    }

    fun imprimirHtml(): String {
        return """
            <html>
            Latitude: $latitude<br>
            Longitude: $longitude<br>
            Altitude: $altitude<br>
            </html>
        """.trimIndent()
    }
}
