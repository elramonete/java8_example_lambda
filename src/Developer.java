import java.util.HashSet;
import java.util.Set;

class Developer {
	
	private String name;
	private Set<String> languages;

	public Developer(String name) {
		this.languages = new HashSet<>();
		this.name = name;
	}

	public void add(String language) {
		this.languages.add(language);
	}

	public Set<String> getLanguages() {
		return languages;
	}
	
	public String getName() {
		return name;
	}
}
