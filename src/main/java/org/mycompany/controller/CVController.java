package org.mycompany.controller;

import java.io.FileWriter;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.json.simple.JsonObject;
import org.mycompany.model.CV;
import org.mycompany.model.Candidat;
import org.mycompany.repo.ICVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CVController {
	
	private static String url = "tcp://194.206.91.85:61616";
	
	@Autowired
	ProducerTemplate producerTemplate;

	@Autowired
	ICVRepository ier;

	@GetMapping("/getCV/{id}")
	public CV getCV(@PathVariable int id) {
		return ier.findById(id).get();
	}

	@GetMapping("/getCVs")
	public List<CV> getCVs() {
		return ier.findAll();
	}

	@PostMapping("/saveCV")
	public void saveCV(@RequestBody CV cv) {
		ier.save(cv);
	}

	@DeleteMapping("/deleteCV/{id}")
	public void deleteCV(@PathVariable int id) {
		ier.deleteById(id);
	}

	@PutMapping("/updateCV{id}")
	public CV updateCV(@RequestBody CV newCV, @PathVariable int id) {
		return ier.findById(id).map(CV -> {
			CV.setId(newCV.getId());
			CV.setDescription(newCV.getDescription());
			CV.setCandidat(newCV.getCandidat());
			return ier.save(CV);
		}).orElseGet(() -> {
			return ier.save(newCV);
		});
	}
	
	@GetMapping("/lancerRoute")
	public void lanceRoute() throws Exception{
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.createConnection("admin", "adaming2022");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.start();
		producerTemplate.sendBody("direct:creationCV", null);
		context.stop();
	}
	



}
