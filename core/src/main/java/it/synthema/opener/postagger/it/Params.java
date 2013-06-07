package it.synthema.opener.postagger.it;

import java.util.Enumeration;
import java.util.Hashtable;

public class Params {

	private String[] arguments;
	private Hashtable<String, String> attribute_to_value;

	public Params(String[] args) {
		this.arguments = args;
		this.attribute_to_value = new Hashtable<String, String>();
	}

	public int handle() {
		int R = arguments.length % 2;
		if (R != 0) {
			return -1;
		}
		int count = 0;
		while (count < arguments.length) {
			String attribute = arguments[count];
			count = count + 1;
			String value = arguments[count];
			if (attribute.startsWith("-")){
				attribute = attribute.substring(1);
				attribute_to_value.put(attribute, value);
			}
			count = count + 1;
		}
		return attribute_to_value.size();
	}

	public void print() {
		Enumeration<String> keys = attribute_to_value.keys();
		while (keys.hasMoreElements()) {
			String attribute = keys.nextElement();
			String value = attribute_to_value.get(attribute);
			System.out.println(attribute + ":\n\t" + value);
		}
	}

	public String getValue(String attribute) {
		if (attribute_to_value.containsKey(attribute))
			return attribute_to_value.get(attribute);
		else
			return null;
	}

}
