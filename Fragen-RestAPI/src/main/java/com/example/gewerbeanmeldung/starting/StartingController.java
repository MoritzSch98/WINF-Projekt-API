package com.example.gewerbeanmeldung.starting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins="https://veranstaltungsformular.firebaseapp.com")
@RestController
@RequestMapping(path = "")
public class StartingController {

	@Autowired
	private StartingService sService;
		
	// Lists all Questions
	@RequestMapping(method = RequestMethod.POST, path = "/form/{form_id}/category/{category_id}/setstart")
	public String addStarting(@RequestBody Starting starting, @PathVariable Integer form_id, @PathVariable Integer category_id) {
		return sService.addStarting(starting, form_id, category_id);
	}
	@RequestMapping(method = RequestMethod.GET, path = "/form/{form_id}/category/{category_id}/findstart")
	public Starting getStarting(@PathVariable Integer form_id, @PathVariable Integer category_id) {
		return sService.getStarting(form_id, category_id);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findAllStarts")
	public List<Starting> getAllStartings(){
		return sService.getAllStartings();
	}
	
}
