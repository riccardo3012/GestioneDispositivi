package utils;
import java.util.Random;

public enum StatoDispositivo {
    DISPONIBILE, ASSEGNATO, IN_MANUTENZIONE, DISMESSO;

    private static final Random rndm = new Random();
    public static StatoDispositivo randomDeviceStatus() {
        StatoDispositivo[] dispositivo = values();
        return dispositivo[rndm.nextInt(dispositivo.length)];
    }
}
