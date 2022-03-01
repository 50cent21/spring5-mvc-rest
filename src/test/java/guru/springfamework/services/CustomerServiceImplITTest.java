package guru.springfamework.services;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplITTest {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	VendorRepository vendorRepository;
	
	CustomerService customerService;
	
	@Before
	public void setUp() throws Exception {
		
		System.out.println("Loading Customer Data");
		System.out.println(customerRepository.findAll().size());
		
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
		bootstrap.run();
		
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	public void testPatchCustomerUpdateFirstName() throws Exception{
		
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		
		//save original first/last name
		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName(updatedName);
		
		customerService.patchCustomer(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstName());
		assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
		assertThat(originalLastName, equalTo(updatedCustomer.getFirstName()));
	}
	

	@Test
	public void testPatchCustomerUpdateLastName() throws Exception{
		
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();
		
		Customer originalCustomer = customerRepository.getOne(id);
		assertNotNull(originalCustomer);
		
		//save original first/last name
		String originalFirstName = originalCustomer.getFirstName();
		String originalLastName = originalCustomer.getLastName();
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastName(updatedName);
		
		customerService.patchCustomer(id, customerDTO);
		
		Customer updatedCustomer = customerRepository.findById(id).get();
		
		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastName());
	    assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
	    assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
	    
	}
	
	private long getCustomerIdValue() {
		
		List<Customer> customers = customerRepository.findAll();
		
		System.out.println("Customers found: " + customers.size());
		
		return customers.get(0).getId();
	}

}
