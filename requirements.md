# Documento de Requisitos

## Introducción

Este documento especifica los requisitos para una aplicación antivirus educativa construida con JavaFX. El sistema realiza análisis estático y dinámico de archivos para detectar firmas de malware simuladas con fines educativos, utilizando archivos de prueba seguros incluyendo el archivo de prueba estándar EICAR y firmas de malware educativas personalizadas.

## Glosario

- **Sistema_Antivirus**: La aplicación JavaFX que escanea archivos en busca de firmas de malware
- **Archivo_Prueba_EICAR**: Archivo de prueba estándar internacional utilizado para verificar la funcionalidad del antivirus
- **Firma_Malware**: Patrones de texto que identifican amenazas de malware simuladas
- **Análisis_Estático**: Análisis de archivos realizado sin ejecutar el archivo
- **Análisis_Dinámico**: Análisis de comportamiento simulado de las características del archivo
- **Base_Datos_Firmas**: Colección de patrones de malware conocidos y sus nombres de amenaza
- **Escáner_Archivos**: Componente responsable de leer y analizar el contenido de los archivos
- **Detección_Amenazas**: Proceso de identificar firmas de malware dentro del contenido de los archivos

## Requisitos

### Requisito 1

**Historia de Usuario:** Como estudiante de ciberseguridad, quiero escanear archivos en busca de firmas de malware, para poder aprender cómo funciona la detección de antivirus.

#### Criterios de Aceptación

1. CUANDO un usuario selecciona un archivo a través del selector de archivos, EL Sistema_Antivirus DEBERÁ leer el contenido del archivo y analizarlo en busca de firmas conocidas
2. CUANDO el Escáner_Archivos detecta una firma de malware conocida, EL Sistema_Antivirus DEBERÁ mostrar el nombre de amenaza correspondiente
3. CUANDO no se encuentran firmas de malware, EL Sistema_Antivirus DEBERÁ reportar el archivo como limpio
4. CUANDO falla la lectura del archivo, EL Sistema_Antivirus DEBERÁ mostrar un mensaje de error apropiado
5. EL Sistema_Antivirus DEBERÁ realizar coincidencia de firmas sin distinción de mayúsculas y minúsculas para asegurar detección confiable

### Requisito 2

**Historia de Usuario:** Como estudiante, quiero que el sistema detecte el archivo de prueba EICAR, para poder verificar que el antivirus está funcionando correctamente.

#### Criterios de Aceptación

1. CUANDO un archivo contiene la cadena de prueba estándar EICAR, EL Sistema_Antivirus DEBERÁ detectarlo como "EICAR-Test-File"
2. EL Sistema_Antivirus DEBERÁ reconocer la firma EICAR independientemente de la extensión del archivo
3. CUANDO se detecta el archivo de prueba EICAR, EL Sistema_Antivirus DEBERÁ reportar inmediatamente la amenaza sin escanear firmas adicionales

### Requisito 3

**Historia de Usuario:** Como instructor de ciberseguridad, quiero que el sistema detecte 10 firmas específicas de malware educativo, para que los estudiantes puedan practicar con varios tipos de amenazas.

#### Criterios de Aceptación

1. EL Sistema_Antivirus DEBERÁ mantener una base de datos de firmas que contenga exactamente 10 firmas de malware predefinidas
2. CUANDO un archivo contiene "trojan.fake.2025", EL Sistema_Antivirus DEBERÁ detectar "Trojan.Fake.2025"
3. CUANDO un archivo contiene "ransom.simlock", EL Sistema_Antivirus DEBERÁ detectar "Ransomware.SimLock"
4. CUANDO un archivo contiene "keylogger.sim", EL Sistema_Antivirus DEBERÁ detectar "Spyware.KeyLogger.Sim"
5. CUANDO un archivo contiene "autorun=true", EL Sistema_Antivirus DEBERÁ detectar "Worm.AutoSpread.Sim"
6. CUANDO un archivo contiene "backdoor.sim", EL Sistema_Antivirus DEBERÁ detectar "Backdoor.Remote.Sim"
7. CUANDO un archivo contiene "cryptominer.sim", EL Sistema_Antivirus DEBERÁ detectar "Miner.CryptoSim"
8. CUANDO un archivo contiene "adware.sim", EL Sistema_Antivirus DEBERÁ detectar "Adware.PopupSim"
9. CUANDO un archivo contiene "exploit.pdf.sim", EL Sistema_Antivirus DEBERÁ detectar "Exploit.PDF.Sim"
10. CUANDO un archivo contiene "macro.doc.sim", EL Sistema_Antivirus DEBERÁ detectar "Macro.Doc.Sim"

### Requisito 4

**Historia de Usuario:** Como usuario, quiero una interfaz responsiva que no se congele durante el escaneo, para poder continuar interactuando con la aplicación.

#### Criterios de Aceptación

1. CUANDO se inicia un escaneo de archivo, EL Sistema_Antivirus DEBERÁ ejecutar el proceso de escaneo en un hilo de fondo
2. MIENTRAS el escaneo está en progreso, EL Sistema_Antivirus DEBERÁ mostrar un indicador de progreso para mostrar la actividad de escaneo
3. MIENTRAS el escaneo está en progreso, EL Sistema_Antivirus DEBERÁ actualizar el mensaje de estado para indicar el estado actual de escaneo
4. CUANDO el escaneo se completa, EL Sistema_Antivirus DEBERÁ actualizar la interfaz con los resultados finales
5. EL Sistema_Antivirus DEBERÁ simular tiempo de escaneo realista para demostrar comportamiento apropiado de hilos

### Requisito 5

**Historia de Usuario:** Como estudiante, quiero una interfaz de antivirus clara y profesional, para poder entender fácilmente el proceso de escaneo y los resultados.

#### Criterios de Aceptación

1. EL Sistema_Antivirus DEBERÁ mostrar un título que lo identifique como "Antivirus Educativo 2025"
2. EL Sistema_Antivirus DEBERÁ proporcionar un botón claramente etiquetado "Escanear Archivo" para iniciar escaneos
3. EL Sistema_Antivirus DEBERÁ mostrar el estado actual del sistema (esperando, escaneando, o resultados)
4. EL Sistema_Antivirus DEBERÁ mostrar una barra de progreso que indique el progreso del escaneo
5. CUANDO muestre resultados, EL Sistema_Antivirus DEBERÁ indicar claramente si se detectaron amenazas o si el archivo está limpio

### Requisito 6

**Historia de Usuario:** Como estudiante de ciberseguridad, quiero probar varios tipos de archivos con firmas incrustadas, para poder entender cómo el malware puede ocultarse en diferentes formatos de archivo.

#### Criterios de Aceptación

1. EL Sistema_Antivirus DEBERÁ escanear cualquier tipo de archivo seleccionado por el usuario
2. EL Sistema_Antivirus DEBERÁ detectar firmas de malware independientemente de la extensión del archivo
3. CUANDO un archivo tiene una combinación de extensión sospechosa, EL Sistema_Antivirus DEBERÁ aún realizar detección basada en firmas
4. EL Sistema_Antivirus DEBERÁ manejar archivos de texto, archivos de documento y archivos ejecutables uniformemente para detección de firmas