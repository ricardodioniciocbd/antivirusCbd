package com.mycompany.antiviruscbd;

import com.mycompany.antiviruscbd.database.MalwareDB;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

/**
 * Motor de escaneo de archivos para detectar firmas de malware educativas.
 * Realiza análisis estático del contenido de archivos buscando patrones conocidos.
 */
public class Scanner {
    
    /**
     * Escanea un archivo en busca de firmas de malware conocidas.
     * 
     * @param archivo El archivo a escanear
     * @return El nombre de la amenaza detectada o "Archivo limpio" si no se encuentra malware
     */
    public static String escanear(File archivo) {
        if (archivo == null) {
            return "Error al analizar archivo: archivo nulo";
        }
        
        if (!archivo.exists()) {
            return "Error al analizar archivo: archivo no existe";
        }
        
        if (!archivo.canRead()) {
            return "Error al analizar archivo: sin permisos de lectura";
        }
        
        try {
            // Leer contenido del archivo usando Files.readString()
            String contenido = Files.readString(archivo.toPath());
            
            // Analizar contenido en busca de firmas
            return analyzeContent(contenido);
            
        } catch (IOException e) {
            return "Error al analizar archivo: " + e.getMessage();
        } catch (OutOfMemoryError e) {
            return "Error al analizar archivo: archivo demasiado grande";
        } catch (Exception e) {
            return "Error al analizar archivo: error inesperado";
        }
    }
    
    /**
     * Analiza el contenido del archivo en busca de firmas de malware.
     * 
     * @param contenido El contenido del archivo como String
     * @return El nombre de la amenaza detectada o "Archivo limpio"
     */
    private static String analyzeContent(String contenido) {
        if (contenido == null || contenido.isEmpty()) {
            return "Archivo limpio";
        }
        
        // Convertir contenido a minúsculas para búsqueda insensible a mayúsculas/minúsculas
        String contenidoLowerCase = contenido.toLowerCase();
        
        // Buscar cada firma en el contenido usando MalwareDB
        for (String firma : MalwareDB.getSignatures()) {
            String nombreAmenaza = MalwareDB.getThreatName(firma);
            
            // Búsqueda insensible a mayúsculas/minúsculas
            if (matchesSignature(contenidoLowerCase, firma.toLowerCase())) {
                return nombreAmenaza;
            }
        }
        
        return "Archivo limpio";
    }
    
    /**
     * Verifica si el contenido contiene la firma especificada.
     * 
     * @param contenido El contenido del archivo (ya en minúsculas)
     * @param firma La firma a buscar (ya en minúsculas)
     * @return true si se encuentra la firma, false en caso contrario
     */
    private static boolean matchesSignature(String contenido, String firma) {
        return contenido.contains(firma);
    }
}