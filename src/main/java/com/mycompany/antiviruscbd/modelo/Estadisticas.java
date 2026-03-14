package com.mycompany.antiviruscbd.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo para almacenar estadísticas del antivirus.
 */
public class Estadisticas {
    private int totalArchivosEscaneados;
    private int totalAmenazasDetectadas;
    private int totalArchivosLimpios;
    private LocalDateTime ultimoEscaneo;
    
    public Estadisticas() {
        this.totalArchivosEscaneados = 0;
        this.totalAmenazasDetectadas = 0;
        this.totalArchivosLimpios = 0;
        this.ultimoEscaneo = null;
    }
    
    public void registrarEscaneo(String resultado) {
        totalArchivosEscaneados++;
        ultimoEscaneo = LocalDateTime.now();
        
        if (resultado.equals("Archivo limpio")) {
            totalArchivosLimpios++;
        } else if (!resultado.startsWith("Error")) {
            totalAmenazasDetectadas++;
        }
    }
    
    public void reiniciar() {
        totalArchivosEscaneados = 0;
        totalAmenazasDetectadas = 0;
        totalArchivosLimpios = 0;
        ultimoEscaneo = null;
    }
    
    public int getTotalArchivosEscaneados() {
        return totalArchivosEscaneados;
    }
    
    public int getTotalAmenazasDetectadas() {
        return totalAmenazasDetectadas;
    }
    
    public int getTotalArchivosLimpios() {
        return totalArchivosLimpios;
    }
    
    public String getUltimoEscaneoFormateado() {
        if (ultimoEscaneo == null) {
            return "Nunca";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return ultimoEscaneo.format(formatter);
    }
    
    public boolean isProtegido() {
        return totalAmenazasDetectadas == 0 && totalArchivosEscaneados > 0;
    }
}
