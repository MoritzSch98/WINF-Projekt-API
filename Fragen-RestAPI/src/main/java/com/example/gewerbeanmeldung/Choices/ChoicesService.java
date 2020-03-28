package com.example.gewerbeanmeldung.Choices;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//This is the Choices Service class with all relevant methods for choices. 
@Service
public class ChoicesService {

	@Autowired
	private ChoicesRepository choiceRepo;

	public List<Choices> getAllChoices(Integer question_id) {

		return null;
	}

	// Get Choice
	public Choices getChoiceById(Integer id) {
		return choiceRepo.findById(id).orElse(null);
	}

	// Edit Choices
	public void editChoices(List<Choices> choices) {
		choiceRepo.saveAll(choices);
	}

	// Delete Choices by ID
	public void deleteById(Integer id) {
		choiceRepo.deleteById(id);
	}

	// Delete all Choices
	public void deleteAllChoices(List<Choices> c) {
		choiceRepo.deleteAll(c);

	}
}
