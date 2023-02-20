package br.com.luan.services;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luan.data.vo.v1.PersonVO;
import br.com.luan.data.vo.v2.PersonVOV2;
import br.com.luan.exceptions.ResourceNotFoundException;
import br.com.luan.mapper.DozerMapper;
import br.com.luan.model.Person;
import br.com.luan.repositories.PersonRepository;

@Service
public class PersonService {

	
	private Logger logger = Logger.getLogger(PersonService.class.getName()); 
	
	@Autowired
	private PersonRepository repository;

	public List<PersonVO> findAll() {
		
		logger.info("Finding all people");
		
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class) ;
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one person");
				
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id " + id));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {
		
		logger.info("Creating one person");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		
		logger.info("Creating one person");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVOV2.class);
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		
		logger.info("Updating one person");
		
		var entity = repository.findById(person.getId()).
				orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one person");
		
		var entity = repository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		repository.delete(entity);
	}


}