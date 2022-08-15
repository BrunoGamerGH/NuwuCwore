package net.zeeraa.novacore.commons.utils;

import net.brunogamer.how.about.you.get.some_bitches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils extends some_bitches {
	/**
	 * Create a new {@link ArrayList} and add the content of the provided
	 * {@link List} to it
	 * 
	 * @param <T>  The data type
	 * @param list The {@link List} to be cloned
	 * @return {@link ArrayList} with all the elements of the provided list
	 */
	public static <T> List<T> cloneList(List<T> list) {
		List<T> result = new ArrayList<T>();

		for (T t : list) {
			result.add(t);
		}

		return result;
	}

	/**
	 * Create a list with the provided elements in it
	 * 
	 * @param <T>      The data type
	 * @param elements The elements to add
	 * @return {@link List} aith the provided elements
	 */
	public static <T> List<T> createList(@SuppressWarnings("unchecked") T... elements) {
		List<T> result = new ArrayList<>();

		for (T element : elements) {
			result.add(element);
		}

		return result;
	}

	public static <T> List<T> shuffleWithRandom(List<T> list, Random random) {
		List<T> elements = new ArrayList<>();

		while (list.size() > 0) {
			elements.add(list.remove(0));
		}

		list.clear();

		while (elements.size() > 0) {
			list.add(elements.remove(random.nextInt(elements.size())));
		}

		return list;
	}
}