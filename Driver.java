import java.util.NoSuchElementException;

public class Driver {

	private static IUArrayList<String> list;
	
	public static void main(String args[]) {
		String[] items = "Hello there my interesting fellow. This super duper long string will be used for testing and you're gonna like it".split(" ");
		System.out.printf("Size of items: %d\n", items.length);
		list = new IUArrayList<String>(5);
		System.out.printf("The list is empty: %s\n", list.isEmpty());
		for (String item : items)
			list.add(item);
		//printList();
		
		System.out.println();
		System.out.printf("Index of 'fellow.': %s\n", list.indexOf("fellow."));
		
//		System.out.println();
//		list.addToFront("Traveler.");
//		printList();

//		System.out.println();
//		list.addAfter("impressive", "long");
//		printList();

//		System.out.println();
//		System.out.printf("Value at index 16: %s\n", list.get(16));
//		list.add(16, "some random fucking string");
//		printList();

//		System.out.println();
//		try {
//			list.add(35, "some random fucking string");
//		} catch (IndexOutOfBoundsException e) {
//			System.out.println("Caught the exception ya filthy animal");
//		}

//		System.out.println();
//		System.out.printf("Element from removeFirst: %s\n", list.removeFirst());
//		printList();

//		System.out.println();
//		System.out.printf("Element from removeLast: %s\n", list.removeLast());
//		printList();

//		System.out.println();
//		System.out.printf("Element from remove: %s\n", list.remove("used"));
//		printList();

//		System.out.println();
//		try {
//			list.remove("delivery boy");
//		} catch (NoSuchElementException e) {
//			System.out.println("Caught the exception boy.");
//		}

//		System.out.println();
//		System.out.printf("Element from remove using index: %s\n", list.remove(12));
//		printList();

//		System.out.println();
//		System.out.println("List before set method");
//		printList();
//		list.set(12, "extracted");
//		System.out.println("List after set method");
//		printList();

//		System.out.println();
//		System.out.printf("The very first element: %s\n", list.first());
//		System.out.printf("The very last element: %s\n", list.last());

		System.out.println();
		System.out.printf("The list contains 'super': %s\n", list.contains("super"));
		System.out.printf("The list is empty: %s\n", list.isEmpty());
	}
	
	public static void printList() {
		System.out.printf("Size of list: %d\n", list.size());
		System.out.println("Contents of list:");
		for (String item : list)
			System.out.println("\t" + item);
	}

}