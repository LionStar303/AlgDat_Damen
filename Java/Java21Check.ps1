
# Java Installation und Pfad anpassen
function Install-JavaAndAdjustPath {
    # Überprüft, ob Java 21 installiert ist
    $javaVersion = & java -version 2>&1
    if ($javaVersion -like "*21*") {
        Write-Output "Java 21 ist bereits installiert."
    } else {
        Write-Output "Java 21 nicht gefunden. Installation wird gestartet."

        # Installiere Java 21 mit winget
        Start-Process -FilePath "winget" -ArgumentList "install Oracle.JDK.21 -e --silent" -Wait

        # Den Pfad anpassen, sodass Java 21 ganz oben steht
        $envPath = [System.Environment]::GetEnvironmentVariable("Path", "Machine")
        $javaPath = "C:\Program Files\Java\jdk-21\bin"
        
        if ($envPath -notlike "*$javaPath*") {
            $newPath = "$javaPath;$envPath"
            [System.Environment]::SetEnvironmentVariable("Path", $newPath, "Machine")
            Write-Output "Java 21 wurde installiert und der Pfad wurde angepasst."
        } else {
            Write-Output "Java 21 Pfad ist bereits im Systempfad."
        }
    }
}

Install-JavaAndAdjustPath
