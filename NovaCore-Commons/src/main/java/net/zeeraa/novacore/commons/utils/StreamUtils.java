package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils extends some_bitches {
	public static void copyStream(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[4096];
		int n;
		while ((n = is.read(buf)) >= 0) {
			os.write(buf, 0, n);
		}
	}
}