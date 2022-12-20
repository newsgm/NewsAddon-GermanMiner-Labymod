package dev.janheist.newsaddon.utls.sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class PlaySound {
    public static Thread d = null;

    public static void play(String url) {
        if (!url.endsWith(".wav"))
            return;
        if (d != null)
            d.stop();

        d = new Thread(() -> {
            AudioInputStream din = null;
            try {
                AudioInputStream in = AudioSystem.getAudioInputStream(new URL(url).openConnection().getInputStream());

                // avoid 403 error


                AudioFormat baseFormat = in.getFormat();
                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                        baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                        false);

                din = AudioSystem.getAudioInputStream(decodedFormat, in);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

                if (line != null) {
                    line.open(decodedFormat);

                    // Lautstärke
                    FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-15.0f);

                    byte[] data = new byte[4096];
                    // Start
                    line.start();

                    int nBytesRead;
                    while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
                        line.write(data, 0, nBytesRead);
                    }
                    // Stop
                    line.drain();
                    line.stop();
                    line.close();
                    din.close();
                    d = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (din != null) {
                    try {
                        din.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        });

        d.start();

    }

}
