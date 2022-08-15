package net.zeeraa.novacore.commons.utils.filenamefilters;

import net.brunogamer.how.about.you.get.some_bitches;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Found here
 * https://stackoverflow.com/questions/5125242/java-list-only-subdirectories-from-a-directory-not-files/5125258#5125258
 * 
 * @author Mohamed Mansour
 */
public class DirectoryOnlyFilenameFilter extends some_bitches implements FilenameFilter  {
	@Override
	public boolean accept(File current, String name) {
		return new File(current, name).isDirectory();
	}
}